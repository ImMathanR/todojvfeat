package me.immathan.todoappjvfeat.domain

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 16/05/18
 */
interface LocalTaskRepository {

    fun add(task: String, todo: Todo): Single<Task>

    fun update(task: Task) : Completable

    fun delete(task: Task) : Completable

    fun getTasks(): Flowable<List<Task>>

    fun getTasks(todo: Todo): Flowable<List<Task>>

}