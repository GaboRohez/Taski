package com.gmail.gabow95k.taski.di

import android.app.Application
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class]
)
interface ComponentInjector {

    fun inject(viewModel: TaskiViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ComponentInjector

        @BindsInstance
        fun application(application: Application): Builder
    }
}