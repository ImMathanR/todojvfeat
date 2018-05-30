package me.immathan.todoappjvfeat.view

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_todo_add.*
import me.immathan.todoappjvfeat.R
import me.immathan.todoappjvfeat.TodoApp
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger
import me.immathan.todoappjvfeat.utils.isEmpty
import me.immathan.todoappjvfeat.view.base.BaseActivity

class TodoAddActivity : BaseActivity() {

    companion object {
        val TAG = TodoAddActivity.javaClass.simpleName!!
    }

    private val appComponent by lazy {
        (application as TodoApp).appComponent
    }

    private val todoViewModel by lazy {
        val todoViewModelFactory = appComponent.getTodoViewModelFactory(appComponent.localTodoResponse)
        appComponent.getTodoViewModel(this@TodoAddActivity, todoViewModelFactory)
    }

    private val taskViewModel by lazy {
        val taskViewFactory = appComponent.getTasksViewModelFactory(appComponent.localTaskResponse)
        appComponent.getTaskViewModel(this@TodoAddActivity, taskViewFactory)
    }

    private var activeTodo: Todo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_add)

        titleET.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && !titleET.isEmpty()) {
                todoViewModel.addTodo(titleET.text.toString()) {
                    activeTodo = it
                    Logger.d(TAG, "Todo added: ${activeTodo?.groupId} ${activeTodo?.todo}")
                }
            }
        }

        color.setOnClickListener {
            todoViewModel.addTodo(titleET.text.toString()) {
                activeTodo = it
                Logger.d(TAG, "Todo added: ${activeTodo?.groupId} ${activeTodo?.todo}")
            }
        }
    }



}
