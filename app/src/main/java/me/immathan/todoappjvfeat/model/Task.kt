package me.immathan.todoappjvfeat.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

/**
 * @author Mathan on 15/05/18
 */
@Entity(foreignKeys = [(ForeignKey(entity = Todo::class,
        parentColumns = arrayOf("group_id"),
        childColumns = arrayOf("group_id")))])
data class Task(@PrimaryKey(autoGenerate = true) var id: Long = 0,
                @ColumnInfo(name = "task") var task: String,
                @ColumnInfo(name = "status") var status: Boolean = false,
                @ColumnInfo(name = "ts") var ts: Long = System.currentTimeMillis(),
                @ColumnInfo(name = "sync_pending") var syncPending: Boolean = false,
                @ColumnInfo(name = "group_id") var groupId: Long = 0)