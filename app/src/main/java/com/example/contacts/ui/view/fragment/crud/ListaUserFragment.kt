package com.example.contacts.ui.view.fragment.crud

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.adapter.SwipeController
import com.example.contacts.adapter.UserListAdapter
import com.example.contacts.core.ApiState
import com.example.contacts.core.email.AppExecutors
import com.example.contacts.databinding.FragmentListaUserBinding
import com.example.contacts.domain.model.User
import com.example.contacts.ui.viewModel.delete.DeleteViewModel
import com.example.contacts.ui.viewModel.list.UserListViewModel
import com.example.contacts.utils.ConstantsUser.ID_USER
import com.example.contacts.utils.Credenciales
import com.example.contacts.utils.UtilsMessage
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*
import javax.mail.*
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage
import kotlin.collections.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


@AndroidEntryPoint
class ListaUserFragment : Fragment() {

    private var _binding: FragmentListaUserBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = ListaUserFragment::class.java.simpleName
    private val listUserViewModel: UserListViewModel by viewModels()
    private val deleteViewModel : DeleteViewModel by viewModels()

    lateinit var appExecutors: AppExecutors

    private lateinit var  adapterPerson : UserListAdapter

    private val fragment = CreateUserFragment()

    private val fragmentUpdate = UpdateUserFragment()
    private val bundle = Bundle()
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListaUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerPerson.layoutManager = LinearLayoutManager(requireContext())

        adapterPerson = UserListAdapter(requireContext(),){
            Log.i(TAG,it)
            if (ValidateEditText.isEditTextInGmailFormat(it)){
                sendEmail(it)
                Toast.makeText(requireContext(),getString(R.string.send_gmail),Toast.LENGTH_SHORT).show()
            }
            else{
                UtilsMessage.showAlertOK(requireContext(),getString(R.string.Gmail),getString(R.string.message_gmail))

            }
        }
        binding.recyclerPerson.adapter = adapterPerson
        listUserViewModel.getUserFlow()

        lifecycleScope.launchWhenStarted {
            listUserViewModel.userStateFlow.collect{apiState->
                when(apiState){
                    is ApiState.Loading->{
                        binding.recyclerPerson.isVisible = false
                        binding.progressDialog.isVisible = true
                    }
                    is ApiState.Failure->{
                        binding.recyclerPerson.isVisible = false
                        binding.progressDialog.isVisible = false
                        Log.d(TAG,"OnCreate: ${apiState.msg}")
                    }
                    is ApiState.Success->{
                        binding.recyclerPerson.isVisible = true
                        binding.progressDialog.isVisible = false
                        binding.txtSearch.addTextChangedListener {filter->
                            val items = apiState.data.filter {user->
                                user.firstName.lowercase().contains(filter.toString().lowercase())
                            }
                            adapterPerson.updateRecycler(items)
                        }
                        adapterPerson.setData(apiState.data)
                        swipeToGesture(binding.recyclerPerson,apiState.data as ArrayList<User>)
                        adapterPerson.notifyDataSetChanged()
                    }
                    is ApiState.Empty->{

                    }
                }
            }
        }



        binding.fab.setOnClickListener {
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            ft.replace(R.id.frameUser, fragment, "fragment_create_user")
            ft.addToBackStack(null)
            ft.commit()
        }
    }

    private fun swipeToGesture(recyclerPerson : RecyclerView, items : ArrayList<User>){
        val swipeGesture = object : SwipeController(requireContext()){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                try {
                    when(direction){
                        ItemTouchHelper.LEFT ->{
                            val deleteItem = items[position].id
                            val builder = AlertDialog.Builder(requireContext())
                            builder.setTitle(getString(R.string.delete))
                            builder.setMessage(getString(R.string.delete_message))
                            builder.setPositiveButton("Aceptar") { dimiss, _ ->
                                GlobalScope.launch {
                                    deleteViewModel.deleteUser(deleteItem)
                                }
                                adapterPerson.remoteUser(items,position)
                            }
                            builder.setNegativeButton("Cancelar") { dimiss, _ ->
                                dimiss.dismiss()
                            }
                            val alertDialog = builder.create()
                            alertDialog.show()

                        }
                        ItemTouchHelper.RIGHT ->{
                            val updateUser = items[position].id
                            bundle.putInt(ID_USER,updateUser)
                            fragmentUpdate.arguments = bundle
                            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
                            // fragmentUpdate.arguments = bundle
                            ft.replace(R.id.frameUser, fragmentUpdate, "fragment_create_user")
                            ft.addToBackStack(null)
                            ft.commit()
                        }
                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }

        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerPerson)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appExecutors = AppExecutors()
    }
    private fun sendEmail(email : String){
        appExecutors.diskIO().execute {
            val props = System.getProperties()
            props.put("mail.smtp.host", "smtp.gmail.com")
            props.put("mail.smtp.socketFactory.port", "465")
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory")
            props.put("mail.smtp.auth", "true")
            props.put("mail.smtp.port", "465")

            val session =  Session.getInstance(props,
                object : javax.mail.Authenticator() {
                    override fun getPasswordAuthentication(): PasswordAuthentication {
                        return PasswordAuthentication(Credenciales.EMAIL, Credenciales.PASSWORD)
                    }
                })

            try {
                //Creating MimeMessage object
                val mm = MimeMessage(session)
                //Setting sender address
                mm.setFrom(InternetAddress(Credenciales.EMAIL))
                //Adding receiver
                mm.addRecipient(Message.RecipientType.TO,
                    InternetAddress(email))
                //Adding subject
                mm.subject = "Prueba Email"
                //Adding message
                mm.setText("Este es un correo de prueba")

                //Sending email
                Transport.send(mm)

                appExecutors.mainThread().execute {
                    //Something that should be executed on main thread.
                }

            } catch (e: MessagingException) {
                e.printStackTrace()
            }
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ListaUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ListaUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}