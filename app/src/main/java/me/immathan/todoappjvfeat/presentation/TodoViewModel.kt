package me.immathan.todoappjvfeat.presentation

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.immathan.todoappjvfeat.domain.AddTodoUseCase
import me.immathan.todoappjvfeat.domain.GetTodoUseCase
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 23/05/18
 */
class TodoViewModel(private val addTodoUseCase: AddTodoUseCase,
                    private val getTodoUseCase: GetTodoUseCase) : ViewModel() {

    companion object {
        val TAG = TodoViewModel::class.java.simpleName
    }

    private val todosLiveData = MutableLiveData<List<Todo>>()

    private val disposable = CompositeDisposable()

    public override fun onCleared() {
        disposable.clear()
        loadTodos()
    }

    public fun todos(): MutableLiveData<List<Todo>> {
        return todosLiveData
    }

    public fun addTodo(todo: String, todoAdded: (Todo) -> Unit) {
        disposable.add(addTodoUseCase
                .addTodo(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(TAG, "Todo added successfully")
                    todoAdded.invoke(it)
                }, { Logger.d(TaskViewModel.TAG, "Todo failed with error $it") }))
    }

    public fun updateTodo(todoModal: Todo) {

    }

    private fun loadTodos() {
        disposable.add(getTodoUseCase.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(TaskViewModel.TAG, "Todos prepared successfully")
                    todosLiveData.value = it
                }, { Logger.d(TaskViewModel.TAG, "Todos not able to receive $it") }))
    }

}