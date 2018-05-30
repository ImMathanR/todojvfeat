package me.immathan.todoappjvfeat.domain.service

import com.jakewharton.rxrelay2.PublishRelay
import io.reactivex.Observable
import me.immathan.todoappjvfeat.model.Task

/**
 * @author Mathan on 16/05/18
 */
class SyncTasksRxBus {

    val relay: PublishRelay<SyncTaskResponse> = PublishRelay.create()

    fun post(syncResponseEventType: SyncResponseEventType, task: Task) {
        relay.accept(SyncTaskResponse(syncResponseEventType, task))
    }

    fun toObservable(): Observable<SyncTaskResponse> {
        return relay
    }

}