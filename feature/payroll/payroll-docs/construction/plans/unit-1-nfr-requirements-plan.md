# NFR Requirements Plan: Unit 1 - Attendance Migration

**Purpose**: Determine non-functional requirements and tech stack choices for the Attendance Migration unit.

## Planning Steps
- [x] Step 1: Analyze functional design for Unit 1.
- [x] Step 2: Answer clarifying questions in `construction/unit-1/nfr-questions.md`.
- [x] Step 3: Analyze answers and resolve ambiguities.
- [x] Step 4: Generate NFR requirements and tech stack decision artifacts.

---

## Clarifying Questions for NFR Requirements

### 1. Offline Capability
Since this is a local small business (Mill), how should attendance work if the device is offline?
A) **Local-First**: Store records in the local DB immediately and sync to backend when online (Required for reliability).
B) **Online-Only**: Require an active connection to mark attendance (Prevents data drift but risks downtime).
C) Other (please describe after [Answer]: tag below)

[Answer]: A

### 2. Concurrency & Data Integrity
How should the system handle two admins marking attendance for the same worker at the same time?
A) **Last Write Wins**: The most recent update overrides previous ones.
B) **Optimistic Locking**: Detect conflict and ask the user to refresh (Requires versioning).
C) **Atomic Operation**: The backend handles deduplication based on (Worker, Date, BusinessLine).
D) Other (please describe after [Answer]: tag below)

[Answer]: C

### 3. Image Capture Performance (Future-Proofing)
While Unit 1 is Attendance, Unit 3 (Expenses) will require image capture. Should we establish the image handling library now to ensure consistency?
A) **Standard CameraX**: Use Android's modern camera library for all image capture needs.
B) **Simple Intent**: Just launch the system camera and get the URI (Simplest implementation).
C) Other (please describe after [Answer]: tag below)

[Answer]: B

### 4. PBT Framework Selection (PBT-09)
As agreed, we are using Kotest for Property-Based Testing. Should we apply it specifically to the Attendance mapping and summary logic in this unit?
A) **Yes**: Ensure all attendance transformations are verified with Kotest PBT.
B) **No**: Only use traditional JUnit tests for this unit; save PBT for Unit 2 (Deductions).
C) Other (please describe after [Answer]: tag below)

[Answer]: A
