package com.emarketing_paradice.gnsrilanka.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.emarketing_paradice.gnsrilanka.R
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientEnd
import com.emarketing_paradice.gnsrilanka.ui.theme.BlueGradientStart
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthUiState
import com.emarketing_paradice.gnsrilanka.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
        authViewModel: AuthViewModel,
        onLoginSuccess: () -> Unit,
        onNavigateToRegister: () -> Unit
) {
        val uiState by authViewModel.uiState.collectAsState()
        val focusManager = LocalFocusManager.current

        var nic by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        LaunchedEffect(authViewModel) {
                authViewModel.uiState.collectLatest {
                        if (it is AuthUiState.Success) {
                                onLoginSuccess()
                        }
                }
        }

        LoginScreenContent(
                uiState = uiState,
                nic = nic,
                password = password,
                passwordVisible = passwordVisible,
                onNicChange = { nic = it },
                onPasswordChange = { password = it },
                onPasswordVisibilityChange = { passwordVisible = !passwordVisible },
                onLogin = {
                        focusManager.clearFocus()
                        authViewModel.login(nic, password)
                },
                onNavigateToRegister = onNavigateToRegister
        )

        DisposableEffect(Unit) { onDispose { authViewModel.resetState() } }
}

@Composable
fun LoginScreenContent(
        uiState: AuthUiState,
        nic: String,
        password: String,
        passwordVisible: Boolean,
        onNicChange: (String) -> Unit,
        onPasswordChange: (String) -> Unit,
        onPasswordVisibilityChange: () -> Unit,
        onLogin: () -> Unit,
        onNavigateToRegister: () -> Unit
) {
        val focusManager = LocalFocusManager.current

        Scaffold(
                modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
                Box(
                        modifier =
                                Modifier.fillMaxSize()
                                        .background(
                                                Brush.verticalGradient(
                                                        colors =
                                                                listOf(
                                                                        BlueGradientStart,
                                                                        BlueGradientEnd
                                                                )
                                                )
                                        )
                                        .padding(innerPadding)
                ) {
                        Column(
                                modifier =
                                        Modifier.fillMaxSize()
                                                .padding(24.dp)
                                                .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                        ) {
                                Spacer(modifier = Modifier.height(32.dp))

                                // Professional Icon container
                                Surface(
                                        modifier = Modifier.size(100.dp),
                                        shape = RoundedCornerShape(32.dp),
                                        color = Color.White.copy(alpha = 0.2f),
                                        border =
                                                androidx.compose.foundation.BorderStroke(
                                                        1.dp,
                                                        Color.White.copy(alpha = 0.3f)
                                                )
                                ) {
                                        Box(contentAlignment = Alignment.Center) {
                                                Icon(
                                                        painter =
                                                                painterResource(
                                                                        id =
                                                                                R.drawable
                                                                                        .ic_solar_user_circle
                                                                ),
                                                        contentDescription = "Logo",
                                                        tint = Color.White,
                                                        modifier = Modifier.size(56.dp)
                                                )
                                        }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Text(
                                        "Welcome Back",
                                        style =
                                                MaterialTheme.typography.headlineLarge.copy(
                                                        fontWeight = FontWeight.Bold
                                                ),
                                        color = Color.White,
                                        textAlign = TextAlign.Center
                                )
                                Text(
                                        "Sign in to your dashboard",
                                        style = MaterialTheme.typography.bodyLarge,
                                        color = Color.White.copy(alpha = 0.8f),
                                        textAlign = TextAlign.Center
                                )

                                Spacer(modifier = Modifier.height(48.dp))

                                Card(
                                        shape = RoundedCornerShape(24.dp),
                                        colors =
                                                CardDefaults.cardColors(
                                                        containerColor = Color.White
                                                ),
                                        elevation =
                                                CardDefaults.cardElevation(defaultElevation = 8.dp),
                                        modifier = Modifier.fillMaxWidth()
                                ) {
                                        Column(
                                                modifier = Modifier.padding(24.dp),
                                                verticalArrangement = Arrangement.spacedBy(16.dp)
                                        ) {
                                                OutlinedTextField(
                                                        value = nic,
                                                        onValueChange = onNicChange,
                                                        label = { Text("NIC Number") },
                                                        leadingIcon = {
                                                                Icon(
                                                                        painter =
                                                                                painterResource(
                                                                                        id =
                                                                                                R.drawable
                                                                                                        .ic_solar_user_id
                                                                                ),
                                                                        null,
                                                                        modifier =
                                                                                Modifier.size(24.dp)
                                                                )
                                                        },
                                                        isError = uiState is AuthUiState.Error,
                                                        modifier = Modifier.fillMaxWidth(),
                                                        shape = RoundedCornerShape(16.dp),
                                                        keyboardOptions =
                                                                KeyboardOptions(
                                                                        imeAction = ImeAction.Next
                                                                ),
                                                        singleLine = true,
                                                        colors =
                                                                OutlinedTextFieldDefaults.colors(
                                                                        focusedBorderColor =
                                                                                BlueGradientStart,
                                                                        focusedLabelColor =
                                                                                BlueGradientStart,
                                                                        focusedContainerColor =
                                                                                Color(0xFFF8FAFC),
                                                                        unfocusedContainerColor =
                                                                                Color(0xFFF8FAFC)
                                                                )
                                                )

                                                OutlinedTextField(
                                                        value = password,
                                                        onValueChange = onPasswordChange,
                                                        label = { Text("Password") },
                                                        leadingIcon = {
                                                                Icon(
                                                                        painter =
                                                                                painterResource(
                                                                                        id =
                                                                                                R.drawable
                                                                                                        .ic_solar_shield_check
                                                                                ),
                                                                        null,
                                                                        modifier =
                                                                                Modifier.size(24.dp)
                                                                )
                                                        },
                                                        trailingIcon = {
                                                                val icon =
                                                                        if (passwordVisible)
                                                                                Icons.Filled
                                                                                        .Visibility
                                                                        else
                                                                                Icons.Filled
                                                                                        .VisibilityOff
                                                                IconButton(
                                                                        onClick = onPasswordVisibilityChange
                                                                ) {
                                                                        Icon(
                                                                                imageVector = icon,
                                                                                contentDescription =
                                                                                        null
                                                                        )
                                                                }
                                                        },
                                                        isError = uiState is AuthUiState.Error,
                                                        visualTransformation =
                                                                if (passwordVisible)
                                                                        VisualTransformation.None
                                                                else PasswordVisualTransformation(),
                                                        modifier = Modifier.fillMaxWidth(),
                                                        shape = RoundedCornerShape(16.dp),
                                                        keyboardOptions =
                                                                KeyboardOptions(
                                                                        keyboardType =
                                                                                KeyboardType
                                                                                        .Password,
                                                                        imeAction = ImeAction.Done
                                                                ),
                                                        keyboardActions =
                                                                KeyboardActions(
                                                                        onDone = {
                                                                                onLogin()
                                                                        }
                                                                ),
                                                        singleLine = true,
                                                        colors =
                                                                OutlinedTextFieldDefaults.colors(
                                                                        focusedBorderColor =
                                                                                BlueGradientStart,
                                                                        focusedLabelColor =
                                                                                BlueGradientStart,
                                                                        focusedContainerColor =
                                                                                Color(0xFFF8FAFC),
                                                                        unfocusedContainerColor =
                                                                                Color(0xFFF8FAFC)
                                                                )
                                                )

                                                if (uiState is AuthUiState.Error) {
                                                        Text(
                                                                text =
                                                                        (uiState as
                                                                                        AuthUiState.Error)
                                                                                .message,
                                                                color =
                                                                        MaterialTheme.colorScheme
                                                                                .error,
                                                                style =
                                                                        MaterialTheme.typography
                                                                                .bodySmall,
                                                                modifier =
                                                                        Modifier.padding(
                                                                                start = 8.dp
                                                                        )
                                                        )
                                                }

                                                Spacer(modifier = Modifier.height(8.dp))

                                                Button(
                                                        onClick = onLogin,
                                                        modifier =
                                                                Modifier.fillMaxWidth()
                                                                        .height(56.dp),
                                                        shape = RoundedCornerShape(16.dp),
                                                        enabled = uiState !is AuthUiState.Loading,
                                                        colors =
                                                                ButtonDefaults.buttonColors(
                                                                        containerColor =
                                                                                BlueGradientStart
                                                                ),
                                                        elevation =
                                                                ButtonDefaults.buttonElevation(
                                                                        defaultElevation = 4.dp
                                                                )
                                                ) {
                                                        if (uiState is AuthUiState.Loading) {
                                                                CircularProgressIndicator(
                                                                        modifier =
                                                                                Modifier.size(
                                                                                        24.dp
                                                                                ),
                                                                        color = Color.White
                                                                )
                                                        } else {
                                                                Text(
                                                                        "Login",
                                                                        style =
                                                                                MaterialTheme
                                                                                        .typography
                                                                                        .titleMedium
                                                                                        .copy(
                                                                                                fontWeight =
                                                                                                        FontWeight
                                                                                                                .Bold
                                                                                        )
                                                                )
                                                        }
                                                }
                                        }
                                }

                                Spacer(modifier = Modifier.height(24.dp))

                                Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                                "Don't have an account?",
                                                color = Color.White.copy(alpha = 0.9f)
                                        )
                                        TextButton(onClick = onNavigateToRegister) {
                                                Text(
                                                        "Register here",
                                                        fontWeight = FontWeight.Bold,
                                                        color = Color.White,
                                                        style = MaterialTheme.typography.bodyLarge
                                                )
                                        }
                                }
                        }
                }
        }
}

import androidx.compose.ui.tooling.preview.Preview

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
        LoginScreenContent(
                uiState = AuthUiState.Idle,
                nic = "",
                password = "",
                passwordVisible = false,
                onNicChange = {},
                onPasswordChange = {},
                onPasswordVisibilityChange = {},
                onLogin = {},
                onNavigateToRegister = {}
        )
}

