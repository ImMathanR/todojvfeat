package me.immathan.todoappjvfeat.domain.service

import me.immathan.todoappjvfeat.model.Task
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * @author Mathan on 16/05/18
 */
interface TodoApi {

    @POST("/tasks")
    fun addTodo(@Body task: Task): Call<Task>

}