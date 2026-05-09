# Unit of Work Definitions: Payroll Module

This document decomposes the Payroll module into distinct development units based on a domain-based grouping strategy.

## Unit 1: Attendance Migration
- **Purpose**: Consolidate existing attendance functionality.
- **Responsibilities**:
    - Migrate generic attendance UI/logic from `:feature:dashboard`.
    - Migrate mill-specific attendance UI/logic from `:feature:ricemill`.
    - Establish the base `AttendanceComponent` and `MillAttendanceExtension`.
    - Ensure historical data remains accessible.

## Unit 2: Statutory Deduction Engine
- **Purpose**: Implement 2025 Philippines statutory calculation logic.
- **Responsibilities**:
    - Implement `StatutoryDeductionEngine`.
    - Implement `PayrollRuleManager` with Room persistence for rate tables.
    - Implement SSS (including WISP), PhilHealth, and Pag-IBIG 2025 calculation rules.
    - Support remote updates for government rate changes.

## Unit 3: Loans & Expenses
- **Purpose**: Manage employee financial transactions outside of base pay.
- **Responsibilities**:
    - Implement `LoanManager` for cash advances and product loans (with price pinning).
    - Implement `ExpenseManager` for business-related expense recording and receipt capture.
    - Support administrative discretion for loan deductions per payroll cycle.

## Unit 4: Payroll Orchestration
- **Purpose**: The core system for weekly payroll calculation.
- **Responsibilities**:
    - Implement `PayrollOrchestrator` service.
    - Implement `DeductionConfigurator` for enabling/disabling benefits.
    - Implement percentage-based salary calculation based on business income.
    - Consolidate attendance, loans, and statutory deductions into final payslips.

## Unit 5: Audit & Outputs
- **Purpose**: Handle post-calculation record-keeping and worker documentation.
- **Responsibilities**:
    - Implement `AuditLogger` to track payroll/attendance generation and payment receipt.
    - Implement `PayslipGenerator` to create and print PDF payslips.
    - Ensure all financial events are recorded for transparency.
