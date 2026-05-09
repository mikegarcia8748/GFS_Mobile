# Code Generation Plan: Firebase Auth Migration

**Unit**: Firebase Auth
**Purpose**: Transition from local authentication to Firebase Authentication while maintaining Custom MPIN support.

## Context & Traceability
- **Stories**: [N/A - Unit derived from legacy migration]
- **Dependencies**: `:core:domain`, `:core:data`, `:feature:auth`
- **Interfaces**: `AuthenticationRepository`

## Execution Steps

### Phase 1: Dependency Management
- [x] Step 1: Add Firebase BOM and Authentication to `libs.versions.toml`.
- [x] Step 2: Apply `google-services` plugin in `:app`.
- [x] Step 3: Add Firebase Auth dependency to `:core:data`.

### Phase 2: Domain Layer Alignment
- [x] Step 4: Update `AuthenticationRepository` interface in `:core:domain`.
- [x] Step 5: Implement `GetAuthenticationTokenUseCase` (existing).
- [x] Step 6: Implement `SaveAuthenticationTokenUseCase` (existing).

### Phase 3: Data Layer Implementation
- [x] Step 7: Create `FirebaseModule` in `:core:data` for Hilt provisioning.
- [x] Step 8: Implement `FirebaseAuthenticationRepositoryImpl` using `FirebaseAuth`.
- [x] Step 9: Implement Token mapping logic in `AuthenticationRepositoryImpl`.

### Phase 4: Feature Integration
- [x] Step 10: Select and add a PBT framework to `libs.versions.toml` (e.g., Kotest Property Testing) (PBT-09).
- [x] Step 11: Update `:feature:auth` `AuthenticationViewModel` to use Firebase-backed UseCases.
    - [x] Ensure structured logging (Timber) is used for all auth events (SECURITY-03).
    - [x] Implement generic error messages for production (SECURITY-15).
    - [x] Validate MPIN and Email inputs (SECURITY-05).
- [x] Step 12: Update `AuthenticationScreen` UI to support Email/Password entry placeholders.
- [x] Step 13: Identify and implement PBT tests for Authentication logic (e.g., Token parsing/mapping) (PBT-01, PBT-02).
- [x] Step 14: Verify Hilt dependency graph for `AuthenticationRepository`.

### Phase 5: Build & Verification
- [x] Step 15: Execute project build.
- [x] Step 16: Verify login flow in debug environment.
- [x] Step 17: Perform Security Compliance Review (Baseline).
- [x] Step 18: Perform PBT Compliance Review.
