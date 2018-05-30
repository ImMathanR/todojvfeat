package me.immathan.todoappjvfeat.domain

import io.reactivex.Single
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 23/05/18
 */
class AddTodoUseCase(private val localTodoRepository: LocalTodoRepository) {

    fun addTodo(todo: String): Single<Todo> {
        return localTodoRepository.add(todo)
    }

}