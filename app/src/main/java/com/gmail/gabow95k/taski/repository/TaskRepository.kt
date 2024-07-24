package com.gmail.gabow95k.taski.repository

import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.data.TaskDao
import javax.inject.Inject

class TaskRepository @Inject constructor(private val taskDao: TaskDao) {

    val allTasks = taskDao.getAllTasks()

    val allTasksDone = taskDao.getAllTasksDone()

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }

    suspend fun update(task: Task) {
        taskDao.updateTask(task)
    }

    suspend fun delete(task: Task) {
        taskDao.deleteTask(task)
    }

    suspend fun searchTasks(query: String): List<Task> {
        return taskDao.searchTasks("%$query%")
    }

    suspend fun deleteAllCompletedTasks() {
        taskDao.deleteAllCompletedTasks()
    }
}