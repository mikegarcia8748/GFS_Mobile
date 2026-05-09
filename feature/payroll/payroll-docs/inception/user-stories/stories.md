# User Stories: Payroll Module

These stories follow the **User Journey-Based** approach, covering the lifecycle from onboarding to final payment.

## Phase 1: Onboarding & Configuration

### US-01: Business Line Profiling
**As an** Admin,
**I want to** assign each employee to a specific business line profile (Mill, Harvester, or Ploughing),
**so that** I can apply the correct percentage-based salary rules and payment cycles for their specific group.

**Acceptance Criteria**:
- Admin can create/edit employee profiles with a mandatory "Business Line" field.
- Each business line supports its own unique percentage calculation rules.
- System prevents a worker from being active in multiple business lines simultaneously unless specifically configured.

### US-02: Statutory Benefit Toggle
**As an** Admin,
**I want to** enable or disable SSS, PhilHealth, and Pag-IBIG deductions for each business line or individual worker,
**so that** I can comply with varying employee situations or business preferences.

**Acceptance Criteria**:
- Configurable toggles for SSS, PhilHealth, and Pag-IBIG are available in the settings.
- When enabled, the system automatically applies the 2025 statutory rates.

## Phase 2: Operations & Attendance

### US-03: Daily Attendance Tracking
**As an** Admin,
**I want to** record the daily attendance of my workers, including mill-specific context like shifts,
**so that** I have an accurate record of who worked during the payroll period.

**Acceptance Criteria**:
- Admin can mark attendance for a list of workers.
- Mill attendance supports shift or machine assignment context.
- Attendance history is viewable per worker and per business line.

## Phase 3: Expenses & Loans

### US-04: Admin-Managed Expense Recording
**As an** Admin,
**I want to** record business expenses by capturing a photo of the physical receipt presented by a worker,
**so that** I can maintain a digital record of the expenditure and link it to the payroll.

**Acceptance Criteria**:
- Admin can initiate an expense record with amount, date, and description.
- System allows capturing a photo using the device camera or uploading from the gallery.
- Expenses are tagged to a specific worker or business line.

### US-05: Issuing Loans and Cash Advances
**As an** Admin,
**I want to** issue cash advances or product loans (like rice) to workers, with the current price of products pinned at the time of issuance,
**so that** I can accurately track how much the worker owes the business.

**Acceptance Criteria**:
- Admin can record a new loan entry.
- For product loans, the "Current Price" field is mandatory and recorded.
- System calculates and displays the total outstanding balance for each worker.

## Phase 4: Weekly Payroll Generation

### US-06: Percentage-Based Payroll Calculation
**As an** Admin,
**I want to** generate the weekly payroll by entering the Mill's income and letting the system calculate each worker's share based on their configured percentage,
**so that** I avoid manual calculation errors.

**Acceptance Criteria**:
- Admin selects a business line and date range.
- Admin enters the total income for the period.
- System automatically calculates gross pay based on the percentage assigned to workers.

### US-07: Flexible Loan Deductions
**As an** Admin,
**I want to** see a suggested deduction amount for outstanding loans and choose which ones to apply to this week's payslip,
**so that** I can manage worker debt recovery flexibly.

**Acceptance Criteria**:
- System displays a checkbox list of all workers with outstanding loans.
- A "Suggested Deduction" badge is shown for each loan (e.g., 10% of balance or fixed amount).
- Admin can check/uncheck loans for deduction.
- Admin can manually override the deduction amount if needed.

### US-08: Payroll Audit Logging
**As an** Admin,
**I want to** have a detailed audit log generated for every payroll run,
**so that** I can review who generated the payroll, the total amounts, and the exact breakdown of deductions.

**Acceptance Criteria**:
- System creates a permanent log entry after payroll finalization.
- Log includes: Admin ID, Timestamp, Worker List, Gross Pay, Statutory Deductions, Loan Deductions, and Net Pay.

## Phase 5: Payment & Verification

### US-09: Physical Payslip Generation
**As an** Admin,
**I want to** generate and print a PDF payslip for each worker,
**so that** I can hand it to them along with their cash payment.

**Acceptance Criteria**:
- System generates a PDF following a standardized template (Earnings vs Deductions).
- PDF is optimized for printing directly from the device.
- A digital copy is archived in the system.

### US-10: Payment Receipt Logging
**As an** Admin,
**I want to** mark a payroll entry as "Paid" once the worker receives their money,
**so that** I can track which payments have been settled and which are pending.

**Acceptance Criteria**:
- Admin can toggle a "Paid" status for individual workers.
- The system logs the time and Admin ID of the payment settlement.
