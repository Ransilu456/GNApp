### Prompt for Android Studio Gemini

I have a Jetpack Compose app with `MainActivity`, `MainScreen`, `AppNavHost`, and `MainNavGraph`. Currently, I have the following issues:

1. `MainActivity` is passing a `FileRepository` to `MainScreen` instead of the required `AuthViewModel` and other ViewModels.
2. `MainScreen` and `MainNavGraph` require `citizenViewModel`, `householdViewModel`, and `requestViewModel`, but they are missing.
3. `padding` from `Scaffold` is not forwarded correctly to `MainNavGraph`.
4. `AppNavHost` tries to use an undefined `padding` variable.

I want Gemini to **fix the navigation and ViewModel wiring** so that:

- `MainActivity` creates all required ViewModels (`AuthViewModel`, `CitizenViewModel`, `HouseholdViewModel`, `RequestViewModel`) and passes them to `MainScreen`.
- `MainScreen` passes the `padding` from `Scaffold` correctly to `MainNavGraph` using `Modifier.padding(padding)`.
- `AppNavHost` accepts `modifier: Modifier` and forwards it to `NavHost`.
- Bottom bar visibility works correctly with the correct routes.
- Navigation between `Login`, `Register`, `Home`, `CitizenList`, `HouseholdList`, `RequestList`, `Profile`, and all add/edit/detail screens works properly.
- Remove all unused parameters and unresolved references.

Please rewrite **all affected files** (`MainActivity`, `MainScreen`, `AppNavHost`, `MainNavGraph`) with correct types, padding handling, and ViewModel usage.
