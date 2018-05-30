package me.immathan.todoappjvfeat.domain.service.networking

import android.content.Context
import com.google.gson.GsonBuilder
import me.immathan.todoappjvfeat.domain.service.TodoApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Mathan on 15/05/18
 */
class RestAdapter(context: Context) {

    private val retrofit: Retrofit

    val todoApi: TodoApi

    companion object {
        const val BASE_URL = "http://firebase.com"
    }

    init {
        val gson = GsonBuilder().create()

        retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        todoApi = retrofit.create(TodoApi::class.java)
    }

}