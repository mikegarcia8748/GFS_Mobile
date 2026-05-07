# GFS Mobile

GFS Mobile is an application intended for Family Farm Business management, including Rice Mill operations, Land Ploughing, Harvesting, and more.

## Version 1 Features
- **Sales Recording:** Specifically for Rice Mill operations.
- **Payroll:** Management for 2-5 employees.
- **Accounting:** Basic accounting features for the business.
- **Rice Deposit:** Tracking and management of rice deposits.
- **Truck Servicing:** Logistics management, primarily for picking up customer's rice and deliveries.

## Tech Stack & Libraries
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose with Material Design 3
- **Architecture:** MVVM (Model-View-ViewModel)
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
