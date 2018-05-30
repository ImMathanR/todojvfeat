package me.immathan.todoappjvfeat.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author Mathan on 20/05/18
 */
@Entity
data class Todo(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "group_id") var groupId: Long = 0,
                @ColumnInfo(name = "todo") var todo: String,
                @ColumnInfo(name = "status") var status: Boolean = false,
                @ColumnInfo(name = "ts") var ts: Long = System.currentTimeMillis(),
                @ColumnInfo(name = "sync_pending") var syncPending: Boolean = false)