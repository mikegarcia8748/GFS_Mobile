# Unit of Work Dependency Matrix: Payroll Module

| Unit | Depends On | Rationale |
|---|---|---|
| **Unit 1: Attendance Migration** | None | Foundational unit; establishes the module presence. |
| **Unit 2: Statutory Deduction Engine** | None | Isolated logic; can be developed in parallel with Unit 1. |
| **Unit 3: Loans & Expenses** | None | Can be developed independently; provides input to Unit 4. |
| **Unit 4: Payroll Orchestration** | Unit 1, Unit 2, Unit 3 | Core service that aggregates data from all functional areas. |
| **Unit 5: Audit & Outputs** | Unit 4 | Operates on the final payroll data produced by the Orchestrator. |
