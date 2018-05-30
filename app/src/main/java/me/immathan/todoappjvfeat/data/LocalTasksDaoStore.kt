package me.immathan.todoappjvfeat.data

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.immathan.todoappjvfeat.domain.LocalTaskRepository
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 19/05/18
 */
class LocalTasksDaoStore(private val taskDao: TaskDao) : LocalTaskRepository {

    companion object {
        val TAG = LocalTasksDaoStore.javaClass.simpleName!!
    }

    override fun add(task: String, todo: Todo): Single<Task> {
        Logger.d(TAG, "Adding task: $task")
        return Single.fromCallable({
            val taskObj = Task(task = task, groupId = todo.groupId)
            val rowId = taskDao.add(taskObj)
            return@fromCallable taskObj.copy(id = rowId)
        })
    }

    override fun update(task: Task): Completable {
        Logger.d(TAG, "Updating Task")
        return Completable.fromAction({ taskDao.update(task) })
    }

    override fun delete(task: Task): Completable {
        Logger.d(TAG, "Deleting Task")
        return Completable.fromAction({ taskDao.delete(task) })
    }

    override fun getTasks(): Flowable<List<Task>> {
        Logger.d(TAG, "Retrieving Todos")
        return taskDao.getTodo()
    }

}