package com.coderefer.newyorktimesapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coderefer.newyorktimesapp.NYTApp
import com.coderefer.newyorktimesapp.R
import com.coderefer.newyorktimesapp.data.home.PostRepo
import com.coderefer.newyorktimesapp.databinding.ActivityHomeBinding
import com.coderefer.newyorktimesapp.util.extensions.replaceFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityHomeBinding
    private lateinit var viewModelFactory: HomeViewModelFactory
    private lateinit var viewModel : HomeViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        replaceFragment(obtainViewFragment(), R.id.frag_container, true)
//        TODO: replace with di
        viewModelFactory = HomeViewModelFactory((application as NYTApp).repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
    }

    private fun obtainViewFragment() =
        supportFragmentManager.findFragmentById(R.id.frag_container) ?: HomeFragment.newInstance()

    fun obtainViewModel() = viewModel

}