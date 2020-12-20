package com.coderefer.newyorktimesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coderefer.newyorktimesapp.R
import com.coderefer.newyorktimesapp.data.database.entity.PostAndMultiMedia
import com.coderefer.newyorktimesapp.databinding.PostRecyclerviewItemBinding

class PostsRecyclerAdapter : ListAdapter<PostAndMultiMedia, PostsRecyclerAdapter.PostViewHolder>(POSTS_COMPARATOR) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        return PostViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = getItem(position)
        holder.bind(postItem)
    }


    class PostViewHolder(binding: PostRecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private var postsWithMultiMedia: PostAndMultiMedia? = null
        private var mBinding: PostRecyclerviewItemBinding = binding
        fun bind(postsWithMultiMedia: PostAndMultiMedia) {
            this.postsWithMultiMedia = postsWithMultiMedia
            mBinding.textView.text = this.postsWithMultiMedia?.post?.title
            Glide.with(mBinding.root.context).load(postsWithMultiMedia.multiMedia[0].url).into(mBinding.ivPost)
        }

        companion object {
            fun create(parent: ViewGroup): PostViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding: PostRecyclerviewItemBinding =
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.post_recyclerview_item,
                        parent,
                        false
                    )
                return PostViewHolder(binding)
            }
        }

    }

    companion object {
        private val POSTS_COMPARATOR = object : DiffUtil.ItemCallback<PostAndMultiMedia>() {
            override fun areItemsTheSame(oldItem: PostAndMultiMedia, newItem: PostAndMultiMedia): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PostAndMultiMedia, newItem: PostAndMultiMedia): Boolean {
                return oldItem.post.title == newItem.post.title
            }
        }
    }

}