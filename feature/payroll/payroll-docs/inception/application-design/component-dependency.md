# Component Dependency and Communication: Payroll Module

## Dependency Matrix

| Component | Depends On | Purpose |
|---|---|---|
| `StatutoryDeductionEngine` | `PayrollRuleManager` | Needs rate tables to perform calculations. |
| `PayrollOrchestrator` | `AttendanceComponent` | Needs work history. |
| `PayrollOrchestrator` | `StatutoryDeductionEngine` | Needs deduction amounts. |
| `PayrollOrchestrator` | `DeductionConfigurator` | Needs to know which deductions to skip. |
| `PayrollOrchestrator` | `LoanManager` | Needs loan balances. |
| `PayrollOrchestrator` | `AuditLogger` | Needs to log the transaction. |
| `PayrollOrchestrator` | `PayslipGenerator` | Needs to create the PDF. |
| `MillAttendanceExtension` | `AttendanceComponent` (Base) | Extends base check-in logic with mill context. |
| `PayrollRuleManager` | `:core:data` (Room) | Persistence for rule tables. |

## Communication Patterns
- **Reactive Data Flow**: `PayrollRuleManager` exposes rule changes via `Flow`, allowing the `Engine` to stay updated in real-time if a sync occurs during app use.
- **Synchronous Logic**: The `Engine` and `Configurator` provide immediate calculations to the `Orchestrator`.
- **Cross-Module Communication**: The `Orchestrator` uses standard Hilt-injected interfaces to interact with repositories in `:core:data`.
