package com.coderefer.newyorktimesapp.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.coderefer.newyorktimesapp.R
import com.coderefer.newyorktimesapp.data.home.Post
import com.coderefer.newyorktimesapp.databinding.PostRecyclerviewItemBinding

class PostsRecyclerAdapter(private val posts: List<Post>?) :
    RecyclerView.Adapter<PostsRecyclerAdapter.PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: PostRecyclerviewItemBinding =
            DataBindingUtil.inflate(inflater, R.layout.post_recyclerview_item, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val postItem = posts?.get(position)
        postItem?.let { holder.bindComment(it) }
    }


    class PostViewHolder(binding: PostRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        private var post: Post? = null
        private var mBinding: PostRecyclerviewItemBinding = binding

        fun bindComment(comment: Post) {
            this.post = comment
            mBinding.textView.text = this.post?.name
        }
    }

    override fun getItemCount(): Int {
        return posts?.size ?: 0
    }
}