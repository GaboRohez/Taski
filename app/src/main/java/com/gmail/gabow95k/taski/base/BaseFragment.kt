package com.gmail.gabow95k.taski.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = createBinding(inflater, container)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    abstract fun createBinding(inflater: LayoutInflater, container: ViewGroup?): VB

    protected fun setFragment(fragment: Fragment, content: Int) {
        (activity as? BaseActivity)?.setFragment(fragment, content)
    }

    protected fun addFragment(fragment: Fragment, content: Int) {
        (activity as? BaseActivity)?.addFragment(fragment, content)
    }

}