package com.coderefer.newyorktimesapp.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.coderefer.newyorktimesapp.R
import com.coderefer.newyorktimesapp.data.home.Post
import com.coderefer.newyorktimesapp.databinding.PostRecyclerviewItemBinding

class PostsRecyclerAdapter : ListAdapter<Post, PostsRecyclerAdapter.PostViewHolder>(PostsComparator()){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)
    }


    class PostViewHolder(binding: PostRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var post: Post? = null
        private var mBinding: PostRecyclerviewItemBinding = binding
        fun bind(post: Post) {
            this.post = post
            mBinding.textView.text = this.post?.name
        }
        companion object {
            fun create(parent : ViewGroup) : PostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: PostRecyclerviewItemBinding =
                    DataBindingUtil.inflate(inflater, R.layout.post_recyclerview_item, parent, false)
                return PostViewHolder(binding)
            }
        }

    }

    class PostsComparator : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
            return oldItem.name == newItem.name
        }

    }
}