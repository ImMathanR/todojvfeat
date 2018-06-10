package me.immathan.todoappjvfeat.di

import android.support.v4.app.FragmentActivity
import me.immathan.todoappjvfeat.domain.*
import me.immathan.todoappjvfeat.presentation.TaskViewModel
import me.immathan.todoappjvfeat.presentation.TaskViewModelFactory
import me.immathan.todoappjvfeat.presentation.TodoViewModel
import me.immathan.todoappjvfeat.presentation.TodoViewModelFactory

/**
 * @author Mathan on 19/05/18
 */
interface TodoListActivityModule {

    fun addTaskUseCase(localTaskRepository: LocalTaskRepository): AddTaskUseCase

    fun getTasksUseCase(localTaskRepository: LocalTaskRepository): GetTasksUseCase

    fun addTodoUseCase(localTodoRepository: LocalTodoRepository): AddTodoUseCase

    fun getTodoUseCase(localTodoRepository: LocalTodoRepository): GetTodoUseCase

    fun updateTaskUseCase(localTaskRepository: LocalTaskRepository): UpdateTaskUseCase

    fun getTasksViewModelFactory(localTaskRepository: LocalTaskRepository): TaskViewModelFactory

    fun getTodoViewModelFactory(localTodoRepository: LocalTodoRepository): TodoViewModelFactory

    fun getTodoViewModel(fragmentActivity: FragmentActivity, viewModelFactory: TodoViewModelFactory): TodoViewModel

    fun getTaskViewModel(fragmentActivity: FragmentActivity, viewModelFactory: TaskViewModelFactory): TaskViewModel

}