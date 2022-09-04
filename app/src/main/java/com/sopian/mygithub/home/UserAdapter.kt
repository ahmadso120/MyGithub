package com.sopian.mygithub.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopian.mygithub.core.domain.model.SearchUsers
import com.sopian.mygithub.core.utils.loadPhotoUrl
import com.sopian.mygithub.databinding.UserItemBinding

class UserAdapter (
    private val onItemClick: (SearchUsers) -> Unit
) : ListAdapter<SearchUsers, UserAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        val binding: UserItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            UserItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.apply {
            root.setOnClickListener {
                onItemClick(item)
            }
            userImage.loadPhotoUrl(item.avatarUrl)
            loginTv.text = item.login
        }
    }
}

object DiffCallback : DiffUtil.ItemCallback<SearchUsers>() {
    override fun areItemsTheSame(
        oldItem: SearchUsers,
        newItem: SearchUsers
    ): Boolean  = oldItem == newItem

    override fun areContentsTheSame(
        oldItem: SearchUsers,
        newItem: SearchUsers
    ): Boolean = oldItem.id == newItem.id
}