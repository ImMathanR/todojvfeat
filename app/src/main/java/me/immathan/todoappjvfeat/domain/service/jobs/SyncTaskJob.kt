package me.immathan.todoappjvfeat.domain.service.jobs

import com.firebase.jobdispatcher.JobParameters
import com.firebase.jobdispatcher.JobService

/**
 * @author Mathan on 16/05/18
 */
class SyncTaskJob : JobService() {

    override fun onStartJob(job: JobParameters?): Boolean {
        return false
    }

    override fun onStopJob(job: JobParameters?): Boolean {
        return false
    }

}