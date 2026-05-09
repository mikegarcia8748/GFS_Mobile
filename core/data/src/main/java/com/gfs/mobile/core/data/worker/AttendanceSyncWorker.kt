package com.gfs.mobile.core.data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.gfs.mobile.core.domain.repository.AttendanceRepository
import com.gfs.mobile.core.domain.util.NetworkResource
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.last

@HiltWorker
class AttendanceSyncWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val repository: AttendanceRepository
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        return try {
            val result = repository.syncUnsyncedRecords().last()
            if (result is NetworkResource.Success) {
                Result.success()
            } else {
                Result.retry()
            }
        } catch (e: Exception) {
            Result.retry()
        }
    }
}
