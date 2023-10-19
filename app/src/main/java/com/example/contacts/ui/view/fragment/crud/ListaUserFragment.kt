package com.example.contacts.ui.view.fragment.crud

import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.contacts.R
import com.example.contacts.adapter.SwipeController
import com.example.contacts.adapter.UserListAdapter
import com.example.contacts.databinding.FragmentListaUserBinding
import com.example.contacts.domain.model.User
import com.example.contacts.ui.viewModel.delete.DeleteViewModel
import com.example.contacts.ui.viewModel.list.UserListViewModel
import com.example.contacts.utils.ConstantsUser.ID_USER
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ListaUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class ListaUserFragment : Fragment() {

    private var _binding: FragmentListaUserBinding? = null
    private val binding get() = _binding!!

    private val TAG: String = ListaUserFragment::class.java.simpleName
    private val listUserViewModel: UserListViewModel by viewModels()
    private val deleteViewModel : DeleteViewModel by viewModels()

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
            Log.i(TAG,it.toString())
           /* bundle.putInt(ID_USER,it)
            fragmentUpdate.arguments = bundle
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            // fragmentUpdate.arguments = bundle
            ft.replace(R.id.frameUser, fragmentUpdate, "fragment_create_user")
            ft.addToBackStack(null)
            ft.commit()*/
        }
        binding.recyclerPerson.adapter = adapterPerson
        listUserViewModel.getUser.observe(this) {
            swipeToGesture(recyclerPerson = binding.recyclerPerson, items = it as ArrayList<User>)
            adapterPerson.setData(it)

        }
        listUserViewModel.getAllUser()
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
                var actionBtnTapped = false

                try {
                    when(direction){
                        ItemTouchHelper.LEFT ->{
                            //val deleteItem = items[position].id
                            val deleteItem = items[position].id
                          /*  GlobalScope.launch {

                                deleteViewModel.deleteUser(deleteItem)
                                //adapterPerson.updateRecycler(items)
                            }*/
                            items.removeAt(position)
                            adapterPerson.notifyItemRemoved(position)

                            //Log.d("ID seleccionado",deleteItem.toString())

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