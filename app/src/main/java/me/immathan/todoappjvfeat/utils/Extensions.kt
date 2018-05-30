package me.immathan.todoappjvfeat.utils

import android.widget.EditText

/**
 * @author Mathan on 19/05/18
 */
fun String.logTrim(): String {
    return substring(0, if (length > 7) 7 else length)
}

fun EditText.isEmpty(): Boolean {
    return text.toString().isEmpty()
}
