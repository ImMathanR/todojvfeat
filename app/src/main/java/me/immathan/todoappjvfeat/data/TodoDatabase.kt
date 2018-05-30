package me.immathan.todoappjvfeat.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo


/**
 * @author Mathan on 15/05/18
 */
@Database(entities = [(Task::class), (Todo::class)], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    companion object {
        const val DB_NAME = "todo_db"

        var instance: TodoDatabase? = null

        @Synchronized
        fun getInstance(context: Context): TodoDatabase {
            if (instance == null) {
                instance = Room
                        .databaseBuilder(context.applicationContext, TodoDatabase::class.java, DB_NAME)
                        .build()
            }
            return instance as TodoDatabase
        }
    }

    abstract fun taskDao(): TaskDao

    abstract fun todoDao(): TodoDao
}