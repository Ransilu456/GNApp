# 🚨 Critical Navigation Bug + UX & Validation Improvements (Jetpack Compose)

You are an expert Android engineer specializing in **Jetpack Compose**, **Navigation-Compose**, and **Material 3**.

This project currently has **broken navigation behavior and poor UX**.

---

## 🧨 PROBLEM DESCRIPTION (IMPORTANT)

There is a **navigation bug** with the following behavior:

1. User navigates using the **Navigation Drawer**
2. Then user navigates using the **Bottom Navigation Bar**
3. After that:
    - Screens that were previously opened via the drawer
    - **CANNOT be navigated to again using the Bottom Bar**
4. Back stack becomes inconsistent
5. Navigation feels unstable and unpredictable

👉 This indicates **incorrect NavController usage, duplicated NavHost, or improper back stack handling**.

---

## 🎯 PRIMARY OBJECTIVE

### 🔧 FIX NAVIGATION ARCHITECTURE

You must:

- Use **ONE shared `NavHostController`**
- Ensure:
    - Drawer navigation
    - BottomBar navigation
    - Programmatic navigation  
      all work from the **same NavHost**
- Fix back stack using:
    - `popUpTo`
    - `launchSingleTop`
    - `restoreState`
    - `saveState`
- Ensure **every BottomBar destination is ALWAYS reachable**
- Prevent duplicate destinations in the back stack
- Follow **single-activity Compose navigation best practices**

⚠️ Navigation correctness is the **top priority**

---

## 🧭 NAVIGATION REQUIREMENTS

- Drawer navigation and BottomBar navigation must:
    - Share the same route definitions
    - Share the same NavController
- Bottom bar selection must reflect the **current destination**
- Pressing a bottom tab should always navigate correctly,
  regardless of previous drawer navigation

---

## 🖥️ SCREENS TO UPDATE (MANDATORY)

Apply improvements to **ALL** of the following:

- `HomeScreen.kt`
- `ProfileScreen.kt`
- `CitizenListScreen.kt`
- `RequestListScreen.kt`

---

## 📜 SCROLLING (REQUIRED)

For each screen:

- Add **vertical scrolling**
- Use:
    - `LazyColumn` for lists
    - `Column + verticalScroll()` for static content
- Ensure:
    - Content never overflows
    - UI works on small screens
    - Keyboard does not hide inputs

---

## 🎨 UI / UX IMPROVEMENTS

Improve UI using **Material 3** principles:

- Proper spacing (`PaddingValues`)
- Consistent typography
- Cards for grouped content
- Better empty states
- Loading indicators where needed
- Improve visual hierarchy
- Avoid clutter
- Responsive layouts

⚠️ Keep UI clean, modern, and readable

---

## 🧾 FORM VALIDATION (APP-WIDE)

Add **full form validation** across the entire app:

### Validation rules:
- Required fields must not be empty
- Proper text length validation
- Email / phone validation where applicable
- Show **inline error messages**
- Disable submit buttons until valid
- Use:
    - `TextField` / `OutlinedTextField`
    - `isError`
    - supporting text

### Architecture:
- Validation logic must live in **ViewModel**
- UI must react using **StateFlow / MutableState**
- NO validation logic directly inside composables

---

## ❌ ERROR HANDLING (APP-WIDE)

Implement **robust error handling**:

- Handle:
    - Network failures
    - Empty responses
    - Invalid user input
    - Unexpected crashes
- Use:
    - Sealed UI states (`Loading`, `Success`, `Error`)
- Show:
    - Snackbar messages
    - Dialogs for critical failures
- Never crash the app

---

## 🧠 CODE QUALITY REQUIREMENTS

- Follow **MVVM**
- Use:
    - `UiState` sealed classes
    - `rememberSaveable` where needed
- Avoid:
    - Duplicate NavHosts
    - Nested NavControllers
    - Hardcoded strings
- Keep code:
    - Clean
    - Modular
    - Readable

---

## 📦 FINAL OUTPUT EXPECTED

You must:

1. Fix the navigation bug completely
2. Improve scrolling for all listed screens
3. Improve UI/UX across those screens
4. Add global form validation
5. Add proper error handling
6. Refactor code only where necessary
7. Ensure app behavior is **stable and predictable**

⚠️ Do NOT change app features  
⚠️ Do NOT introduce databases  
⚠️ Focus on correctness, UX, and stability

---

## ✅ SUCCESS CRITERIA

- Bottom navigation ALWAYS works
- Drawer navigation NEVER breaks bottom navigation
- Screens scroll correctly
- Forms validate correctly
- Errors are handled gracefully
- Navigation feels smooth and professional
