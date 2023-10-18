package com.example.contacts.ui.view.fragment.crud

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contacts.R
import com.example.contacts.adapter.UserListAdapter
import com.example.contacts.databinding.FragmentListaUserBinding
import com.example.contacts.ui.viewModel.list.UserListViewModel
import com.example.contacts.utils.ConstantsUser.ID_USER
import dagger.hilt.android.AndroidEntryPoint

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
            bundle.putInt(ID_USER,it)
            fragmentUpdate.arguments = bundle
            val ft: FragmentTransaction = activity?.supportFragmentManager!!.beginTransaction()
            // fragmentUpdate.arguments = bundle
            ft.replace(R.id.frameUser, fragmentUpdate, "fragment_create_user")
            ft.addToBackStack(null)
            ft.commit()
        }
        binding.recyclerPerson.adapter = adapterPerson
        listUserViewModel.getUser.observe(this) {

            /* Log.i(TAG, it.toString())

             model.addAll(listOf(it))*/

            /* for (i in it.iterator()) {

                  if (i != null) {
                      val listaObjetos = mutableListOf(i)
                      model.addAll(i.toString())
                  }

              }*/
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