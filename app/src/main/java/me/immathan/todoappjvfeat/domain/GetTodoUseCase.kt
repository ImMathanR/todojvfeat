package me.immathan.todoappjvfeat.domain

import io.reactivex.Flowable
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 23/05/18
 */
class GetTodoUseCase(private val localTodoRepository: LocalTodoRepository) {
    fun getTasks(): Flowable<List<Todo>> {
        return localTodoRepository.getTodos()
    }
}