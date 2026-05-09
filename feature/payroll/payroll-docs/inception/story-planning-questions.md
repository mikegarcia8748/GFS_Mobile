# User Story Planning Questions: Payroll Module

Please answer the following questions to help clarify the user personas and workflows for the Payroll module. You can also add notes for each question.

## Question 1
Since Mill and Logistics share the same workforce and salary is percentage-based, how should the system handle payroll generation for different business lines (Mill, Harvester, Ploughing)?

A) **Unified List**: One master list of employees; Admin selects the business line (Mill/Harvester/etc.) and the percentage/income to calculate this week's pay.
B) **Separate Profiles**: Employees are grouped by business line; each group has its own unique percentage rules and payment cycles.
C) **Multi-Mode**: A single worker can have earnings from multiple lines in one payslip (e.g., Mill percentage + Harvester fixed task).
D) Other (please describe after [Answer]: tag below)

[Answer]: B

## Question 2
How should the receipt capture/upload process for expenses be initiated and approved?

A) **Self-Service**: Employee uploads the receipt via the app -> Admin approves -> Added to next payroll.
B) **Admin-Only**: Employee presents a physical receipt -> Admin captures the photo and records the expense directly.
C) **Notification-Based**: System notifies Admin whenever an expense is uploaded for immediate approval.
D) Other (please describe after [Answer]: tag below)

[Answer]: B

## Question 3
For the "optional deduction per payroll" rule for loans, how should the Admin interact with the system during payroll generation?

A) **Checkbox List**: System shows all outstanding loans; Admin checks which ones to deduct this week.
B) **Manual Entry**: Admin types in the specific amount to deduct from the outstanding loan balance for that week.
C) **Auto-Suggest**: System suggests a standard deduction (e.g., 10%), but Admin can override or skip it.
D) Other (please describe after [Answer]: tag below)

[Answer]: A, but also implement auto suggest like badge for the option.

## Question 4
How should the employee receive the generated PDF payslip?

A) **In-App Viewer**: Employee logs in and views/downloads it from their own dashboard.
B) **External Sharing**: Admin shares the PDF via 3rd party apps (WhatsApp, Messenger, Email) after generation.
C) **Physical**: Admin prints the PDF for the employee (a digital log of the generation is still maintained).
D) Other (please describe after [Answer]: tag below)

[Answer]: C

## Question 5
Which organizational approach should we use for the user stories?

A) **User Journey-Based**: Stories follow the lifecycle of a worker (Hiring -> Attendance -> Expense -> Payroll -> Payment).
B) **Persona-Based**: Separate stories for what an Admin does vs. what an Employee does.
C) **Feature-Based**: Groups stories by technical areas like Deduction Engine, Audit Logs, Loan Management, etc.
D) Other (please describe after [Answer]: tag below)

[Answer]: A
