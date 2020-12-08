package com.coderefer.newyorktimesapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.coderefer.newyorktimesapp.data.home.Post
import com.coderefer.newyorktimesapp.data.home.PostRepo
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val postRepo: PostRepo) : ViewModel() {

    val allPosts: LiveData<List<Post>> = postRepo.allPosts.asLiveData()


    //    launching coroutine to insert data in a non-blocking way
    fun insert(post: Post) = viewModelScope.launch {
        postRepo.insert(post)
    }

}