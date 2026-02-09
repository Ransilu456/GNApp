package com.emarketing_paradice.gnsrilanka

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.emarketing_paradice.gnsrilanka.data.repository.FileRepository
import com.emarketing_paradice.gnsrilanka.ui.MainScreen
import com.emarketing_paradice.gnsrilanka.ui.theme.GNAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = FileRepository(applicationContext)
        
        setContent {
            GNAppTheme {
                MainScreen(repository)
            }
        }
    }
}
