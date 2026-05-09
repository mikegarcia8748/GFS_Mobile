package com.gfs.mobile.core.data.data.mapper

import com.gfs.mobile.core.data.data.local.room.attendance.AttendanceEntity
import com.gfs.mobile.core.domain.model.attendance.AttendanceRecord
import com.gfs.mobile.core.domain.model.attendance.AttendanceStatus
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.arbitrary
import io.kotest.property.arbitrary.enum
import io.kotest.property.arbitrary.long
import io.kotest.property.arbitrary.next
import io.kotest.property.arbitrary.orNull
import io.kotest.property.arbitrary.string
import io.kotest.property.checkAll

class AttendanceMapperPbt : StringSpec({

    val statusArb = Arb.enum<AttendanceStatus>()
    
    val recordArb = arbitrary {
        AttendanceRecord(
            attendanceID = Arb.string().orNull().bind(),
            workerID = Arb.string().bind(),
            businessLineID = Arb.string().bind(),
            date = Arb.string().bind(),
            status = statusArb.bind(),
            entryBy = Arb.string().bind(),
            timestamp = Arb.long().bind()
        )
    }

    "AttendanceRecord should survive mapping round-trip to Entity" {
        // PBT-02: Round-Trip Properties
        checkAll(recordArb) { record ->
            val entity = record.toEntity(isSynced = true)
            val domain = entity.toDomain()
            
            domain.attendanceID shouldBe record.attendanceID
            domain.workerID shouldBe record.workerID
            domain.businessLineID shouldBe record.businessLineID
            domain.date shouldBe record.date
            domain.status shouldBe record.status
            domain.entryBy shouldBe record.entryBy
            domain.timestamp shouldBe record.timestamp
        }
    }
})
