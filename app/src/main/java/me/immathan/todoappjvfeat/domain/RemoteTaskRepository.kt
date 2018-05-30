package me.immathan.todoappjvfeat.domain

import io.reactivex.Completable
import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 16/05/18
 */
interface RemoteTaskRepository {
    fun sync(task: Task): Completable
}