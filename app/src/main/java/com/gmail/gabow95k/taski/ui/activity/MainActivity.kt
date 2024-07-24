package com.gmail.gabow95k.taski.ui.activity

import android.os.Bundle
import com.gmail.gabow95k.taski.R
import com.gmail.gabow95k.taski.base.BaseActivity
import com.gmail.gabow95k.taski.databinding.ActivityMainBinding
import com.gmail.gabow95k.taski.ui.fragment.create.TaskBottomSheet
import com.gmail.gabow95k.taski.ui.fragment.dashboar.DashboardFragment
import com.gmail.gabow95k.taski.ui.fragment.done.DoneFragment
import com.gmail.gabow95k.taski.ui.fragment.search.SearchFragment
import com.gmail.gabow95k.taski.ui.listener.OnBackPressedListener

class MainActivity : BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_todo -> {
                    setFragment(DashboardFragment.newInstance(), R.id.contentMain)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_create -> {
                    TaskBottomSheet().show(supportFragmentManager, TaskBottomSheet.TAG)
                    return@setOnNavigationItemSelectedListener false
                }

                R.id.navigation_search -> {
                    setFragment(SearchFragment.newInstance(), R.id.contentMain)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.navigation_done -> {
                    setFragment(DoneFragment.newInstance(), R.id.contentMain)
                    return@setOnNavigationItemSelectedListener true
                }
            }
            false
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.navigation_todo
        }
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.contentMain)
        if (fragment is OnBackPressedListener) {
            if (!fragment.onBackPressed()) {
                super.onBackPressed()
            }
        } else {
            super.onBackPressed()
        }
    }

}