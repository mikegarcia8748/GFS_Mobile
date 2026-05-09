# Business Rules: Attendance Migration

## BR-ATT-01: Multi-Business Line Entries
A worker is allowed to have multiple attendance entries on the same calendar day, provided they belong to different business lines. 
- *Scenario*: A worker can be marked as `PRESENT` for the Mill in the morning and `PRESENT` for a Harvester in the afternoon.

## BR-ATT-02: Daily Limit per Business Line
A worker cannot have more than one attendance entry per business line per day. Subsequent attempts to record attendance for the same line should update the existing record rather than create a duplicate.

## BR-ATT-03: Mandatory Admin Attribution
All attendance records must store the `entryBy` field (Admin User ID) to maintain accountability for financial calculations.

## BR-ATT-04: Legacy Data Compatibility
The system must support querying and displaying attendance records created before this modularization. These records will be mapped to a default business line (likely `MILL`) if the context is missing.

## BR-ATT-05: Status Impact on Payroll
Each `AttendanceStatus` has a specific weight for salary calculation:
- `PRESENT`: 1.0 (Full share)
- `HALFDAY`: 0.5 (Half share)
- `ABSENT`: 0.0 (No share)
- `LATE`: 1.0 (Full share, but flagged for audit)
