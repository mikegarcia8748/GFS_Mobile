# Application Design Questions: Payroll Module

Please answer the following questions to help refine the architectural design for the Payroll module.

## Question 1
How should the core logic for statutory deductions (SSS, PhilHealth, Pag-IBIG) be organized?

A) **Isolated Engine**: A dedicated `StatutoryDeductionEngine` component that only handles Philippines-specific government rules.
B) **Generic Calculator**: A more general `PayrollCalculator` that handles all deductions (loans, benefits, taxes) together.
C) **Domain-Driven**: Separate components for each benefit type (e.g., `SssManager`, `PhilHealthManager`) for easier maintenance of individual rules.
D) Other (please describe after [Answer]: tag below)

[Answer]: A

## Question 2
The SSS and PhilHealth tables change periodically (e.g., the 2025 hike). How should these rates and brackets be managed?

A) **Local Configuration**: Hardcoded constant tables within the module (simplest for 2025-only focus).
B) **Remote Updateable**: Stored in a local database (Room) with the ability to sync new rates from an API without app updates.
C) **Shared Preferences/DataStore**: Configurable values where the user or admin can manually set the current % or ceiling values.
D) Other (please describe after [Answer]: tag below)

[Answer]: B

## Question 3
Where should the "Enabled/Disabled" state for specific deductions (SSS, Pag-IBIG, etc.) be stored?

A) **User Preferences**: Encrypted DataStore in `:feature:payroll` (module-specific).
B) **Centralized Config**: A shared `AppConfig` in `:core:data` that manages all feature flags across the app.
C) **Build Flavors**: Managed at the build level (less flexible for runtime changes).
D) Other (please describe after [Answer]: tag below)

[Answer]: B

## Question 4
How should "General Attendance" (Dashboard) and "Mill Attendance" (Rice Mill) be unified?

A) **Single Entry Point**: Both features use the same `AttendanceRepository` and UI components, passing a `type` parameter (GENERAL vs MILL).
B) **Base + Extension**: A core `AttendanceComponent` in `:feature:payroll` with specific extensions or subclasses for Mill-specific needs.
C) **Decoupled**: Completely separate screens but sharing a unified domain layer for data persistence.
D) Other (please describe after [Answer]: tag below)

[Answer]: B
