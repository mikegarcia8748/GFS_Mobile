# Requirements: Payroll Module

## Overview
Consolidate all attendance and payroll business logic into a dedicated feature module to improve maintainability and decouple business domains.

## Functional Requirements

### 1. General Attendance
- Record daily attendance for employees.
- View today's attendance summary.
- Retrieve attendance history for specific employees.

### 2. Business-Specific Payroll (Rice Mill)
- **Percentage-Based Salary**: Calculate worker pay based on a percentage of the Mill's income (not fixed daily/monthly).
- **Logistics Integration**: Handle logistics-related tasks as a side feature of the Mill operations, using the same pool of workers.
- **Harvester & Land Ploughing**: Support distinct payroll configurations and calculation rules for different business lines (e.g., Harvester vs. Land Ploughing).

### 3. Statutory Benefits & Deductions (2025 Standards)
- **Configurability**: Allow enabling/disabling of specific deductions (SSS, Pag-IBIG, PhilHealth) per business line.
- **Variable Contribution**: Support calculations based on variable income (percentage-based) rather than fixed MSC when applicable.
- **SSS (Social Security System)**: 
    - Implement 2025 rate: 15% total (EE: 5%, ER: 10%).
    - Support Monthly Salary Credit (MSC) range: ₱5,000 to ₱35,000.
    - Include Mandatory Provident Fund (WISP) for compensation above ₱20,000.
- **Pag-IBIG (HDMF)**:
    - Implement 2025 rate: 2% EE, 2% ER (for monthly compensation > ₱1,500).
    - Max Monthly Fund Salary (MFS) cap: ₱10,000 (Fixed max ₱200 EE / ₱200 ER).
- **PhilHealth**:
    - Implement 2025 rate: 5% total premium (shared 50-50: 2.5% EE / 2.5% ER).
    - Min Monthly Salary: ₱10,000 (Min ₱250 EE / ₱250 ER).
    - Max Monthly Salary: ₱100,000 (Max ₱2,500 EE / ₱2,500 ER).

### 4. Expense & Loan Management
- **Expense Declaration**: Allow users to declare expenses with date and receipt photo (capture/upload).
- **Loans & Cash Advances**:
    - Support monetary cash advances.
    - Support product-based loans (e.g., Rice).
    - **Price Pinning**: Product loans must record the price at the time of issuance.
    - **Flexible Deductions**: Deductions for loans are optional per payroll run, based on the administrator's decision.

### 5. Payroll Generation & Audit
- **Weekly Cycle**: Payroll is typically generated on a weekly basis.
- **Audit Logging**: Mandatory logging for every payroll and attendance generation event.
    - Include: Admin User ID, Employee ID, Total Amount, Detailed Deductions, Timestamp.
- **Payment Verification**: Log when an employee officially receives their pay.
- **Payslips**: Generate downloadable/shareable PDF payslips for employees.

### 6. Data Integrity
- Ensure that payroll calculations correctly account for all deductions (statutory benefits, loans, expenses).
- Maintain consistency between general attendance and mill-specific attendance records.

## Non-Functional Requirements

### 1. Security (Baseline)
- Apply all **Security Baseline** rules.
- Generic error handling for payroll data access (SECURITY-15).
- Structured logging for salary-related operations, ensuring PII is protected (SECURITY-03).

### 2. Testing (PBT)
- **Property-Based Testing** should be used for salary calculation logic (rounding, deductions vs total).

### 3. Architecture
- Follow Clean Architecture: ViewModels should interact with UseCases, not repositories directly.
- Ensure strict Hilt dependency injection.
