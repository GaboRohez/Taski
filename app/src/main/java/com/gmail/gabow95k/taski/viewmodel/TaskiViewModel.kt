package com.gmail.gabow95k.taski.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmail.gabow95k.taski.base.BaseViewModel
import com.gmail.gabow95k.taski.data.Task
import com.gmail.gabow95k.taski.repository.TaskRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class TaskiViewModel : BaseViewModel() {

    @Inject
    lateinit var taskRepository: TaskRepository

    val allTasks = taskRepository.allTasks

    val allTasksDone = taskRepository.allTasksDone

    private val _taskInsertResult = MutableLiveData<Boolean>()
    val taskInsertResult: LiveData<Boolean> get() = _taskInsertResult

    private val _taskUpdateResult = MutableLiveData<Boolean>()
    val taskUpdateResult: LiveData<Boolean> get() = _taskUpdateResult

    private val _taskDeleteResult = MutableLiveData<Boolean>()
    val taskDeleteResult: LiveData<Boolean> get() = _taskDeleteResult

    private val _searchResults = MutableLiveData<List<Task>>()
    val searchResults: LiveData<List<Task>> get() = _searchResults

    private var currentQuery: String = ""

    fun insert(task: Task) = viewModelScope.launch {
        try {
            taskRepository.insert(task)
            _taskInsertResult.postValue(true)
            if (currentQuery.isNotEmpty()) {
                _searchResults.value = taskRepository.searchTasks(currentQuery)
            }
        } catch (e: Exception) {
            _taskInsertResult.postValue(false)
        }
    }

    fun update(task: Task) = viewModelScope.launch {
        try {
            taskRepository.update(task)
            _taskUpdateResult.postValue(true)
            if (currentQuery.isNotEmpty()) {
                _searchResults.value = taskRepository.searchTasks(currentQuery)
            }
        } catch (e: Exception) {
            _taskUpdateResult.postValue(false)
        }
    }

    fun delete(task: Task) = viewModelScope.launch {
        try {
            taskRepository.delete(task)
            _taskDeleteResult.postValue(true)
            if (currentQuery.isNotEmpty()) {
                _searchResults.value = taskRepository.searchTasks(currentQuery)
            }
        } catch (e: Exception) {
            _taskDeleteResult.postValue(false)
        }
    }

    fun searchTasks(query: String) = viewModelScope.launch {
        currentQuery = query

        if (query.isEmpty()) {
            _searchResults.value = emptyList()
        } else {
            _searchResults.value = taskRepository.searchTasks(query)
        }
    }

    fun deleteAllCompletedTasks() = viewModelScope.launch {
        try {
            taskRepository.deleteAllCompletedTasks()
            _taskDeleteResult.postValue(true)
        } catch (e: Exception) {
            _taskDeleteResult.postValue(false)
        }
    }

    fun resetTaskResults() {
        _taskInsertResult.value = false
        _taskUpdateResult.value = false
        _taskDeleteResult.value = false
    }
}