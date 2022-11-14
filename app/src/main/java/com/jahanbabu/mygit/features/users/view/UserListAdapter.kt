package com.jahanbabu.mygit.features.users.view

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.jahanbabu.mygit.R
import com.jahanbabu.mygit.databinding.RowUserBinding
import com.jahanbabu.mygit.features.users.model.User
import jp.wasabeef.glide.transformations.CropCircleWithBorderTransformation


class UserListAdapter(private val onUserSelect: (String) -> Unit): PagingDataAdapter<User, UserListAdapter.UsersViewHolder>(DiffCallback){

    class UsersViewHolder(
        val binding: RowUserBinding
    ):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
    val binding = RowUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return UsersViewHolder(binding)
}

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        getItem(position)?.let { user->
            holder.binding.title.text = user.login.uppercase()
            Glide.with(holder.binding.root.context)
                .load(user.avatarUrl)
                .apply(RequestOptions.bitmapTransform(CropCircleWithBorderTransformation(1, Color.GRAY)))
                .placeholder(R.drawable.github_logo)
                .into(holder.binding.mediaImage)

            holder.itemView.setOnClickListener {
                onUserSelect(user.login)
            }
        }
    }

    /*
     * Allows the RecyclerView to determine which items have changed when the list
     * has been updated.
     */
    companion object DiffCallback : DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.userId == newItem.userId
        }


        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
             return oldItem == newItem  //Auto generated equality check from data classes
        }
    }
}