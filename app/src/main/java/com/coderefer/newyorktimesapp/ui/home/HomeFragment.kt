package com.coderefer.newyorktimesapp.ui.home


import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.coderefer.newyorktimesapp.data.database.entity.Post
import com.coderefer.newyorktimesapp.databinding.FragmentHomeBinding
import kotlinx.coroutines.Job
import com.coderefer.newyorktimesapp.data.Result
import com.coderefer.newyorktimesapp.data.database.entity.PostAndMultiMedia


class HomeFragment : Fragment() {
    private lateinit var mBinding: FragmentHomeBinding
    private var job: Job? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        mBinding = FragmentHomeBinding.inflate(inflater, container, false).apply {
            viewmodel = (activity as HomeActivity).obtainViewModel()
        }
        val adapter = initRecyclerAdapter()
        fetchPosts()//TODO change to db version check
        observePostsLiveData(adapter)
        observeUIState()
        return mBinding.root
    }

    private fun observeUIState() {
        mBinding.viewmodel!!.uiState.observe(viewLifecycleOwner, Observer {
            val uiModel = it ?: return@Observer
            if (uiModel.showProgress) {
                beginDelayedTransition()
            }

            if (uiModel.showError != null && !uiModel.showError.consumed) {
//                TODO
//                uiModel.showError.consume()?.let { showLoginFailed(it) }
            }
            if (uiModel.showSuccess != null && !uiModel.showSuccess.consumed) {
//                TODO
//                uiModel.showSuccess.consume()?.let { updateUiWithUser(it) }
//                setResult(Activity.RESULT_OK)
//                finish()
            }
        })
    }

//    private fun fetchPosts(adapter: PostsRecyclerAdapter) {
//        mBinding.viewmodel!!.allPosts.observe(
//            this,
//            Observer<List<Post>> {
//                if (it == null) {
////                    db is null need to call fetchPostsFromNetwork
//                    fetchPostsFromNetwork()
//                } else {
//                    val storiesList = it
//                    populateAdapter(adapter, storiesList)
//                }
//
//            }
//        )
//    }

    private fun fetchPosts() {
        job = mBinding.viewmodel!!.fetchPosts()
    }

    private fun observePostsLiveData(adapter: PostsRecyclerAdapter) {
        mBinding.viewmodel!!.postsLiveData.observe(
            this,
            Observer { fetchPostResult ->
                when (fetchPostResult) {
                    is Result.Success -> {
                        val storiesList = fetchPostResult.data as ArrayList<PostAndMultiMedia>
                        populateAdapter(adapter, storiesList)
                    }
                    is Result.Error -> handleError()
                    is Result.Loading -> TODO()
                }

            }
        )
    }

    private fun handleError() {
        Log.d(HomeFragment::class.java.simpleName, "error occured")
    }

    private fun populateAdapter(adapter: PostsRecyclerAdapter,list:List<PostAndMultiMedia>) {
        adapter.submitList(list)
    }

    private fun initRecyclerAdapter() : PostsRecyclerAdapter {
        val adapter = PostsRecyclerAdapter()
        mBinding.recyclerview.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(activity)
        }
        return adapter
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    private fun beginDelayedTransition() {
//        TODO: show transition
//        TransitionManager.beginDelayedTransition(binding.container)
    }

}