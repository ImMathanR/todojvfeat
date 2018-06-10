package me.immathan.todoappjvfeat.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.immathan.todoappjvfeat.domain.AddTaskUseCase
import me.immathan.todoappjvfeat.domain.GetTasksUseCase
import me.immathan.todoappjvfeat.domain.UpdateTaskUseCase


/**
 * @author Mathan on 19/05/18
 */
class TaskViewModelFactory(private val addTaskUseCase: AddTaskUseCase,
                           private val getTasksUseCase: GetTasksUseCase,
                           private val updateTaskUseCase: UpdateTaskUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TaskViewModel::class.java)) {
            return TaskViewModel(addTaskUseCase, getTasksUseCase, updateTaskUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}