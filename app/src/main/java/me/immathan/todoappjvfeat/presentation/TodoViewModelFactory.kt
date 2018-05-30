package me.immathan.todoappjvfeat.presentation

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import me.immathan.todoappjvfeat.domain.AddTodoUseCase
import me.immathan.todoappjvfeat.domain.GetTodoUseCase

/**
 * @author Mathan on 23/05/18
 */
/**
 * @author Mathan on 19/05/18
 */
class TodoViewModelFactory(private val addTodoUseCase: AddTodoUseCase,
                           private val getTodoUseCase: GetTodoUseCase) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoViewModel::class.java)) {
            return TodoViewModel(addTodoUseCase, getTodoUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}