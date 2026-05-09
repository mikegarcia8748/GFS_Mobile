# Functional Design Plan: Unit 1 - Attendance Migration

**Purpose**: Design the consolidated business logic for general and mill-specific attendance tracking within the Payroll module.

## Planning Steps
- [x] Step 1: Define consolidated Attendance domain models and relationships.
- [x] Step 2: Design business logic for recording and retrieving attendance.
- [x] Step 3: Design frontend component structure for the unified attendance screen.
- [x] Step 4: Answer clarifying questions for functional design.
- [x] Step 5: Generate functional design artifacts.

---

## Clarifying Questions for Functional Design

### 1. Attendance Status
Currently, the `MillAttendanceViewModel` has `tagAsPresent` and `tagAsAbsent`, but both call `createAttendanceUseCase` with the same params. Is there a status field missing in `CreateAttendanceParams`?
A) **Binary Status**: Yes, we should add an `isPresent: Boolean` field to `CreateAttendanceParams`.
B) **Multi-Status**: We should use an Enum (e.g., PRESENT, ABSENT, LATE, HALFDAY).
C) **Implicit**: The backend determines status based on the time of entry or specific endpoint.
D) Other (please describe after [Answer]: tag below)

[Answer]: B

### 2. Business Line Context
For Unit 1, how should we pass the business line context (Mill, Harvester, Ploughing) to the attendance recording logic?
A) **Explicit Parameter**: Every attendance record should include a `businessLineID`.
B) **User Profile**: The system infers the business line from the worker's current active profile.
C) **Implicit by Screen**: The specific screen used (e.g., Mill Attendance) hardcodes the context.
D) Other (please describe after [Answer]: tag below)

[Answer]: A

### 3. Business Rules for Overlapping Entries
Should a worker be allowed to have multiple attendance entries on the same day for different business lines?
A) **Restrictive**: No, a worker can only be marked once per day across the entire system.
B) **Flexible**: Yes, a worker might work at the Mill in the morning and a Harvester in the afternoon.
C) **Limited**: Only if the time ranges do not overlap (requires start/end time tracking).
D) Other (please describe after [Answer]: tag below)

[Answer]: B

### 4. Historical Data Migration
When moving logic to the new module, do we need to support viewing attendance history created before the modularization?
A) **Legacy Support**: Yes, the new logic must be able to query old records from the centralized database.
B) **Clean Slate**: No, start fresh with the new module's structure.
C) Other (please describe after [Answer]: tag below)

[Answer]:A
