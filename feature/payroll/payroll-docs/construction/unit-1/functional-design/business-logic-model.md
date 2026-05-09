# Business Logic Model: Attendance Migration

## Workflow: Recording Attendance
1. **Selection**: Admin selects a worker from the unified list.
2. **Context Selection**: Admin chooses the current business line (Default: Mill).
3. **Status Selection**: Admin selects the status (PRESENT, ABSENT, etc.).
4. **Validation**:
    - Check if a record for this (Worker, Date, BusinessLine) already exists.
    - If exists: Perform Update.
    - If not: Perform Create.
5. **Persistence**: Save the record with the Admin ID and current timestamp.

## Workflow: Retrieving Today's Summary
1. **Aggregation**: Query all attendance records for the current date.
2. **Grouping**: Group records by `workerID`.
3. **Mapping**: Convert the raw records into the `AttendanceSummary` view model, listing all business lines the worker participated in today.

## Workflow: Legacy Data Query
1. **Union Query**: Query records from the new `Attendance` table and the old legacy table.
2. **Standardization**: Map legacy fields (`date`, `entryBy`) to the new entity format, defaulting `businessLineID` to `MILL` and `status` to `PRESENT` (since legacy was binary).

## Testable Properties (PBT)
- **Round-Trip**: `save(attendance) -> get(attendance)` should always yield identical data.
- **Invariance**: The total number of entries in `AttendanceSummary` must equal the sum of entries for all business lines.
- **Idempotence**: Recording the same status for the same worker/line/date twice should result in exactly one database record.
