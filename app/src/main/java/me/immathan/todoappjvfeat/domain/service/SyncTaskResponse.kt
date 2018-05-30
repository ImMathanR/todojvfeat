package me.immathan.todoappjvfeat.domain.service

import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 16/05/18
 */
data class SyncTaskResponse(var syncResponseEventType: SyncResponseEventType,
                            var task: Task)