package me.immathan.todoappjvfeat.view

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LifecycleRegistry
import android.arch.lifecycle.Observer
import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_todo_list.*
import kotlinx.android.synthetic.main.content_todo_list.*
import me.immathan.todoappjvfeat.R
import me.immathan.todoappjvfeat.TodoApp
import me.immathan.todoappjvfeat.view.base.BaseActivity


class TodoListActivity : BaseActivity(), LifecycleOwner {

    private val registry = LifecycleRegistry(this)

    override fun getLifecycle(): LifecycleRegistry {
        return registry
    }

    private val appComponent by lazy {
        (application as TodoApp).appComponent
    }

    private val todoViewModel by lazy {
        val todoViewModelFactory = appComponent.getTodoViewModelFactory(appComponent.localTodoResponse)
        appComponent.getTodoViewModel(this@TodoListActivity, todoViewModelFactory)
    }

    private val taskViewModel by lazy {
        val taskViewFactory = appComponent.getTasksViewModelFactory(appComponent.localTaskResponse)
        appComponent.getTaskViewModel(this@TodoListActivity, taskViewFactory)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_todo_list)
        setSupportActionBar(toolbar)

        taskViewModel.allTasks().observe(this, Observer {
            var text = ""
            it!!.forEach {
                text += it.task + "\n"
            }
            helloText.text = text
        })

        addTodo.setOnClickListener({

        })

        addTask.setOnClickListener({
            if (!TextUtils.isEmpty(newTask.text)) {
                taskViewModel.addTasks(newTask.text.toString(), null)
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_todo_list, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
