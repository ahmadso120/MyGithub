package com.sopian.mygithub.detail

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.sopian.mygithub.core.data.source.remote.response.UserRepositoriesResponse
import com.sopian.mygithub.core.domain.model.UserRepositories
import com.sopian.mygithub.core.utils.formatTimeAgo
import com.sopian.mygithub.databinding.RepositoriesItemBinding

class RepositoriesAdapter :
    ListAdapter<UserRepositories, RepositoriesAdapter.ViewHolder>(DiffCallback) {

    class ViewHolder(
        private val binding: RepositoriesItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserRepositories) {
            binding.apply {
                nameTv.text = item.name
                descriptionTv.text = item.description
                starCountTv.text = item.stargazersCount.toString()
                updatedTv.text = "Updated " + item.updatedAt.formatTimeAgo()
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        Log.d("RepositoriesAdapter", item.toString())
        if (item != null) {
            holder.bind(item)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RepositoriesItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }
}

object DiffCallback : DiffUtil.ItemCallback<UserRepositories>() {
    override fun areItemsTheSame(
        oldItem: UserRepositories,
        newItem: UserRepositories
    ): Boolean  = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: UserRepositories,
        newItem: UserRepositories
    ): Boolean = oldItem == newItem
}