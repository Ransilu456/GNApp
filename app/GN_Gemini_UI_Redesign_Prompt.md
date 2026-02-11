# GN Application -- Full UI Redesign Prompt for Gemini (Android Studio)

You are an expert Android UI/UX designer and Jetpack Compose developer.

## Objective

Redesign the entire GN (Grama Niladhari) Android application using
Jetpack Compose with a modern, clean, professional government-style UI
based on a soft-card layout with gradient header design.

The redesign must improve:

-   Visual hierarchy
-   Navigation structure
-   UX consistency
-   Form validation
-   Error handling
-   Layout responsiveness
-   Code organization

------------------------------------------------------------------------

# Design System Requirements

## 1. Overall Style

-   Modern soft-card UI
-   Large rounded corners (20dp--30dp)
-   Soft elevation shadows
-   Light gray background (#F2F4F8)
-   White cards
-   Blue gradient header (#1E2BFF → #3A4BFF)
-   Minimal but structured layout

------------------------------------------------------------------------

# Screens to Redesign

## 1. Dashboard Screen

### Header Section

-   Gradient background
-   Large curved bottom corners (30dp--40dp)
-   Title: "GN Dashboard"
-   Subtitle: GN Division Name
-   Left: Drawer icon
-   Right: Profile / Notification icon

### Stats Section (New)

Add statistic cards: - Total Residents - Pending Requests - Issued
Certificates

### Feature Grid (2 Columns)

Create modern icon cards for:

-   My Profile
-   Residents List
-   Search Citizen
-   Certificate Requests
-   Reports & Statistics
-   Complaints

Use LazyVerticalGrid with equal spacing and animation on click.

------------------------------------------------------------------------

## 2. Search Citizen Screen

Add:

-   Fixed TopAppBar (not scrollable)
-   Search bar with real-time filtering
-   Filter chips:
    -   All
    -   Approved
    -   Pending
    -   Rejected

Citizen Card Layout: - Circular image/avatar - Name (bold) - NIC -
Address - Status badge (color-coded) - Soft shadow - Rounded 20dp

Use LazyColumn with proper spacing.

------------------------------------------------------------------------

## 3. Certificate Request Screen

Each request card must show:

-   Citizen Name
-   Certificate Type
-   Submission Date
-   Status
-   View Details button

Detail screen must include:

-   Full citizen data
-   Approve / Reject buttons
-   Confirmation dialog
-   Proper validation & state handling

------------------------------------------------------------------------

## 4. Forms (All Forms in App)

Apply:

-   Real-time validation
-   Required field indicators
-   Error text under field
-   Clean spacing (16dp standard padding)
-   Scrollable layout
-   Keyboard safe area handling

No crashes allowed. Handle all null states safely.

------------------------------------------------------------------------

# Navigation Architecture

-   Use single NavHost
-   Proper navigation routes
-   No navigation stack duplication
-   Drawer + BottomBar must not conflict
-   Each screen must preserve state properly

Fix existing navigation bugs completely.

------------------------------------------------------------------------

# Technical Requirements

-   Use Jetpack Compose only
-   Proper state management (ViewModel + StateFlow)
-   Clean architecture separation
-   Reusable composables
-   No deprecated APIs
-   No layout overlap issues
-   No unresolved references
-   No unnecessary recompositions

------------------------------------------------------------------------

# UX Improvements

-   Add subtle animations (fade/scale)
-   Add loading indicators
-   Add empty state screens
-   Add proper error messages
-   Improve spacing consistency
-   Improve typography hierarchy

------------------------------------------------------------------------

# Deliverables Required

1.  Fully redesigned composable files
2.  Updated Navigation setup
3.  Reusable UI components
4.  Fixed layout issues
5.  Proper form validation implementation
6.  Complete working code ready to build

The result must be: - Clean - Professional - Government-grade - Modern -
Fully functional without crashes
