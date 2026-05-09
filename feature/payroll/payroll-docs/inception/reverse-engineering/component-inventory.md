# Component Inventory: Payroll & Attendance

This document lists existing components that belong to the Payroll/Attendance domain and are currently located in other modules.

## UI Components (Screens & ViewModels)

### In `:feature:dashboard`
- `com.gfs.mobile.feature.dashboard.ui.screen.attendance.AttendanceScreen.kt`
- `com.gfs.mobile.feature.dashboard.ui.screen.attendance.AttendanceViewModel.kt`
- `com.gfs.mobile.feature.dashboard.ui.screen.attendance.AttendanceUIState.kt`
- `com.gfs.mobile.feature.dashboard.ui.screen.attendance.AttendanceCallback.kt`

### In `:feature:ricemill`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll.MillWorkerPayrollScreen.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll.MillWorkerPayrollViewModel.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll.MillWorkerPayrollUiState.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.payroll.MillWorkerPayrollCallback.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance.MillAttendanceScreen.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance.MillAttendanceViewModel.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance.MillAttendanceUiState.kt`
- `com.gfs.mobile.feature.ricemill.ui.screen.milling.attendance.MillAttendanceCallback.kt`

## Domain Components (Interfaces & UseCases)

### In `:core:domain`
- `com.gfs.mobile.core.domain.repository.AttendanceRepository.kt`
- `com.gfs.mobile.core.domain.usecase.worker.CreateAttendanceUseCase.kt`
- `com.gfs.mobile.core.domain.usecase.worker.GetAttendanceTodayUseCase.kt`
- `com.gfs.mobile.core.domain.usecase.worker.GetEmployeeAttendanceUseCase.kt`
- Models: `AttendanceModel`, `AttendanceTodayModel`, `DailySalaryDetail`, `DeductedLoan`, `Expense`, `WorkerPayrollDetail`.

## Data Components (Implementations)

### In `:core:data`
- `com.gfs.mobile.core.data.data.repository.AttendanceRepositoryImpl.kt`
