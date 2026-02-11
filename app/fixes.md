# 🚀 Android Application Full UI/UX & Navigation Refactor Prompt (Gemini)

## 🎯 Objective
Refactor and redesign the **entire Android application** to achieve a **modern, stable, production-ready** experience.
The focus is on **UI/UX quality, navigation stability, validation, and correct data flow** across all screens.

---

## 🧭 Navigation & Architecture (CRITICAL)

### 1️⃣ App Drawer – `AppDrawer.kt`
- Completely **redesign** the drawer UI
- ❌ Remove any split-screen or complex layouts
- ✅ Use a **single clean Material 3 navigation drawer**
- Drawer must include:
    - App header (icon + app name)
    - Clear navigation items
    - Selected/active state indicator
    - Smooth open/close animations
- Drawer must look **professional, minimal, and modern**
- Ensure drawer navigation does **NOT conflict with bottom navigation**

---

### 2️⃣ Bottom Navigation – `AppBottomBar.kt`
- Fully redesign using **Material 3 Bottom Navigation**
- Icons + labels must be aligned correctly
- Selected item must be clearly visible
- Bottom bar must:
    - Work correctly after navigating from the drawer
    - Preserve navigation state
    - Never block or overlap screen content
- Eliminate bad UX, clutter, and visual imbalance

---

### 3️⃣ Navigation Stability
- Use **ONE shared NavController** for Drawer + BottomBar
- Fix all navigation bugs:
    - Screens becoming unreachable
    - Broken back stack
    - Incorrect back button behavior
- Navigation must be predictable and consistent

---

## 🖥️ Screen-Level Improvements (ALL SCREENS)

For **every screen in the app**:
- Fix layout issues, spacing, alignment, and padding
- Add proper scrolling:
    - `LazyColumn` or `Column + verticalScroll`
- Ensure content does NOT hide behind:
    - Top bars
    - Bottom bars
- Improve typography and visual hierarchy
- Bind **correct and real data** (no mismatches or placeholders)

---

## ✅ Validation & Error Handling (MANDATORY)

### 🔹 Form Validation
- Validate all user inputs:
    - Empty fields
    - Invalid formats
    - Range limits
- Show **inline error messages**
- Disable submit buttons until inputs are valid

---

### 🔹 Error & State Handling
- Handle:
    - Network errors
    - Empty states
    - Loading states
- Use proper UI for:
    - Loading
    - Success
    - Error
- ❌ No crashes or blank screens

---

### 🔹 State Management
- Use `UiState` (Loading / Success / Error)
- Avoid recomposition bugs
- Preserve state across navigation where required

---

## 🎨 UI / UX Design Rules
- Follow **Material 3 (Material You)** principles
- Consistent color scheme
- Proper dark & light mode support
- Avoid hardcoded sizes where adaptive layouts are needed
- Smooth animations and transitions
- Professional, government-app-appropriate design

---

## 🧪 Code Quality & Stability
- Fix all crashes, warnings, and layout exceptions
- Remove unused code and redundant composables
- Improve composable reusability
- Add comments where logic is complex
- Ensure:
    - No ANRs
    - No UI freezes
    - Stable performance

---

## 📦 Final Deliverables
- Fully redesigned `AppDrawer.kt`
- Fully redesigned `AppBottomBar.kt`
- All screens:
    - Functional
    - Visually improved
    - Correct data
    - Smooth UX
- No broken navigation
- No placeholder UI
- Production-ready quality

---

## ⚠️ Important Instruction
> Do NOT apply partial fixes.  
> Ensure **all changes are consistent across the entire application** and work together as a single system.
