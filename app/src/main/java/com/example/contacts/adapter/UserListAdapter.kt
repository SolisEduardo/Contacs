package com.example.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.data.remote.model.response.ListUserResponse
import com.example.contacts.databinding.CardPresentationBinding
import com.example.contacts.domain.model.User

class UserListAdapter (private val context: Context, private val userClick: (Int) -> Unit) : RecyclerView.Adapter<UserListAdapter.SearchViewHolder>(){
    private var personList: List<User> = emptyList()
    // private var personTest : List<ListUserResponse> = emptyList()
    private lateinit var testPerson : ListUserResponse

    fun setData(list: List<User>) {
        personList = list
        notifyDataSetChanged()
    }
    fun updateRecycler(list: List<User>){
        this.personList = list
        notifyDataSetChanged()
    }




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val binding =
            CardPresentationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SearchViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return personList.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        val binding = holder.binding
        val persons = personList[position]
        //val test = personTest[position]
        binding.txtName.text = "${persons.firstName} ${persons.lastName}"
        binding.txtEmail.text = "${persons.email}"
        if (persons.avatar == ""){
            Glide.with(holder.itemView.context).load(R.drawable.ic_launcher_foreground).into(binding.imgPerson)
        }
        else{
            Glide.with(holder.itemView.context).load(persons.avatar).into(binding.imgPerson)
        }
        holder.itemView.setOnClickListener {
            userClick((persons.id!!))
        }
        /*for ( i in test.data!!.iterator()){
            binding.txtName.text = "${i!!.firstName} ${i!!.lastName}"
            binding.txtEmail.text = "${i!!.email}"
            Glide.with(holder.itemView.context).load(i!!.avatar).into(binding.imgPerson)
        }*/
    }

    class SearchViewHolder(val binding: CardPresentationBinding) :
        RecyclerView.ViewHolder(binding.root)
}