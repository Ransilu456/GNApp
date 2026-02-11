# GN Application -- Modern UI Redesign Guide

## 1. Design Overview

This design follows a modern soft-card layout with:

-   Gradient header section
-   Rounded containers (30dp radius)
-   Soft elevation shadows
-   2-column dashboard grid
-   Clean typography
-   Clear primary action buttons

------------------------------------------------------------------------

## 2. Dashboard Layout Structure

### Header Section

-   Height: 30--35% of screen
-   Gradient: Deep Blue → Royal Blue
-   Bottom radius: 30dp--40dp
-   White text

**Header Content:** - Left: Hamburger Menu - Center: GN Dashboard
Title - Right: Profile / Notification icon - Subtitle: GN Division Name

------------------------------------------------------------------------

### Main Body Container

-   Background: White or Light Gray
-   Rounded top corners: 30dp
-   Elevation: Soft shadow
-   Padding: 20dp

------------------------------------------------------------------------

### Dashboard Grid (2 Columns)

Replace menu items as:

  Original Concept   GN Application Version
  ------------------ ------------------------
  My Account         My Profile
  Inventory          Residents List
  Search Mechanic    Search Citizen
  Request            Certificate Requests
  Analytics          Reports & Statistics
  Contact Us         Complaints / Contact

Each card: - RoundedCornerShape(20dp) - Soft elevation - Centered icon -
Label below icon

------------------------------------------------------------------------

## 3. Search Citizen Screen

### Top Bar

-   Hamburger menu
-   Center title: Search Citizen
-   Notification icon

------------------------------------------------------------------------

### Citizen List Cards

Each card contains: - Circular avatar (left) - Citizen Name (bold) - NIC
number (secondary text) - Address or Division - Status badge: - Green =
Approved - Yellow = Pending - Red = Rejected

Card specs: - Height: 100dp - Corner radius: 20dp - Padding: 16dp -
Spacing between cards: 12--16dp

------------------------------------------------------------------------

## 4. Primary Action Button

Large rounded button for: - Approve Request - Issue Certificate - Submit
Application

Specifications: - Width: 85% - Height: 55--60dp - Radius: 30dp -
Background: Deep Blue - Text: White - Center aligned

------------------------------------------------------------------------

## 5. Recommended Color Palette

Header Gradient: - #1E2BFF - #3A4BFF

Background: - #F2F4F8

Card Background: - #FFFFFF

Text: - Primary: #1A1A1A - Secondary: #7A7A7A

Status Colors: - Green: #3BB273 - Red: #E63946 - Yellow: #F4A261

------------------------------------------------------------------------

## 6. Jetpack Compose Structure Example

Scaffold ├── TopBar ├── Box │ ├── Gradient Header │ └── Main Content
Card │ ├── LazyVerticalGrid (Dashboard) │ └── LazyColumn (Citizen List)
└── Primary Action Button

Use: - RoundedCornerShape(30.dp) - Card(elevation = 8.dp) -
Brush.linearGradient()

------------------------------------------------------------------------

## 7. UX Improvements

-   Add search bar in citizen screen
-   Add filter chips (Pending, Approved, Rejected)
-   Add statistics section on dashboard:
    -   Total Residents
    -   Pending Requests
    -   Issued Certificates
-   Add subtle animations (fade, slide, scale)

------------------------------------------------------------------------

## Conclusion

This UI redesign will modernize the GN Application by: - Improving
visual hierarchy - Enhancing readability - Increasing usability -
Creating professional government-grade presentation
