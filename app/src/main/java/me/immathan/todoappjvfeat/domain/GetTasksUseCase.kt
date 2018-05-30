package me.immathan.todoappjvfeat.domain

import io.reactivex.Flowable
import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 19/05/18
 */
class GetTasksUseCase(private val localTaskRepository: LocalTaskRepository) {

    fun getTasks(): Flowable<List<Task>> {
        return localTaskRepository.getTasks()
    }

}