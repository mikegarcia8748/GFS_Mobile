# Component Methods and Interfaces: Payroll Module

## StatutoryDeductionEngine
- `calculateSssDeduction(salary: Double): SssBreakdown`
    - Purpose: Computes EE and ER shares including WISP.
- `calculatePhilHealthDeduction(salary: Double): BenefitShare`
    - Purpose: Computes 5% shared premium.
- `calculatePagIbigDeduction(salary: Double): BenefitShare`
    - Purpose: Computes fixed or percentage-based HDMF share.

## PayrollRuleManager
- `getSssTable(): Flow<List<SssBracket>>`
    - Purpose: Reactive access to current SSS rules.
- `updateRules(rulesJson: String): Result<Unit>`
    - Purpose: Persists new rates downloaded from remote.

## DeductionConfigurator
- `isDeductionEnabled(type: DeductionType): Boolean`
    - Purpose: Checks feature flags.
- `getEnabledDeductions(): List<DeductionType>`
    - Purpose: Returns list of active statutory benefits.

## AttendanceComponent (Base)
- `recordAttendance(params: AttendanceParams): Flow<NetworkResource<Unit>>`
- `getAttendanceHistory(workerId: String): Flow<List<Attendance>>`

## PayrollOrchestrator
- `calculateWorkerPayroll(workerId: String, period: DateRange, loanDeduction: Double?): Flow<WorkerPayrollDetail>`
    - Purpose: Master method to aggregate attendance, deductions, loans, and expenses into a final payslip model. `loanDeduction` is optional and provided by the admin.

## LoanManager
- `issueLoan(workerId: String, type: LoanType, amount: Double, productPrice: Double?): Result<Unit>`
- `getOutstandingLoans(workerId: String): Flow<List<Loan>>`

## ExpenseManager
- `recordExpense(workerId: String, amount: Double, date: Long, receiptImageUri: String?): Result<Unit>`

## PayslipGenerator
- `generatePdf(payrollDetail: WorkerPayrollDetail): Flow<File>`

## AuditLogger
- `logPayrollGeneration(adminId: String, payrollData: PayrollAuditData): Result<Unit>`
- `logPaymentReceipt(workerId: String, amount: Double, adminId: String): Result<Unit>`
