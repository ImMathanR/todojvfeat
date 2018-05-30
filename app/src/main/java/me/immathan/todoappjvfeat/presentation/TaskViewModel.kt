package me.immathan.todoappjvfeat.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.immathan.todoappjvfeat.domain.AddTaskUseCase
import me.immathan.todoappjvfeat.domain.GetTasksUseCase
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 19/05/18
 */
class TaskViewModel(private val addTaskUseCase: AddTaskUseCase,
                    private val getTasksUseCase: GetTasksUseCase) : ViewModel() {

    companion object {
        val TAG = TaskViewModel.javaClass.simpleName
    }

    private val todosLiveData = MutableLiveData<List<Task>>()

    private val disposable = CompositeDisposable()

    init {
        loadTasks()
    }

    override fun onCleared() {
        disposable.clear()
    }

    public fun addTasks(task: String, todo: Todo?) {
        disposable.add(addTaskUseCase.addTask(task, todo!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Logger.d(TAG, "Task added successfully") }, { Logger.d(TAG, "Task failed with error $it") }))
    }

    public fun allTasks(): LiveData<List<Task>> {
        return todosLiveData
    }

    private fun loadTasks() {
        disposable.add(getTasksUseCase.getTasks()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(TAG, "Todos prepared successfully")
                    todosLiveData.value = it
                }, { Logger.d(TAG, "Todos not able to receive $it") }))
    }

}