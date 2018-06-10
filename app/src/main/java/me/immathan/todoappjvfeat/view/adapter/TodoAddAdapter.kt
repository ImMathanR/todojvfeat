package me.immathan.todoappjvfeat.view.adapter

import android.graphics.Typeface
import android.support.v7.widget.AppCompatCheckBox
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.ImageView
import me.immathan.todoappjvfeat.R
import me.immathan.todoappjvfeat.model.Task
import me.immathan.todoappjvfeat.model.Todo
import me.immathan.todoappjvfeat.presentation.TaskViewModel
import me.immathan.todoappjvfeat.utils.Logger
import me.immathan.todoappjvfeat.utils.isEmpty
import me.immathan.todoappjvfeat.utils.strike
import me.immathan.todoappjvfeat.utils.toast


/**
 * @author Mathan on 23/05/18
 */
class TodoAddAdapter : RecyclerView.Adapter<TodoAddAdapter.BaseDraggableHolder>() {

    companion object {
        val TAG = TodoAddAdapter::class.java.simpleName!!
        const val NEW_TASK = 1
        const val EXISTING_TASK = 2
        const val FINISHED_TASK = 3
    }

    var tasks: List<Task>? = null
    var todo: Todo? = null

    var taskViewModel: TaskViewModel? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseDraggableHolder {
        return when (viewType) {
            NEW_TASK -> NewTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_new_add_item, parent, false))
            EXISTING_TASK -> ExistingTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_exiting_add_item, parent, false))
            FINISHED_TASK -> FinishedViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_finished_add_item, parent, false))
            else -> NewTaskViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.todo_new_add_item, parent, false))
        }
    }

    override fun getItemCount(): Int {
        tasks?.let {
            return tasks?.size!! + 1
        }

        return 1
    }

    override fun onBindViewHolder(holder: BaseDraggableHolder, position: Int) {
        when (getItemViewType(position)) {
            NEW_TASK -> {
                holder.taskText.setText("")
                holder.taskText.requestFocus()
                holder.taskText.addTextChangedListener(object : TextWatcher {

                    override fun afterTextChanged(p0: Editable?) {
                        if (holder.taskText.text.toString().isEmpty()) {
                            holder.taskText.setTypeface(null, Typeface.ITALIC)
                        } else {
                            holder.taskText.setTypeface(null, Typeface.NORMAL)
                        }
                    }

                    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                    }

                    override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    }
                })
            }
            EXISTING_TASK -> {
                val task = tasks?.get(position)
                holder.taskText.setText(task?.task)
                holder.check.isChecked = task?.status!!
                holder.taskText.strike(task.status)
            }
            FINISHED_TASK -> {
                val task = tasks?.get(position)
                holder.taskText.setText(task?.task)
                holder.check.isChecked = task?.status!!
                holder.taskText.strike(task.status)
            }
        }

        holder.check.setOnClickListener({
            if (getItemViewType(position) != NEW_TASK) {
                val task = tasks?.get(position)?.copy()
                task?.status = holder.check.isChecked
                taskViewModel?.updateTask(task!!)
            }
        })

        holder.taskText.setOnEditorActionListener({ v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_NEXT) {

                if (!holder.taskText.isEmpty()) {
                    if (todo != null) {
                        taskViewModel?.addTasks(holder.taskText.text.toString(), todo)
                        holder.taskText.setText("")
                        Logger.d(TAG, "Task added")
                    } else {
                        "Please enter a valid Title".toast(holder.taskText.context)
                        return@setOnEditorActionListener false
                    }
                }

                return@setOnEditorActionListener true
            }
            return@setOnEditorActionListener false
        })

    }

    override fun getItemViewType(position: Int): Int {
        if (tasks == null || tasks!!.isEmpty() || position == tasks!!.size) {
            // This means there is no Todos and we are going to display NEW_TASK view holder
            return NEW_TASK
        }

        val todo = tasks!![position]
        return if (todo.status)
            FINISHED_TASK
        else
            EXISTING_TASK
    }

    open class BaseDraggableHolder(open val view: View) : RecyclerView.ViewHolder(view) {
        val dragIcon: ImageView by lazy {
            view.findViewById(R.id.dragIcon) as ImageView
        }
        val check: AppCompatCheckBox by lazy {
            view.findViewById(R.id.checkbox) as AppCompatCheckBox
        }
        val taskText: EditText by lazy {
            view.findViewById(R.id.taskText) as EditText
        }
    }

    class FinishedViewHolder(override var view: View) : BaseDraggableHolder(view) {
        val cross: ImageView by lazy {
            view.findViewById(R.id.cross) as ImageView
        }
    }

    class NewTaskViewHolder(override var view: View) : BaseDraggableHolder(view)

    class ExistingTaskViewHolder(override var view: View) : BaseDraggableHolder(view) {
        val cross: ImageView by lazy {
            view.findViewById(R.id.cross) as ImageView
        }
    }

}