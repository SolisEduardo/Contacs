package com.example.contacts.ui.view.fragment.crud

import android.os.Bundle
import android.provider.Settings.Global
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.databinding.FragmentUpdateUserBinding
import com.example.contacts.domain.model.User
import com.example.contacts.ui.viewModel.data.delete.DeleteViewModel
import com.example.contacts.ui.viewModel.data.search.SearchViewModel
import com.example.contacts.ui.viewModel.data.update.UpdateUserViewModel
import com.example.contacts.utils.ConstantsUser
import com.example.contacts.utils.ValidateEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class UpdateUserFragment : Fragment() {

    private var _binding : FragmentUpdateUserBinding? = null
    private val binding get() = _binding!!
    private var id_user : Int =0

    private val searchViewModel : SearchViewModel by viewModels()
    private val updateUserViewModel : UpdateUserViewModel by viewModels()
    private val deleteViewModel : DeleteViewModel by viewModels()
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
        _binding= FragmentUpdateUserBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val arg= this.arguments
        if (arg!=null){
            id_user = arg.getInt(ConstantsUser.ID_USER)
        }
        GlobalScope.launch(Dispatchers.IO) {
            binding.editTextFirtName.setText(searchViewModel.searchUser(id_user).firstName)
            binding.editTextLastName.setText(searchViewModel.searchUser(id_user).lastName)
            binding.editTextEmail.setText(searchViewModel.searchUser(id_user).email)
            val job = searchViewModel.searchUser(id_user).job
            val avatar = searchViewModel.searchUser(id_user).avatar
            if(avatar.isNotBlank() || avatar.isNotEmpty()){
                Log.i("vacio",avatar.toString())
                lifecycleScope.launch {
                    Glide.with(this@UpdateUserFragment).load(avatar).into(binding.imageView)
                }
            }
            if (job.isNullOrEmpty()){
                binding.editTextJob.setText("")
            }
            else{
                binding.editTextJob.setText(job.toString())
            }
        }
        binding.buttonUpdate.setOnClickListener {
            if (ValidateEditText.areCreateUser(binding.editTextLastName,binding.editTextFirtName, binding.editTextEmail, binding.editTextJob)){
                if(ValidateEditText.doesNotContainEmoji(binding.editTextEmail)){
                    GlobalScope.launch {
                        val userUpdate = User(binding.editTextLastName.text.toString(),id_user,searchViewModel.searchUser(id_user).avatar,binding.editTextFirtName.text.toString(),binding.editTextEmail.text.toString(),"")
                        updateUserViewModel.updateUser(id_user = id_user, firstName = binding.editTextFirtName.text.toString(), lastName = binding.editTextLastName.text.toString(), email = binding.editTextEmail.text.toString(), job = binding.editTextJob.text.toString() )
                    }
                    requireActivity().supportFragmentManager.popBackStack()
                }else{
                    Toast.makeText(requireContext(),getString(R.string.emojis),Toast.LENGTH_SHORT).show()
                }

            }else{
                Toast.makeText(requireContext(),getString(R.string.editTextBlanck), Toast.LENGTH_SHORT).show()
            }



        }


        Log.i("INFO",id_user.toString())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UpdateUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpdateUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}