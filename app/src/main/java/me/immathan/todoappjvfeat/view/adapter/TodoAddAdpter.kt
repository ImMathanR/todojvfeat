package me.immathan.todoappjvfeat.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
import me.immathan.todoappjvfeat.R

/**
 * @author Mathan on 23/05/18
 */
class TodoAddAdpter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val NEW_TASK = 1
        const val EXISTING_TASK = 2
        const val FINISHED_TASK = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ExistingTaskViewHolder(null)
    }

    override fun getItemCount(): Int {
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

    }

    class FinishedViewHolder(var view: View) : AbstractDraggableItemViewHolder(view) {
        val dragIcon: ImageView = view.findViewById(R.id.dragIcon)
        val check: ImageView = view.findViewById(R.id.checkbox)
        val taskText: TextView = view.findViewById(R.id.taskText)
        val cross: ImageView = view.findViewById(R.id.cross)
    }

    class NewTaskViewHolder(var view: View) : AbstractDraggableItemViewHolder(view) {
        val dragIcon: ImageView = view.findViewById(R.id.dragIcon)
        val check: ImageView = view.findViewById(R.id.checkbox)
        val taskText: TextView = view.findViewById(R.id.taskText)
    }

    class ExistingTaskViewHolder(var view: View?) : AbstractDraggableItemViewHolder(view) {
        val dragIcon: ImageView = view!!.findViewById(R.id.dragIcon)
        val check: ImageView = view!!.findViewById(R.id.checkbox)
        val taskText: TextView = view!!.findViewById(R.id.taskText)
        val cross: ImageView = view!!.findViewById(R.id.cross)

    }

}