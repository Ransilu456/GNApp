package com.emarketing_paradice.gnsrilanka.viewmodel

sealed class FormStatus {
    object Idle : FormStatus()
    object Loading : FormStatus()
    object Success : FormStatus()
    data class Error(val message: String) : FormStatus()
}
