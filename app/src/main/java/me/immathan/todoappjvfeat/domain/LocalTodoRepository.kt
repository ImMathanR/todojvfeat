package me.immathan.todoappjvfeat.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 23/05/18
 */
interface LocalTodoRepository {

    fun add(todo: String): Single<Todo>

    fun update(todo: Todo): Completable

    fun delete(todo: Todo): Completable

    fun getTodos(): Flowable<List<Todo>>

}