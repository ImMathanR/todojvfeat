package me.immathan.todoappjvfeat.data

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import me.immathan.todoappjvfeat.domain.LocalTodoRepository
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 23/05/18
 */
class LocalTodoDaoStore(private val todoDao: TodoDao) : LocalTodoRepository {

    companion object {
        val TAG = LocalTodoDaoStore::class.java.simpleName!!
    }

    override fun add(todo: String): Single<Todo> {
        Logger.d(TAG, "Adding task: $todo")
        return Single.fromCallable({
            val todo = Todo(todo = todo)
            val rowId = todoDao.add(todo)
            return@fromCallable todo.copy(groupId = rowId)
        })
    }

    override fun update(todo: Todo): Completable {
        Logger.d(TAG, "Updating Task")
        return Completable.fromAction({ todoDao.update(todo) })
    }

    override fun delete(todo: Todo): Completable {
        Logger.d(TAG, "Deleting Task")
        return Completable.fromAction({ todoDao.delete(todo) })
    }

    override fun getTodos(): Flowable<List<Todo>> {
        Logger.d(TAG, "Retrieving Todos")
        return todoDao.getTodo()
    }

}