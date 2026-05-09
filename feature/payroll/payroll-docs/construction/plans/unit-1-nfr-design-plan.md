# NFR Design Plan: Unit 1 - Attendance Migration

**Purpose**: Incorporate non-functional requirements into the detailed design patterns and components for Unit 1.

## Planning Steps
- [x] Step 1: Analyze NFR requirements for Unit 1.
- [x] Step 2: Answer clarifying questions in `construction/unit-1/nfr-design-questions.md`.
- [x] Step 3: Analyze answers and resolve ambiguities.
- [x] Step 4: Generate NFR design artifacts (patterns and logical components).

---

## Clarifying Questions for NFR Design

### 1. Resilience & Retry Strategy
For the background synchronization of attendance records, what should be the retry policy if the server is unreachable?
A) **Exponential Backoff**: Standard WorkManager retry policy starting at 30 seconds (Prevents server hammering).
B) **Fixed Interval**: Retry every 10 minutes regardless of failure count.
C) **Manual-Only**: Notify the admin and wait for them to trigger a sync manually.
D) Other (please describe after [Answer]: tag below)

[Answer]: 

### 2. Performance & Data Pruning
Since we are using a local-first Room database, how long should attendance records be kept locally before pruning to ensure app performance?
A) **Infinite**: Keep all records locally for historical reference (Requires large storage).
B) **Recent Only**: Keep 3 months of records locally; older data is only accessible via backend query.
C) **Weekly**: Only keep current and previous week (Aligns with the payroll cycle).
D) Other (please describe after [Answer]: tag below)

[Answer]: 

### 3. Security & Admin Context
The `AuditLogger` requires the Admin User ID. How should this ID be retrieved for records created while the device is offline?
A) **Session Pinning**: Store the last active Admin ID in the Encrypted DataStore and use it for offline entries.
B) **Strict Re-Auth**: Prevent attendance entry if the offline session has expired (Requires periodic login).
C) Other (please describe after [Answer]: tag below)

[Answer]: 

### 4. Logical Synchronization Trigger
When should the `WorkManager` attempt to sync local attendance records?
A) **Immediate**: Attempt sync as soon as a record is saved (If internet is available).
B) **Periodic**: Sync once every hour in the background.
C) **Lifecycle-Based**: Sync whenever the admin opens or closes the app.
D) Other (please describe after [Answer]: tag below)

[Answer]:
