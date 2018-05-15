package me.immathan.todoappjvfeat.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * @author Mathan on 15/05/18
 */
@Entity
data class Todo(@PrimaryKey(autoGenerate = true) var id: Long,
                @ColumnInfo(name = "task") var task: String,
                @ColumnInfo(name = "status") var status: String,
                @ColumnInfo(name = "ts") var ts: String,
                @ColumnInfo(name = "sync_pending") var syncPending: String)