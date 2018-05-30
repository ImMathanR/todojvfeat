package me.immathan.todoappjvfeat.di

import android.content.Context
import me.immathan.todoappjvfeat.data.LocalTasksDaoStore
import me.immathan.todoappjvfeat.data.LocalTodoDaoStore
import me.immathan.todoappjvfeat.data.TaskDao
import me.immathan.todoappjvfeat.data.TodoDao
import me.immathan.todoappjvfeat.domain.service.TodoApi

/**
 * @author Mathan on 16/05/18
 */
interface AppModule {

    val context: Context
    val todoApi: TodoApi
    val taskDao: TaskDao
    val todoDao: TodoDao
    val localTaskResponse: LocalTasksDaoStore
    val localTodoResponse: LocalTodoDaoStore

}