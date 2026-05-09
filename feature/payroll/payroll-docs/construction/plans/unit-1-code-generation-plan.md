# Code Generation Plan: Unit 1 - Attendance Migration

**Unit**: Unit 1 - Attendance Migration
**Purpose**: Consolidate existing attendance functionality into the new `:feature:payroll` module and implement local-first architecture with 2025 compliance.

## Context & Traceability
- **Stories**: US-03: Daily Attendance Tracking
- **Dependencies**: `:core:domain`, `:core:data`
- **Database Entities**: `Attendance` (Modified)
- **Service Boundaries**: Unified attendance recording for all business lines.

## Execution Steps

### Phase 1: Project Structure & Scaffolding
- [x] Step 1: Scaffold `:feature:payroll` module (build.gradle.kts, src/main/java).
- [x] Step 2: Include `:feature:payroll` in `settings.gradle.kts`.
- [x] Step 3: Add dependencies (Core modules, Hilt, Compose, Room, WorkManager) to `:feature:payroll`.

### Phase 2: Domain Layer (Core Domain)
- [x] Step 4: Define `AttendanceStatus` Enum in `:core:domain`.
- [x] Step 5: Update `AttendanceRecord` and `AttendanceSummary` models in `:core:domain`.
- [x] Step 6: Update `AttendanceRepository` interface to support multi-status and business lines.
- [x] Step 7: Update UseCases (`CreateAttendanceUseCase`, `GetAttendanceTodayUseCase`).

### Phase 3: Data Layer (Core Data)
- [x] Step 8: Update Room `AttendanceEntity` in `:core:data` with `businessLineID`, `status`, and `isSynced`.
- [x] Step 9: Update `AttendanceDao` with upsert logic for (Worker, Date, BusinessLine).
- [x] Step 10: Update `AttendanceRepositoryImpl` for Local-First logic.
- [x] Step 11: Implement `SecureSessionStore` (Encrypted DataStore) for offline Admin ID (SECURITY-12).
- [x] Step 12: Implement `AttendanceSyncWorker` using WorkManager for background sync.

### Phase 4: Business Logic & ViewModel (Payroll Module)
- [x] Step 13: Create `AttendanceViewModel` in `:feature:payroll` following MVI pattern.
- [x] Step 14: Implement logic for switching business lines and tagging workers.
- [x] Step 15: Implement daily summary aggregation logic.

### Phase 5: Frontend Components (Payroll Module)
- [x] Step 16: Create unified `AttendanceScreen.kt` using Jetpack Compose.
- [x] Step 17: Implement `BusinessLineSelector` and `WorkerAttendanceItem` components.
- [x] Step 18: Add `data-testid` to interactive elements for automation (Automation Friendly Rule).

### Phase 6: Unit Testing & Summary
- [x] Step 19: Implement PBT for `AttendanceSummary` mapping using Kotest (PBT-01, PBT-02).
- [x] Step 20: Clean up legacy attendance files in `:feature:dashboard` and `:feature:ricemill`.
- [ ] Step 21: Generate Unit 1 Code Generation Summary.
