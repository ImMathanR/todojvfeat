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
import android.widget.LinearLayout
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemAdapter
import com.h6ah4i.android.widget.advrecyclerview.draggable.DraggableItemConstants
import com.h6ah4i.android.widget.advrecyclerview.draggable.ItemDraggableRange
import com.h6ah4i.android.widget.advrecyclerview.utils.AbstractDraggableItemViewHolder
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
class TodoAddAdapter : RecyclerView.Adapter<TodoAddAdapter.BaseDraggableHolder>(),
        DraggableItemAdapter<TodoAddAdapter.BaseDraggableHolder> {

    // NOTE: Make accessible with short name
    public interface Draggable : DraggableItemConstants

    init {
        setHasStableIds(true)
    }

    companion object {
        val TAG = TodoAddAdapter::class.java.simpleName!!
        const val NEW_TASK = 1
        const val EXISTING_TASK = 2
        const val FINISHED_TASK = 3
    }

    var tasks: MutableList<Task>? = null
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
            EXISTING_TASK, FINISHED_TASK -> {
                val task = tasks?.get(position)
                holder.taskText.setText(task?.task)
                holder.check.isChecked = task?.status!!
                holder.taskText.strike(task.status)
                // set background resource (target view ID: container)
                val dragState = holder.dragStateFlags

                if (dragState != 0 && DraggableItemConstants.STATE_FLAG_IS_UPDATED !== 0) {
                    val bgResId: Int

                    if (dragState and DraggableItemConstants.STATE_FLAG_IS_ACTIVE !== 0) {
                        //bgResId = R.drawable.bg_item_dragging_active_state

                        // need to clear drawable state here to get correct appearance of the dragging item.
                        //DrawableUtils.clearState(holder.mContainer.getForeground())
                    } else if (dragState and DraggableItemConstants.STATE_FLAG_DRAGGING !== 0 && dragState and DraggableItemConstants.STATE_FLAG_IS_IN_RANGE !== 0) {
                        //bgResId = R.drawable.bg_item_dragging_state
                    } else {
                        //bgResId = R.drawable.bg_item_normal_state
                    }

                    //holder.mContainer.setBackgroundResource(bgResId)
                }
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

    override fun getItemId(position: Int): Long {
        if (position <= tasks?.size!! - 1) {
            return tasks?.get(position)?.id!!
        }
        return 10001;
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

    override fun onGetItemDraggableRange(holder: BaseDraggableHolder?, position: Int): ItemDraggableRange {
        val freshTasks = tasks?.filter {
            !it.status
        }
        Logger.d(TAG, "position $position")
        Logger.d(TAG, "Fresh task size: ${freshTasks?.size}")
        if (tasks?.size == 0) {
            Logger.d(TAG, "Trying to move New task")
            return ItemDraggableRange(0, 0)
        }
        if (freshTasks?.size!! - 1 >= position) {
            Logger.d(TAG, "Dragging inside Existing tasks")
            return ItemDraggableRange(0, freshTasks.size - 1)
        }
        if (freshTasks.size - 1 < position) {
            Logger.d(TAG, "Dragging inside Finished tasks")
            return ItemDraggableRange(freshTasks.size, tasks?.size!!)
        }
        Logger.d(TAG, "Came out of all the conditions")
        return ItemDraggableRange(0, 0)
    }

    override fun onCheckCanStartDrag(holder: BaseDraggableHolder?, position: Int, x: Int, y: Int): Boolean {
        Logger.d(TAG, "OnCheckcanstartdrag: $position")
        if (position == tasks?.size) {
            Logger.d(TAG, "Dragging new task")
            return false
        }

        val offsetX = holder?.container?.left!! + (holder.container.translationX + 0.5f)
        val offsetY = holder.container.top + (holder.container.translationY + 0.5f)

        val result = hitTest(holder.dragIcon, (x - offsetX).toInt(), (y - offsetY).toInt());
        return result
    }

    override fun onItemDragStarted(position: Int) {
        Logger.d(TAG, "Item drag started $position")
        notifyDataSetChanged()
    }

    override fun onMoveItem(fromPosition: Int, toPosition: Int) {
        Logger.d(TAG, "on move item. From: $fromPosition - To: $toPosition")
        val task = tasks?.removeAt(fromPosition)
        tasks?.add(toPosition, task!!)
    }

    override fun onCheckCanDrop(draggingPosition: Int, dropPosition: Int): Boolean {
        Logger.d(TAG, "onCheckCanDrop dragginPosition: $draggingPosition and dropPosition: $dropPosition")
        if(tasks?.size!! -1 == dropPosition) {
            return false
        }
        return true
    }

    override fun onItemDragFinished(fromPosition: Int, toPosition: Int, result: Boolean) {
        Logger.d(TAG, "Item drag finished. fromPosition: $fromPosition - toPosition: $toPosition - result: $result")
        notifyDataSetChanged()
    }

    private fun hitTest(v: View, x: Int, y: Int): Boolean {
        val tx = (v.translationX + 0.5f).toInt()
        val ty = (v.translationY + 0.5f).toInt()
        val left = v.left + tx
        val right = v.right + tx
        val top = v.top + ty
        val bottom = v.bottom + ty

        return x in left..right && y >= top && y <= bottom
    }

    open class BaseDraggableHolder(open val view: View) : AbstractDraggableItemViewHolder(view) {

        val dragIcon: ImageView by lazy {
            view.findViewById(R.id.dragIcon) as ImageView
        }
        val check: AppCompatCheckBox by lazy {
            view.findViewById(R.id.checkbox) as AppCompatCheckBox
        }
        val taskText: EditText by lazy {
            view.findViewById(R.id.taskText) as EditText
        }
        val container: LinearLayout by lazy {
            view.findViewById(R.id.container) as LinearLayout
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