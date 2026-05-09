# Requirements Document

## Functional Requirements

### 1. Authentication
- **MPIN Login**: Users must be able to log in using a 6-digit MPIN.
- **Account Selection**: Users can switch between authorized accounts.
- **Admin Login**: Support for Email/Password login for administrative functions.
- **Session Persistence**: Authentication tokens must be securely cached.
- **Firebase Integration**: Use Firebase Auth for user session management.

### 2. Rice Mill Management
- **Billing**: Manage billing for rice milling operations.
- **Inventory/Cache**: Local caching of billing data for offline/performance optimization.

### 3. Dashboard
- View key metrics and navigate to functional features (Payroll, Logistics, etc.).

## Non-Functional Requirements (NFR)

### 1. Security (Baseline)
- **Encryption at Rest**: Local data (tokens, user preferences) must be encrypted (DataStore Crypto).
- **Secure Communication**: All API calls must use TLS.
- **Structured Logging**: No sensitive data (PII, tokens) in logs.
- **Generic Error Handling**: Do not expose system internals to users on failure.

### 2. Performance
- UI must remain responsive during network operations (asynchronous UseCases).
- Use local caching to reduce latency for frequent data access.

### 3. Maintainability
- Modular structure to allow independent development of features.
- Strict dependency injection using Hilt.
- Property-based testing for critical data transformations.
