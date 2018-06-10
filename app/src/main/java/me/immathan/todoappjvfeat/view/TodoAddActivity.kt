package me.immathan.todoappjvfeat.view

import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.util.DiffUtil
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.activity_todo_add.*
import me.immathan.todoappjvfeat.R
import me.immathan.todoappjvfeat.TodoApp
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.utils.Logger
import me.immathan.todoappjvfeat.utils.isEmpty
import me.immathan.todoappjvfeat.utils.timeout
import me.immathan.todoappjvfeat.utils.toast
import me.immathan.todoappjvfeat.view.adapter.TasksDiffUtilCallback
import me.immathan.todoappjvfeat.view.adapter.TodoAddAdapter
import me.immathan.todoappjvfeat.view.base.BaseActivity

class TodoAddActivity : BaseActivity() {

    companion object {
        val TAG = TodoAddActivity::class.java.simpleName!!
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

    private var lifecycleRegistry = LifecycleRegistry(this)

    private val todoAdapter: TodoAddAdapter by lazy {
        TodoAddAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_add)

        todoRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        todoRecyclerView.adapter = todoAdapter
        todoAdapter.tasks = mutableListOf()
        todoAdapter.notifyDataSetChanged()
        todoAdapter.taskViewModel = taskViewModel

        titleET.requestFocus()

        taskViewModel.allTasks().observe(this, Observer { tasks ->
            Logger.d(TAG, "Tasks observed")
            var tasksList = tasks?.sortedBy({ it.ts })

            tasksList = tasksList?.sortedWith(compareBy { it.status })
            Logger.d(TAG, "$tasksList")
            val diffResult = DiffUtil.calculateDiff(TasksDiffUtilCallback(todoAdapter.tasks!!, tasksList!!))
            diffResult.dispatchUpdatesTo(todoAdapter)

            todoAdapter.tasks = tasksList
        })

        titleET.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            if (!hasFocus && !titleET.isEmpty()) {
                if (activeTodo == null) {
                    todoViewModel.addTodo(titleET.text.toString()) {
                        activeTodo = it
                        taskViewModel.loadTasks(activeTodo!!)
                        todoAdapter.todo = activeTodo

                        Logger.d(TAG, "Todo added: ${activeTodo?.groupId} ${activeTodo?.todo}")
                    }
                } else { // tasks already added, update the todoo

                }
            }
        }

        titleET.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {
                if (activeTodo == null) {
                    if (!titleET.isEmpty()) {
                        todoViewModel.addTodo(titleET.text.toString()) {
                            activeTodo = it
                            taskViewModel.loadTasks(activeTodo!!)
                            todoAdapter.todo = activeTodo
                            todoRecyclerView.isFocusable = true
                            todoAdapter.notifyDataSetChanged()

                            Logger.d(TAG, "Todo added: ${activeTodo?.groupId} ${activeTodo?.todo}")
                        }
                        return@setOnEditorActionListener true
                    } else {
                        "Please enter a valid Title".toast(applicationContext)
                        return@setOnEditorActionListener false
                    }

                } else { // tasks already added, update the todoo

                }
            }
            return@setOnEditorActionListener false
        })

        color.setOnClickListener {
            todoViewModel.addTodo(titleET.text.toString()) {
                activeTodo = it
                Logger.d(TAG, "Todo added: ${activeTodo?.groupId} ${activeTodo?.todo}")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        titleET.timeout(100, {
            titleET.requestFocus()
        })
    }

}
