# Application Design: Payroll Module

## Executive Summary
The Payroll module is designed as a centralized engine for attendance and salary management, specifically optimized for Philippines statutory compliance (2025 standards). It features a flexible deduction system that can be updated remotely and toggled via feature flags.

## Architecture Highlights

### 1. Isolated Deduction Engine
Statutory logic (SSS, PhilHealth, Pag-IBIG) is isolated from business orchestration. This ensures that when government rates change, only the `StatutoryDeductionEngine` and its underlying rule tables in `PayrollRuleManager` require updates.

### 2. Remote-Updateable Rules
Rule tables are stored in a local Room database, enabling "Over-the-Air" (OTA) updates to SSS brackets or PhilHealth percentages without requiring a full application redeploy on the Play Store.

### 3. Unified Attendance with Extensions
A core `AttendanceComponent` handles general check-ins (e.g., for office or field staff), while `MillAttendanceExtension` adds the specialized context required for rice mill operations (shifts, machine assignments).

### 4. Orchestration-Based Payroll
The `PayrollOrchestrator` service serves as the single source of truth for calculating net pay, consolidating data from attendance, statutory engines, and external loan/expense modules.

## Artifacts Detail
- **Components**: [See components.md](./components.md)
- **Interfaces**: [See component-methods.md](./component-methods.md)
- **Services**: [See services.md](./services.md)
- **Dependencies**: [See component-dependency.md](./component-dependency.md)
