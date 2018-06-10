package me.immathan.todoappjvfeat.utils

import android.content.Context
import android.graphics.Paint
import android.view.View
import android.widget.EditText
import android.widget.Toast


/**
 * @author Mathan on 19/05/18
 */
fun String.logTrim(): String {
    return substring(0, if (length > 7) 7 else length)
}

fun EditText.isEmpty(): Boolean {
    return text.toString().isEmpty()
}


fun EditText.notEmpty(): Boolean {
    return !text.toString().isEmpty()
}

fun EditText.strike(value: Boolean): Unit {
    paintFlags = if (value)
        paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
    else
        paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun EditText.removeStrike(): Unit {
    paintFlags = paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
}

fun View.timeout(value: Long, bar: () -> Unit) {
    postDelayed({
        bar()
    }, value)
}

fun Any.toast(context: Context, duration: Int = Toast.LENGTH_SHORT): Toast {
    return Toast.makeText(context, this.toString(), duration).apply { show() }
}