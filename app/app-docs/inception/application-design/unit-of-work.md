# Unit of Work Definition

This document defines the high-level units of work for the GFS Mobile modularization project.

## Unit Hierarchy

### 1. Infrastructure Units (Core)
- **Unit: Core Setup**: Migration of themes, network interceptors, and navigation logic.
- **Unit: Firebase Migration**: Integration of Firebase Auth as the primary identity provider.

### 2. Feature Units
- **Unit: Auth Module**: Extraction and enhancement of login flows.
- **Unit: Rice Mill Module**: Extraction of milling logic and billing.
- **Unit: Payroll Module**: (Planned) Extraction of attendance and salary logic.
- **Unit: Logistics Module**: (Planned) Delivery and tracking.

## Unit Assignment Map
| Unit | Module | Status |
|---|---|---|
| Core Setup | `:core:ui`, `:core:nav` | ✅ Complete |
| Firebase Auth | `:feature:auth`, `:core:data` | ✅ Complete |
| Rice Mill | `:feature:ricemill` | ✅ Complete |
| Dashboard | `:feature:dashboard` | ✅ Complete |
| Payroll | `:feature:payroll` | ⏳ Pending |
