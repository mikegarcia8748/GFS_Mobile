# Units Generation Questions: Payroll Module

Please answer the following questions to help determine the best way to decompose the Payroll module into development units.

## Question 1
How should we group the core functional areas for development?

A) **Domain-Based**: Group by business entities (e.g., Unit 1: Attendance, Unit 2: Loans & Expenses, Unit 3: Statutory Rules, Unit 4: Payroll Orchestration).
B) **Layer-Based**: Group by architectural layers (e.g., Unit 1: Data Infrastructure/DB, Unit 2: Domain Logic/Engines, Unit 3: UI/Presentation).
C) **Feature-Priority**: Group by critical path (e.g., Unit 1: Basic Attendance & Payroll, Unit 2: Statutory Deductions, Unit 3: Advanced Loans/Expenses/PDF).
D) Other (please describe after [Answer]: tag below)

[Answer]: 

## Question 2
The "Statutory Deduction Engine" is technically complex but logically isolated. Should it be its own independent unit?

A) **Yes**: Keep it isolated to ensure deep focus on the 2025 calculation rules and testing.
B) **No**: Integrate it into a larger "Deductions & Benefits" unit that also includes Loan deductions.
C) Other (please describe after [Answer]: tag below)

[Answer]: 

## Question 3
How should we handle the "Audit Logger" and "Payslip Generator"?

A) **Operational Unit**: Group them together as they both deal with the post-calculation "output" and record-keeping phase.
B) **Integrate with Payroll**: Build them directly as part of the main Payroll Execution unit.
C) Other (please describe after [Answer]: tag below)

[Answer]: 

## Question 4
"Attendance" is currently leaked in `:feature:dashboard` and `:feature:ricemill`. Should the migration/refactoring of these existing screens be the very first unit?

A) **Yes**: Migrate existing functionality first to establish the new module's presence before adding new logic.
B) **No**: Build the new logic (Statutory/Loans) first and migrate the UI once the backend is solid.
C) Other (please describe after [Answer]: tag below)

[Answer]:
