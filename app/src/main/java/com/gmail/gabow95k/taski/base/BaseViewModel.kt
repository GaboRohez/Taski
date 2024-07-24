package com.gmail.gabow95k.taski.base

import androidx.lifecycle.ViewModel
import com.gmail.gabow95k.taski.TaskiApplication
import com.gmail.gabow95k.taski.di.ComponentInjector
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel

abstract class BaseViewModel : ViewModel() {

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is TaskiViewModel -> TaskiApplication.component.inject(this)
        }
    }
}