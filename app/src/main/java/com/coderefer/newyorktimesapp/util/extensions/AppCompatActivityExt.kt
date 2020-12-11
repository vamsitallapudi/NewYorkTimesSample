/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coderefer.newyorktimesapp.util.extensions

/**
 * Various extension functions for AppCompatActivity.
 */

import android.app.Activity
import androidx.activity.ComponentActivity
import androidx.annotation.IdRes
import androidx.annotation.MainThread
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelLazy
import androidx.lifecycle.ViewModelProvider
import com.coderefer.newyorktimesapp.R

const val ADD_EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 1
const val DELETE_RESULT_OK = Activity.RESULT_FIRST_USER + 2
const val EDIT_RESULT_OK = Activity.RESULT_FIRST_USER + 3

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean, animationReqd : Boolean = false) {
    supportFragmentManager.transact {
        if(animationReqd) setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        if(addToBackStack)
            replace(frameId, fragment).addToBackStack(null)
        else
            replace(frameId,fragment)
    }
}

/**
 * The `fragment` is added to the container view with id `frameId`. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.replaceFragmentWithTag(fragment: Fragment, frameId: Int, tag: String, addToBackStack: Boolean, animationReqd: Boolean = false) {
    supportFragmentManager.transact {
        if(animationReqd) setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left, R.anim.enter_from_left, R.anim.exit_to_right)
        if(addToBackStack)
            replace(frameId, fragment, tag).addToBackStack(tag)
        else
            replace(frameId,fragment,tag)
    }
}

/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragment(fragment: Fragment, frameId: Int, addToBackStack: Boolean) {
    supportFragmentManager.transact {
        if(addToBackStack)
            add(frameId, fragment).addToBackStack(null)
        else
            add(frameId,fragment)
    }
}/**
 * The `fragment` is added to the container view with tag. The operation is
 * performed by the `fragmentManager`.
 */
fun AppCompatActivity.addFragmentWithTag(fragment: Fragment, tag: String, addToBackStack: Boolean) {
    supportFragmentManager.transact {
        if(addToBackStack)
            add(fragment, tag).addToBackStack(null)
        else
            add(fragment, tag)
    }
}

fun AppCompatActivity.setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
    setSupportActionBar(findViewById(toolbarId))
    supportActionBar?.run {
        action()
    }
}

//fun <T : ViewModel> AppCompatActivity.obtainLoginViewModel(viewModelClass: Class<T>) =
//        ViewModelProviders.of(this, LoginViewModelFactory.getInstance(application)).get(viewModelClass)

/**
 * Runs a FragmentTransaction, then calls commit().
 */
private inline fun FragmentManager.transact(action: FragmentTransaction.() -> Unit) {
    beginTransaction().apply {
        action()
    }.commitAllowingStateLoss()
}