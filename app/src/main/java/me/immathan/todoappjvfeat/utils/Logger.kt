package me.immathan.todoappjvfeat.utils

import android.util.Log

/**
 * @author Mathan on 19/05/18
 */
object Logger {

    /**
     * Default log tag which can be used to filter the android logs.
     */
    private const val TAG = "TODO"

    private const val CHECK_TAG = "TODOJVFEAT"

    /**
     * The debug log can be turned on for a corresponding game.
     *
     *
     *
     * adb shell setprop log.tag.GG_GAME_ID DEBUG
     *
     *
     * The above command can be used to enable and disable log tags.
     */
    public var DEBUG = false

    fun i(tag: String, msg: String) {
        Log.i(getTag(tag), msg)
    }

    fun d(tag: String, msg: String) {
        if (DEBUG) {
            Log.v(getTag(tag), msg)
        }
    }

    fun d(tag: String, msg: String, throwable: Throwable) {
        if (DEBUG) {
            Log.v(getTag(tag), "[ERROR] " + msg + "\nError: " + throwable.localizedMessage)
        }
    }

    fun e(tag: String, msg: String) {
        Log.e(getTag(tag), msg)
    }

    fun e(tag: String, msg: String, e: Exception) {
        Log.e(getTag(tag), msg + "\nException: " + e.message)
        e.printStackTrace()
    }

    private fun getTag(tag: String): String {
        return "$TAG[${tag.logTrim()}]"
    }

    fun checkDebug() {
        DEBUG = Log.isLoggable(CHECK_TAG, Log.DEBUG)
    }

}