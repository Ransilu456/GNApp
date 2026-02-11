package com.emarketing_paradice.gnsrilanka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import com.emarketing_paradice.gnsrilanka.ui.MainScreen
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.CitizenViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.HouseholdViewModel
import com.emarketing_paradice.gnsrilanka.viewmodel.RequestViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val repository = FileRepository(applicationContext)

        // Create a single ViewModel factory
        val viewModelFactory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return when {
                    modelClass.isAssignableFrom(AuthViewModel::class.java) -> AuthViewModel(repository) as T
                    modelClass.isAssignableFrom(CitizenViewModel::class.java) -> CitizenViewModel(repository) as T
                    modelClass.isAssignableFrom(HouseholdViewModel::class.java) -> HouseholdViewModel(repository) as T
                    modelClass.isAssignableFrom(RequestViewModel::class.java) -> RequestViewModel(repository) as T
                    else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
                }
            }
        }

        // Instantiate all ViewModels using the factory
        val authViewModel: AuthViewModel by viewModels { viewModelFactory }
        val citizenViewModel: CitizenViewModel by viewModels { viewModelFactory }
        val householdViewModel: HouseholdViewModel by viewModels { viewModelFactory }
        val requestViewModel: RequestViewModel by viewModels { viewModelFactory }

        setContent {
            GNAppTheme {
                MainScreen(
                    authViewModel = authViewModel,
                    citizenViewModel = citizenViewModel,
                    householdViewModel = householdViewModel,
                    requestViewModel = requestViewModel
                )
            }
        }
    }
}
