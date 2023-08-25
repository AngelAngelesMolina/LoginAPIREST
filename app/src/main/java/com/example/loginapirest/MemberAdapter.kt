package com.example.loginapirest

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import androidx.recyclerview.widget.RecyclerView
import com.example.loginapirest.databinding.ItemMemberBinding

class MemberAdapter(
    private var members: List<Member>
) : RecyclerView.Adapter<MemberAdapter.ViewHolder>() {

    private lateinit var mContext: Context

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemMemberBinding.bind(view) //habilitar el viewbinding con los item del rv
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mContext = parent.context
        val view = LayoutInflater.from(mContext).inflate(R.layout.item_member, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = members.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        Log.i("Miembros:", members.toString())
        val member = members.get(position)
        with(holder) {
            binding.tvName.text = member.first_name + member.last_name
            binding.tvEmail.text = member.email
            Glide.with(mContext)
                .load(member.avatar)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .circleCrop()
                .into(binding.ivAvatar)
        }
    }

    fun setMembers(members: MutableList<Member>) {
        this.members = members
        notifyDataSetChanged()
    }
}