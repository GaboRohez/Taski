package com.gmail.gabow95k.taski.di

import android.app.Application
import androidx.room.Room
import com.gmail.gabow95k.taski.Constants
import com.gmail.gabow95k.taski.data.TaskDao
import com.gmail.gabow95k.taski.data.TaskDatabase
import com.gmail.gabow95k.taski.repository.TaskRepository
import com.gmail.gabow95k.taski.viewmodel.TaskiViewModel
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideTaskDatabase(application: Application): TaskDatabase {
        return Room.databaseBuilder(
            application,
            TaskDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideTaskDao(database: TaskDatabase): TaskDao {
        return database.taskDao()
    }

    @Provides
    @Singleton
    fun provideTaskRepository(taskDao: TaskDao): TaskRepository {
        return TaskRepository(taskDao)
    }

    @Provides
    fun provideTaskiViewModel(): TaskiViewModel {
        return TaskiViewModel()
    }
}