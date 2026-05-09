# Frontend Components: Attendance Migration

## Consolidated Attendance Screen
A unified screen that replaces the fragmented attendance views in Dashboard and Ricemill.

### Component Hierarchy
- `AttendanceScreen` (Container)
    - `BusinessLineSelector` (Tab or Dropdown)
    - `WorkerList` (Scrollable list)
        - `WorkerAttendanceItem` (Row)
            - `WorkerInfo` (Avatar + Name)
            - `StatusControls` (Segmented Button or Toggle for PRESENT/ABSENT/LATE/HALFDAY)
    - `DailySummarySection` (Collapsible view of all marked workers across lines)

### State Definition
- `currentBusinessLine`: Selected business line context.
- `workers`: List of workers available for the selected line.
- `todaySummary`: List of consolidated attendance entries already recorded.
- `isLoading`: Processing state.

### User Interaction Flows
1. **Switch Business Line**: Changes the context for the entire list.
2. **Mark Status**: Tapping a status button immediately invokes the `recordAttendance` use case.
3. **View Summary**: Clicking a "Summary" badge shows what the worker has done across all lines for the day.

### API Integration
- `GET /workers?businessLine={id}`
- `GET /attendance/today`
- `POST /attendance/record` (JSON with workerID, businessLineID, status)
