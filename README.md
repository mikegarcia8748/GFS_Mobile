# GFS Mobile

GFS Mobile is an application intended for Family Farm Business management, including Rice Mill operations, Land Ploughing, Harvesting, and more.

## Version 1 Features
- **Sales Recording:** Specifically for Rice Mill operations.
- **Payroll:** Management for 2-5 employees.
- **Accounting:** Basic accounting features for the business.
- **Rice Deposit:** Tracking and management of rice deposits.
- **Truck Servicing:** Logistics management, primarily for picking up customer's rice and deliveries.

## Architecture & Best Practices
- **Multi-Module Architecture:** The project follows a modularized structure to improve build times, maintainability, and scalability.
- **Clean Architecture:** Each module is structured into layers (Data, Domain, and UI) to ensure separation of concerns.
    - **Domain Layer:** Contains business logic via **UseCases**.
    - **Data Layer:** Handles data sourcing from Room, DataStore, and Firebase.
    - **UI Layer:** Implements **MVVM** using Jetpack Compose.
- **Testing Strategy:**
    - **Unit Testing:** Comprehensive testing for UseCases, ViewModels, and Repositories.
    - **UI Testing:** Automated testing for Compose components and navigation flows.

## Tech Stack & Libraries
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose with Material Design 3
- **Dependency Injection:** Hilt (Dagger)
- **Local Database:** Room Persistence Library
- **Local Storage:** Encrypted Jetpack DataStore (for secure preferences)
- **Backend & Database:** Firebase
    - Authentication
    - Cloud Firestore / SQLConnect
    - Remote Config
- **Navigation:** Jetpack Compose Navigation
- **Asynchronous Programming:** Kotlin Coroutines & Flow
- **Animations & UI Utilities:** 
    - Lottie (Vector animations)
    - Shimmer (Loading effects)
    - Timber (Logging)
- **Serialization:** Kotlinx Serialization
- **Build System:** Gradle Kotlin DSL with Version Catalog
