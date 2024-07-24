package com.gmail.gabow95k.taski.base

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

open class BaseActivity : AppCompatActivity() {

    fun setFragment(fragment: Fragment, content: Int) {
        supportFragmentManager.beginTransaction()
            .replace(content, fragment)
            .commitNowAllowingStateLoss()
    }

    fun addFragment(fragment: Fragment, content: Int) {
        supportFragmentManager.beginTransaction()
            .replace(content, fragment)
            .addToBackStack(fragment.tag)
            .commitAllowingStateLoss()
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStackImmediate()
        } else {
            finish()
        }
    }
}