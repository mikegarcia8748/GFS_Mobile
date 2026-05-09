# NFR Requirements: Unit 1 - Attendance Migration

## Reliability & Offline Capability
- **Local-First Architecture**: The system must prioritize local data persistence. Attendance records must be stored in the local Room database immediately upon entry.
- **Background Synchronization**: A synchronization mechanism must be implemented to push local records to the backend once a network connection is available.
- **Data Integrity**: Concurrency must be handled via **Atomic Operations** on the backend. The backend must deduplicate entries based on the triplet of (Worker, Date, BusinessLine) to prevent data corruption from multiple admin entries.

## Security & Compliance
- **Audit Traceability**: Every attendance record must explicitly store the `entryBy` (Admin User ID) and a server-side timestamp to ensure accountability for salary calculations.
- **Access Control**: Only authorized Admin users may record or modify attendance data.
- **Data Protection**: Employee names and IDs must be handled securely in accordance with the project's Security Baseline.

## Maintainability & Testing
- **Property-Based Testing (PBT)**: All attendance data transformations, especially those involving mapping legacy records and generating consolidated summaries, must be verified using **Kotest PBT**.
- **Unified Logic**: The migration must eliminate the logic duplication currently existing between `:feature:dashboard` and `:feature:ricemill`.
