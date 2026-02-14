
# GN Mobile Application — Architecture Diagram (Local File Storage)

## High-Level Application Architecture

```
+--------------------------------------------------+
|                GN Mobile Application            |
+--------------------------------------------------+
|                                                  |
|  UI Layer                                        |
|  ├── Login Screen                                |
|  ├── Dashboard                                   |
|  ├── Citizen Management                          |
|  ├── Request / Certificate Module                |
|  ├── Area & Household Records                    |
|  ├── Profile & Settings                          |
|                                                  |
|  Application Logic Layer                         |
|  ├── Citizen Controller                          |
|  ├── Request Controller                          |
|  ├── File Manager                                |
|  ├── Validation Engine                           |
|                                                  |
|  Local File Storage Layer                        |
|  ├── citizens.json                               |
|  ├── requests.json                               |
|  ├── households.json                             |
|  ├── profile.json                                |
|  └── documents/                                  |
+--------------------------------------------------+
```

---

## Logical Table Structure → File Schema Mapping

### 1. Citizen Table → `citizens.json`

```
Citizen
├── citizen_id
├── full_name
├── NIC
├── address
├── date_of_birth
├── gender
├── household_id
├── contact_number
└── notes
```

### 2. Household / Area Table → `households.json`

```
Household
├── household_id
├── head_of_family
├── address
├── members_count
├── GN_division
└── remarks
```

### 3. Request / Certificate Table → `requests.json`

```
Request
├── request_id
├── citizen_id
├── request_type
├── description
├── submission_date
├── status
├── approval_notes
└── document_path
```

### 4. Birth Registration Table → `birth_records.json`

```
BirthRecord
├── record_id
├── child_name
├── date_of_birth
├── place_of_birth
├── father_name
├── mother_name
├── guardian_address
└── remarks

```

Used in: Birth Certificate / Citizen Creation Module

### 5. Death Registration Table → `death_records.json`

```
DeathRecord
├── record_id
├── deceased_name
├── age
├── date_of_death
├── cause_of_death
└── remarks

```

### 6. Migration / Movement Notes → `movement_logs.json`

This is a movement or status tracking log.
It appears to record:
* Person movement
* Administrative notes
* Status changes

```
MovementLog
├── log_id
├── citizen_id
├── movement_type
├── description
├── date
└── officer_notes

```
`household_registry.json`

```
HouseholdCitizenRegistry
├── registry_id
├── household_number
├── head_of_household
├── citizen_name
├── NIC
├── date_of_birth
├── occupation
├── education_level
├── marital_status
├── address
├── contact_number
└── remarks
```

### 4. GN Officer Profile → `profile.json`

```
GN_Profile
├── officer_name
├── GN_division
├── office_address
├── contact_info
└── authentication_settings
```

---

## Entity Relationship Diagram (File-Based)

```
Household (1) -------- (Many) Citizen
Citizen (1) ---------- (Many) Request
GN_Profile ----------- manages all
HouseholdRegistry (1)
        ↓
    Citizen
        ↓
MovementLog (Many)

BirthRecord → creates Citizen
DeathRecord → closes Citizen status
```

---

## Screen → File Interaction Diagram

```
Login Screen
   ↓
Dashboard
   ├── Citizen Screen → citizens.json
   ├── Household Screen → households.json
   ├── Requests Screen → requests.json
   └── Profile Screen → profile.json
```

---

## Recommended Local File Folder Structure

```
/GNAppData
│
├── data
│   ├── citizens.json
│   ├── households.json
│   ├── requests.json
│   └── profile.json
│   ├── birth_records.json
│   ├── death_records.json
│   ├── movement_logs.json
│   ├── household_registry.json
│
│
├── documents
│   ├── certificates
│   ├── reports
│   └── attachments
│
└── backups
```

---

## Data Flow Diagram (Simplified)

```
User Input
   ↓
Validation Engine
   ↓
Controller Logic
   ↓
File Manager
   ↓
Local JSON Storage
   ↓
UI Refresh
```

---

## Design Principles

- Structured citizen registry
- Traceable request workflow
- Organized household records
- Administrative accountability
- Simple hierarchical relationships
- JSON-based local persistence instead of a database


## full system likely includes:
- Certificates
- Requests
- Approvals
- Land records
- Family records
- Administrative logs