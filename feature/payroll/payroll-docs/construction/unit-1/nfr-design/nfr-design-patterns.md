# NFR Design Patterns: Unit 1 - Attendance Migration

This document details the non-functional design patterns applied to the Attendance Migration unit.

## 1. Resilience Patterns
### Exponential Backoff (WorkManager)
- **Pattern**: When the synchronization task fails due to a network error, the system will apply an **Exponential Backoff** strategy.
- **Initial Delay**: 30 seconds.
- **Rationale**: Prevents overwhelming the server during outages or high load while ensuring records are eventualy synced without manual intervention.

## 2. Performance Patterns
### Local-First Infinite Storage
- **Pattern**: Attendance records are stored indefinitely in the local Room database.
- **Optimization**: To ensure app performance as the database grows, the `AttendanceRepository` will use indexed queries and Room's reactive `Paging` library for UI displays if necessary.
- **Data Protection**: As requested, the "Clear Cache" and "Clear Data" functions in the system settings should be guarded or discouraged. If the user attempts to clear data, the app should provide a warning or redirect them back to ensure critical historical records (needed for payroll) are not accidentally deleted.

## 3. Security Patterns
### Session Pinning (Offline Security)
- **Pattern**: The `AuditLogger` and `AttendanceRepository` will use **Session Pinning** for offline record creation.
- **Implementation**: The last active Admin User ID is stored in a **Security-Crypto DataStore (Encrypted)**.
- **Workflow**:
    1. Admin logs in online -> ID is saved/refreshed.
    2. Admin records attendance while offline -> System retrieves the "Pinned" ID from Encrypted DataStore.
    3. The `entryBy` field is populated with this pinned ID.

## 4. Synchronization Strategy
### Immediate Eager Sync
- **Pattern**: The system uses an **Immediate Trigger** for synchronization.
- **Workflow**: As soon as an attendance record is saved to the local Room DB, a one-time `WorkManager` task is enqueued with `NetworkType.CONNECTED` constraints.
- **Benefit**: Ensures that data is reflected on the server as quickly as possible while maintaining a reliable local copy.
