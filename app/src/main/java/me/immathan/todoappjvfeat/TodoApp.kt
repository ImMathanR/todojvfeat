package me.immathan.todoappjvfeat

import android.app.Application
import me.immathan.todoappjvfeat.di.*
import me.immathan.todoappjvfeat.utils.Logger

/**
 * @author Mathan on 15/05/18
 */

class TodoApp : Application() {

    val appComponent: AppComponent by lazy {
        object : AppComponent,
                AppModule by AppModuleImpl(this),
                TodoListActivityModule by TodoListActivityModuleImpl() {}
    }

    override fun onCreate() {
        super.onCreate()
        Logger.DEBUG = true
    }

}