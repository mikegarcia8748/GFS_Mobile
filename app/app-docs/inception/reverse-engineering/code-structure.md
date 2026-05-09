# Code Structure Documentation

## Package Conventions

### Feature Modules (`:feature:auth`, etc.)
- `com.gfs.mobile.feature.[feature_name].ui.screen.[sub_feature]`
    - `...ViewModel.kt`
    - `...Screen.kt`
    - `...UiState.kt`

### Domain Layer (`:core:domain`)
- `com.gfs.mobile.core.domain.model`: Domain entities.
- `com.gfs.mobile.core.domain.repository`: Interfaces.
- `com.gfs.mobile.core.domain.usecase`: Specific business actions.

### Data Layer (`:core:data`)
- `com.gfs.mobile.core.data.data.local`: Room and DataStore implementations.
- `com.gfs.mobile.core.data.data.remote`: Retrofit services and DTOs.
- `com.gfs.mobile.core.data.data.repository`: Repository implementations.
- `com.gfs.mobile.core.data.di`: Hilt modules.

## Resource Management
- Shared strings and dimensions are being centralized in `:core:ui` when possible, though feature-specific strings reside in the feature modules.
- Lottie animations and icons are stored in feature-specific or core resource folders.
