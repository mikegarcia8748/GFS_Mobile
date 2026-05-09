# Domain Entities: Attendance Migration

## Attendance Status (Enum)
Defines the possible states for a worker's daily attendance entry.
- `PRESENT`: Worker is physically present and working.
- `ABSENT`: Worker is not present.
- `LATE`: Worker arrived after the expected start time.
- `HALFDAY`: Worker only performed half a day's worth of work.

## Attendance Record (Entity)
Represents a single attendance entry for a worker.
- `attendanceID`: Unique identifier.
- `workerID`: Reference to the employee.
- `businessLineID`: Context (Mill, Harvester, Ploughing).
- `date`: Date of the entry.
- `status`: `AttendanceStatus` (Enum).
- `entryBy`: User ID of the admin who recorded the entry.
- `timestamp`: Precise time of recording.

## Attendance Summary (View Model)
Consolidated view for "Today's Attendance" dashboard.
- `workerID`: Unique ID.
- `fullName`: Display name.
- `userName`: Login name.
- `entries`: List of `AttendanceRecord` (since a worker can have multiple entries for different business lines in a day).
- `overallStatus`: Summarized status for the day.

## Business Line (Reference)
- `lineID`: Unique ID (MILL, HARVESTER, PLOUGHING).
- `displayName`: Human-readable name.
