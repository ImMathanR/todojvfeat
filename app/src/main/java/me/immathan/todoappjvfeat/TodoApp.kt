package me.immathan.todoappjvfeat

import android.app.Application
import android.arch.persistence.db.SupportSQLiteDatabase
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

    fun setInMemoryRoomDatabases(vararg database: SupportSQLiteDatabase) {
        if (BuildConfig.DEBUG) {
            try {
                val debugDB = Class.forName("me.immathan.todoappjvfeat.data.TodoDatabase")
                val argTypes = arrayOf<Class<*>>(HashMap::class.java)
                val inMemoryDatabases = hashMapOf<String, SupportSQLiteDatabase>()
                // set your inMemory databases
                inMemoryDatabases.put("InMemoryOne.db", database[0])
                val setRoomInMemoryDatabase = debugDB.getMethod("setInMemoryRoomDatabases", *argTypes)
                setRoomInMemoryDatabase.invoke(null, inMemoryDatabases)
            } catch (ignore: Exception) {

            }

        }
    }

}