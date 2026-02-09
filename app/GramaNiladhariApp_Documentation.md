# GramaNiladhariApp Documentation

## Overview
GramaNiladhariApp is an offline Android application designed for Grama Niladhari administrative work.
The app is built using **Kotlin** and **Jetpack Compose** and does **not use any database**.
All data is stored locally in files, making it fully offline and suitable for low-connectivity environments.

## Key Goals
- Fully offline operation
- No Room / SQLite / external databases
- Simple file-based data persistence
- Clean MVVM architecture
- Easy migration to database in the future if needed

## Architecture
The application follows the **MVVM (Model–View–ViewModel)** pattern.

### Layers
- **UI (Compose)**: Screens and navigation
- **ViewModel**: Holds UI state and business logic
- **Repository**: Reads and writes data to files
- **Model**: Plain Kotlin data classes
use topbar,bottombar,drawer

## Package Structure
```
com.emarketing_paradice.gramaniladhariapp
├── data
│   ├── model
│   │   ├── GN.kt
│   │   ├── Household.kt
│   │   ├── Citizen.kt
│   │   └── Request.kt
│   └── repository
│       └── FileRepository.kt
├── viewmodel
│   ├── GNViewModel.kt
│   └── CitizenViewModel.kt
├── ui
│   ├── screens
│   │   ├── HomeScreen.kt
│   │   └── CitizenListScreen.kt
│   └── navigation
│       └── AppNavHost.kt
└── theme
    └── Theme.kt
```

## Data Storage Strategy
- Data is saved as **JSON files** in the app's internal storage.
- No internet or external storage permission required.
- Example files:
  - `gn_data.json`
  - `households.json`
  - `citizens.json`

## Repository Responsibilities
- Create files if they do not exist
- Read JSON from file and convert to Kotlin objects
- Write updated data back to files
- Provide data to ViewModels

## Example Data Flow
1. User adds a citizen from the UI
2. ViewModel validates input
3. Repository writes updated list to JSON file
4. UI updates automatically using state

## Advantages of File-Based Storage
- Zero dependency issues
- No version conflicts
- Simple debugging
- Works on very low-end devices
- Ideal for government field devices

## Improvements
- Add data encryption
- Add export/import (backup) functionality
- Add role-based access (GN / Admin)

## Target Users
- Grama Niladhari officers
- Government administrative staff
- Rural offices with limited connectivity
