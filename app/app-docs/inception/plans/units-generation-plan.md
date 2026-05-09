# Units Generation Plan (Modularization)

**Purpose**: Decompose the application into independent, parallelizable Units of Work (UOW). In this architecture, each module represents a UOW.

## Unit Checklist

### 1. Core Modules (Infrastructure)
- [ ] **UOW-Core-1**: Setup `:core:ui` (Themes, Compose dependencies)
- [ ] **UOW-Core-2**: Setup `:core:navigation` (Routing logic)
- [ ] **UOW-Core-3**: Setup `:core:domain` (Base UseCases, Models)
- [ ] **UOW-Core-4**: Setup `:core:data` (Room, DataStore, Firebase config)

### 2. Feature Modules (Business Capabilities)
- [ ] **UOW-Feature-1**: Setup `:feature:auth` (Extract `AuthenticationScreen`)
- [ ] **UOW-Feature-2**: Setup `:feature:ricemill` (Extract `MillBillingCache`)
- [ ] **UOW-Feature-3**: Setup `:feature:payroll`
- [ ] **UOW-Feature-4**: Setup `:feature:accounting`
- [ ] **UOW-Feature-5**: Setup `:feature:deposit`
- [ ] **UOW-Feature-6**: Setup `:feature:logistics`

### 3. App Module (Wiring)
- [ ] **UOW-App-1**: Update `:app` to consume Core and Feature modules. Configure Hilt root component.
