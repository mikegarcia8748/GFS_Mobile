# Logical Components: Unit 1 - Attendance Migration

This document identifies the infrastructure components and integration patterns required for the non-functional design.

## 1. AttendanceSyncWorker (WorkManager)
- **Component**: A `CoroutineWorker` implementation.
- **Responsibilities**: 
    - Fetch unsynced records from Room.
    - Post records to the API.
    - Handle retries via `Result.retry()`.
- **Constraint**: Requires `NetworkType.CONNECTED`.

## 2. SecureSessionStore (DataStore)
- **Component**: `DataStore<Preferences>` with `security-crypto-datastore`.
- **Responsibilities**:
    - Store the `pinnedAdminID` for offline attribution.
    - Store synchronization metadata (e.g., `lastSuccessfulSyncTimestamp`).

## 3. AttendanceRoomDatabase
- **Component**: Room Database.
- **Table Structure**: Includes an `isSynced: Boolean` flag on the `Attendance` table to track local vs. remote state.
- **Indexing**: Composite index on `(workerID, date, businessLineID)` to ensure query performance and facilitate the "Update or Create" business rule.

## 4. AuditLogger
- **Pattern**: Interceptor/Service.
- **Logic**: Automatically captures the metadata for every recording action, regardless of connectivity status, using the `SecureSessionStore`.
