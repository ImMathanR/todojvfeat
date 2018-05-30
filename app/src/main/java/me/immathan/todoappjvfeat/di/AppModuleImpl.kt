package me.immathan.todoappjvfeat.di

import android.app.Application
import android.content.Context
import me.immathan.todoappjvfeat.data.*
import me.immathan.todoappjvfeat.domain.service.TodoApi
import me.immathan.todoappjvfeat.domain.service.networking.RestAdapter

/**
 * @author Mathan on 19/05/18
 */
class AppModuleImpl(private val application: Application) : AppModule {

    override val context: Context by lazy {
        application.applicationContext
    }

    override val todoApi: TodoApi by lazy {
        RestAdapter(context).todoApi
    }

    override val taskDao: TaskDao by lazy {
        TodoDatabase.getInstance(context).taskDao()
    }

    override val todoDao: TodoDao by lazy {
        TodoDatabase.getInstance(context).todoDao()
    }

    override val localTaskResponse: LocalTasksDaoStore by lazy {
        LocalTasksDaoStore(taskDao)
    }

    override val localTodoResponse: LocalTodoDaoStore by lazy {
        LocalTodoDaoStore(todoDao)
    }

}