# Component Definitions: Payroll Module

## 1. StatutoryDeductionEngine
- **Purpose**: Encapsulates the logic for Philippines-specific government deductions (SSS, PhilHealth, Pag-IBIG).
- **Responsibilities**:
    - Calculate deductions based on variable income (percentage-based) or Monthly Salary Credit (MSC).
    - Apply WISP logic for high-income SSS contributions.
    - Handle bracket-based lookups.

## 2. PayrollRuleManager
- **Purpose**: Manages the retrieval and update of statutory deduction tables and parameters.
- **Responsibilities**:
    - Provide access to SSS/PhilHealth/Pag-IBIG rate tables stored in the local database.
    - Orchestrate synchronization of rules from a remote API.

## 3. DeductionConfigurator
- **Purpose**: Manages the enabling/disabling of optional deductions.
- **Responsibilities**:
    - Interface with centralized `AppConfig` to check feature flags for specific benefits.
    - Filter which deductions should be included in a payroll run.

## 4. AttendanceComponent (Base)
- **Purpose**: Core logic for recording and retrieving employee attendance.
- **Responsibilities**:
    - Standard check-in/check-out recording.
    - Basic attendance history retrieval.

## 5. MillAttendanceExtension
- **Purpose**: Specialized attendance for rice mill operations.
- **Responsibilities**:
    - Handle mill-specific worker roles or assignment locations.
    - Link attendance to specific milling shifts.

## 6. PayrollOrchestrator (Service)
- **Purpose**: Orchestrates the end-to-end payroll calculation process.
- **Responsibilities**:
    - Fetch percentage income data from Mill operations.
    - Calculate worker share based on the configured percentage.
    - Gather deductions from the Engine.
    - Consolidate loans and expenses.
    - Generate the final payroll detail for a worker.

## 7. LoanManager
- **Purpose**: Manages employee loans and cash advances.
- **Responsibilities**:
    - Record cash advances.
    - Record product-based loans (e.g., Rice) with price pinning at the time of issuance.
    - Track outstanding balances.
    - Provide optional deduction values for the Orchestrator.

## 8. ExpenseManager
- **Purpose**: Handles employee expense declarations.
- **Responsibilities**:
    - Record business-related expenses.
    - Support image capture/upload for receipts.

## 9. PayslipGenerator
- **Purpose**: Generates official documentation for payroll payments.
- **Responsibilities**:
    - Construct PDF documents containing earnings and deduction breakdowns.
    - Manage digital distribution of payslips.

## 10. AuditLogger
- **Purpose**: Provides a tamper-evident record of payroll and attendance actions.
- **Responsibilities**:
    - Log every generation of payroll/attendance (who, what, when, how much).
    - Log payment receipts by employees.
