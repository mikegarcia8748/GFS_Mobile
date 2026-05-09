# Service Layer Design: Payroll Module

## PayrollOrchestrator Service
The `PayrollOrchestrator` is a domain-level service responsible for coordinating data across several components to produce a final payroll result.

### Orchestration Pattern
1. **Trigger**: User requests payroll calculation for a worker/period.
2. **Data Fetching**:
    - Call `AttendanceComponent` to get total work days/hours.
    - Call `LoanManager` to get outstanding balances and suggested deductions.
    - Call `ExpenseManager` to get business expenses for the period.
3. **Deduction Calculation**:
    - `DeductionConfigurator` determines which statutory benefits are active.
    - For each active benefit, `StatutoryDeductionEngine` computes the specific amount based on basic pay.
4. **Aggregation**:
    - The service sums all earnings and subtracts all deductions (statutory + user-defined loan deduction + expenses).
5. **Finalization & Audit**:
    - `AuditLogger` records the generation event with admin and deduction details.
    - `PayslipGenerator` creates the PDF.
6. **Output**: Returns a `WorkerPayrollDetail` model ready for the UI.

## RuleSyncService
A background service (or WorkManager task) that interfaces with `PayrollRuleManager` to keep statutory tables up to date with the government-mandated schedules.
