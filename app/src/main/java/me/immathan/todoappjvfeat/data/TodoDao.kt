package me.immathan.todoappjvfeat.data

import android.arch.persistence.room.*
import io.reactivex.Flowable
import me.immathan.todoappjvfeat.model.Todo

/**
 * @author Mathan on 23/05/18
 */
@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(todo: Todo): Long

    @Update
    fun update(todo: Todo)

    @Delete
    fun delete(todo: Todo)

    @Query("SELECT * FROM Todo ORDER BY ts DESC")
    fun getTodo(): Flowable<List<Todo>>

}