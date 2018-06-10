package me.immathan.todoappjvfeat.di

import android.arch.lifecycle.ViewModelProviders
import android.support.v4.app.FragmentActivity
import me.immathan.todoappjvfeat.domain.*
import me.immathan.todoappjvfeat.presentation.TaskViewModel
import me.immathan.todoappjvfeat.presentation.TaskViewModelFactory
import me.immathan.todoappjvfeat.presentation.TodoViewModel
import me.immathan.todoappjvfeat.presentation.TodoViewModelFactory

/**
 * @author Mathan on 19/05/18
 */
class TodoListActivityModuleImpl : TodoListActivityModule {

    override fun addTaskUseCase(localTaskRepository: LocalTaskRepository) = AddTaskUseCase(localTaskRepository)

    override fun updateTaskUseCase(localTaskRepository: LocalTaskRepository) = UpdateTaskUseCase(localTaskRepository)

    override fun getTasksUseCase(localTaskRepository: LocalTaskRepository) = GetTasksUseCase(localTaskRepository)

    override fun addTodoUseCase(localTodoRepository: LocalTodoRepository) = AddTodoUseCase(localTodoRepository)

    override fun getTodoUseCase(localTodoRepository: LocalTodoRepository) = GetTodoUseCase(localTodoRepository)

    override fun getTasksViewModelFactory(localTaskRepository: LocalTaskRepository) = TaskViewModelFactory(addTaskUseCase(localTaskRepository), getTasksUseCase(localTaskRepository), updateTaskUseCase(localTaskRepository))

    override fun getTodoViewModelFactory(localTodoRepository: LocalTodoRepository) = TodoViewModelFactory(addTodoUseCase(localTodoRepository), getTodoUseCase(localTodoRepository))

    override fun getTodoViewModel(fragmentActivity: FragmentActivity, viewModelFactory: TodoViewModelFactory) = ViewModelProviders.of(fragmentActivity, viewModelFactory).get(TodoViewModel::class.java)

    override fun getTaskViewModel(fragmentActivity: FragmentActivity, viewModelFactory: TaskViewModelFactory) = ViewModelProviders.of(fragmentActivity, viewModelFactory).get(TaskViewModel::class.java)

}