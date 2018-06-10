package me.immathan.todoappjvfeat.view.adapter

import android.support.v7.util.DiffUtil
import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 10/06/18
 */
class TasksDiffUtilCallback(private val oldList: List<Task>, private val newList: List<Task>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTask = oldList[oldItemPosition]
        val newTask = newList[newItemPosition]
        with(oldTask) {
            return id == newTask.id
        }
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldTask = oldList[oldItemPosition]
        val newTask = newList[newItemPosition]
        with(oldTask) {
            return id == newTask.id && status == newTask.status && task == newTask.task
        }
    }

}