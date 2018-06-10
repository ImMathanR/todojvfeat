package me.immathan.todoappjvfeat.domain

import io.reactivex.Completable
import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 09/06/18
 */
class UpdateTaskUseCase(private val localTaskRepository: LocalTaskRepository) {

    fun updateUseCase(task: Task): Completable {
        return localTaskRepository.update(task)
    }

}