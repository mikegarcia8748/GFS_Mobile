# Architecture Documentation

## Overview
The GFS Mobile application follows **Clean Architecture** principles combined with **MVI (Model-View-Intent)** / **MVVM (Model-View-ViewModel)** for the presentation layer.

## Layered Structure

### 1. Presentation Layer (Feature Modules)
- Uses Jetpack Compose for UI.
- ViewModels manage state using `MutableStateFlow` and handle user intents.
- Communication with the Domain layer via UseCases.

### 2. Domain Layer (:core:domain)
- Contains business models (POJOs/Data Classes).
- Defines repository interfaces.
- Contains UseCases (Interactors) that encapsulate specific business logic.
- Independent of any framework or data source.

### 3. Data Layer (:core:data)
- Implements repository interfaces defined in the domain layer.
- Handles data from multiple sources:
    - **Local**: Room DB, DataStore (Encrypted).
    - **Remote**: Retrofit API services, Firebase Auth.
- Manages caching logic and network-bound resources.

## Dependency Graph
`Feature Modules` → `:core:domain` ← `:core:data`
`Feature Modules` → `:core:ui`
`Feature Modules` → `:core:navigation`
`:app` → `Feature Modules` + `Core Modules`
