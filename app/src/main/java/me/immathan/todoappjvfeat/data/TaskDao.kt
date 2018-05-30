package me.immathan.todoappjvfeat.data

import android.arch.persistence.room.*
import io.reactivex.Flowable
import me.immathan.todoappjvfeat.model.Task


/**
 * @author Mathan on 15/05/18
 */
@Dao
interface TaskDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(task: Task): Long

    @Update
    fun update(task: Task)

    @Delete
    fun delete(task: Task)

    @Query("SELECT * FROM Task ORDER BY ts DESC")
    fun getTodo(): Flowable<List<Task>>

}