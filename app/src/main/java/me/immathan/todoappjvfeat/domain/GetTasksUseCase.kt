package me.immathan.todoappjvfeat.domain

import io.reactivex.Flowable
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 19/05/18
 */
class GetTasksUseCase(private val localTaskRepository: LocalTaskRepository) {

    fun getTasks(): Flowable<List<Task>> {
        return localTaskRepository.getTasks()
    }

    fun getTasks(todo: Todo): Flowable<List<Task>> {
        return localTaskRepository.getTasks(todo);
    }

}