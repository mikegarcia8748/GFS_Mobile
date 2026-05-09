package com.gfs.mobile.core.data.data.local.room.attendance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AttendanceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAttendance(attendance: AttendanceEntity)

    @Query("SELECT * FROM attendance WHERE date = :date")
    fun getAttendanceByDate(date: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE workerID = :workerID ORDER BY timestamp DESC")
    fun getAttendanceByWorker(workerID: String): Flow<List<AttendanceEntity>>

    @Query("SELECT * FROM attendance WHERE isSynced = 0")
    suspend fun getUnsyncedAttendance(): List<AttendanceEntity>

    @Query("UPDATE attendance SET isSynced = 1 WHERE id IN (:ids)")
    suspend fun markAsSynced(ids: List<Long>)
}
