package me.immathan.todoappjvfeat.domain

import io.reactivex.Completable
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 19/05/18
 */
class AddTaskUseCase(private val localTaskRepository: LocalTaskRepository) {

    fun addTask(task: String, todo: Todo): Completable {
        return localTaskRepository.add(task, todo).toCompletable()
    }

}