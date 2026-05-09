# Tech Stack Decisions: Unit 1 - Attendance Migration

## 1. Data Persistence (Offline)
- **Choice**: **Room Persistence Library**
- **Rationale**: Standard Android library for local-first storage. It supports robust schema migrations and reactive data flows (Flow) needed for the consolidated dashboard.

## 2. Image Handling
- **Choice**: **Simple System Intent**
- **Rationale**: For the current scope, launching the system camera via intent is the simplest and most maintainable approach. It offloads complex camera hardware management to the OS while still providing a URI for receipt storage.

## 3. Testing Framework
- **Choice**: **Kotest + Kotest Property Testing**
- **Rationale**: As per project-wide opt-in (PBT-09). Kotest provides a modern, Kotlin-native DSL for writing both example-based and property-based tests for complex data transformations.

## 4. API & Sync
- **Choice**: **Retrofit + WorkManager**
- **Rationale**: Retrofit for standard API communication. WorkManager is the recommended choice for reliable background synchronization of attendance records when the device transitions from offline to online.
