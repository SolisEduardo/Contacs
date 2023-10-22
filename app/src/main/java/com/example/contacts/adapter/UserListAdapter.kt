package com.example.contacts.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.contacts.R
import com.example.contacts.data.remote.model.response.ListUserResponse
import com.example.contacts.databinding.CardPresentationBinding
import com.example.contacts.domain.model.User

class UserListAdapter (private val context: Context, private val userClick: (String) -> Unit) : RecyclerView.Adapter<UserListAdapter.SearchViewHolder>(){
    private var personList: List<User> = emptyList()

    fun setData(list: List<User>) {
        personList = list
        notifyDataSetChanged()
    }
    fun updateRecycler(list: List<User>){
        this.personList = list
        notifyDataSetChanged()
    }
    fun remoteUser(list : ArrayList<User>, position: Int){
        list.remove(list[position])
        this.personList = list
        notifyItemRemoved(position)
    }

    interface SwipeListener {
        fun onItemSwipedLeft(item: User) // Acción de swipe hacia la izquierda
        fun onItemSwipedRight(item: User) // Acción de swipe hacia la derecha
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
            Glide.with(holder.itemView.context).load(R.drawable.hombre).into(binding.imgPerson)
        }
        else{
            Glide.with(holder.itemView.context).load(persons.avatar).into(binding.imgPerson)
        }
        if (persons.job.isEmpty() || persons.job.isBlank()){
            binding.txtJob.visibility = View.GONE
        }
        else{
            binding.txtJob.visibility = View.VISIBLE
            binding.txtJob.text = "${persons.job}"
        }
        holder.itemView.setOnClickListener {
            userClick((persons.email))
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