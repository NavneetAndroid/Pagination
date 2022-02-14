package com.example.retrofiturl

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.retrofiturl.model.User

class UsersAdapter :RecyclerView.Adapter<UsersAdapter.UsersViewHolder>() {
 private val list=ArrayList<User>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.item_user,parent,false)
        return UsersViewHolder(view)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int=list.size
    class UsersViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        fun bind(user: User){
            with(itemView){
             tvEmail.text=user.email
                tvName.text=user.first_name

            }
        }
        val tvEmail =itemView.findViewById<TextView>(R.id.tvEmail)
        val tvName =itemView.findViewById<TextView>(R.id.tvName)



    }
    fun addList(items: ArrayList<User>){
        list.addAll(items)
        notifyDataSetChanged()
    }
    fun clear(){
        list.clear()
        notifyDataSetChanged()
    }
}