package me.immathan.todoappjvfeat.presentation

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import me.immathan.todoappjvfeat.domain.AddTaskUseCase
import me.immathan.todoappjvfeat.domain.GetTasksUseCase
import me.immathan.todoappjvfeat.domain.UpdateTaskUseCase
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 19/05/18
 */
class TaskViewModel(private val addTaskUseCase: AddTaskUseCase,
                    private val getTasksUseCase: GetTasksUseCase,
                    private val updateTaskUseCase: UpdateTaskUseCase) : ViewModel() {

    companion object {
        val TAG = TaskViewModel::class.java.simpleName
    }

    private val tasksLiveData = MutableLiveData<List<Task>>()

    private val disposable = CompositeDisposable()

    override fun onCleared() {
        disposable.clear()
    }

    public fun addTasks(task: String, todo: Todo?) {
        disposable.add(addTaskUseCase.addTask(task, todo!!)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ Logger.d(TAG, "Task added successfully") }, { Logger.d(TAG, "Task failed with error ${it.localizedMessage}") }))
    }

    fun updateTask(task: Task) {
        disposable.add(updateTaskUseCase.updateUseCase(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(TAG, "Task updated")
                }, {
                    Logger.d(TAG, "Task update failed ${it.localizedMessage}")
                }))
    }

    fun loadTasks(todo: Todo) {
        disposable.add(getTasksUseCase.getTasks(todo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Logger.d(TAG, "Todos prepared successfully")
                    tasksLiveData.value = it
                }, { Logger.d(TAG, "Todos not able to receive ${it.localizedMessage}") }))
    }

    fun allTasks(): LiveData<List<Task>> {
        return tasksLiveData
    }

}