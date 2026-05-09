# Firebase Migration Plan: Authentication

**Purpose**: Transition from on-prem local authentication to Firebase Authentication while maintaining support for Custom MPIN and preparing for Email/Password authentication levels.

## Implementation Steps

### 1. Dependency Management
- [ ] Add Firebase BOM and Authentication to `libs.versions.toml`.
- [ ] Apply `google-services` plugin in `:app`.
- [ ] Add Firebase Auth dependency to `:core:data`.

### 2. Domain Layer Preparation
- [ ] Update `AuthenticationRepository` interface in `:core:domain` to support:
    - `signInWithCustomToken(token: String)` (For MPIN flow)
    - `signInWithEmail(email: String, pass: String)` (For future Admin/Higher-level flow)
    - `signOut()`
    - `getCurrentUser()`

### 3. Data Layer Implementation
- [ ] Implement `FirebaseAuthenticationRepositoryImpl` using `FirebaseAuth` SDK.
- [ ] Configure Hilt to provide `FirebaseAuth` instance.
- [ ] Implement Token mapping logic.

### 4. Feature Integration
- [ ] Update `:feature:auth` ViewModels to use the new Firebase-backed UseCases.
- [ ] Prepare UI placeholders for Email/Password login.
