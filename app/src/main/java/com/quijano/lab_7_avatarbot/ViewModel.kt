package com.quijano.lab_7_avatarbot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DataStoreUiState(
    val showSettings: Boolean = false,
    val name: String = "Antonio Quijano",
    val role: String = "Estudiante de Ing. De Software",
    val year: Int = 4,
    val phone: String = "+507: 6175-2856",
    val handle: String = "@AAQuijano",
    val email: String = "alexisq05@hotmail.com",
    val showContactInfo: Boolean = true
)

class DataStoreViewModel(application: Application) : AndroidViewModel(application) {
    private val _uiState = MutableStateFlow(DataStoreUiState())
    val uiState: StateFlow<DataStoreUiState> = _uiState

    init {
        getDataStoreValues()
    }

    private fun getDataStoreValues() {
        val context = getApplication<Application>()

        viewModelScope.launch {
            DataStoreInstance.getStringPreferences(context, DataStoreInstance.NAME_KEY)
                .collect { value ->
                    _uiState.update { it.copy(name = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getStringPreferences(context, DataStoreInstance.ROLE_KEY)
                .collect { value ->
                    _uiState.update { it.copy(role = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getIntPreferences(context)
                .collect { value ->
                    _uiState.update { it.copy(year = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getStringPreferences(context, DataStoreInstance.PHONE_KEY)
                .collect { value ->
                    _uiState.update { it.copy(phone = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getStringPreferences(context, DataStoreInstance.HANDLE_KEY)
                .collect { value ->
                    _uiState.update { it.copy(handle = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getStringPreferences(context, DataStoreInstance.EMAIL_KEY)
                .collect { value ->
                    _uiState.update { it.copy(email = value) }
                }
        }

        viewModelScope.launch {
            DataStoreInstance.getBooleanPreferences(context)
                .collect { value ->
                    _uiState.update { it.copy(showContactInfo = value) }
                }
        }
    }



    fun toggleSettings() {
        _uiState.update {
            it.copy(showSettings = !it.showSettings)
        }
    }

    fun saveValuesInDataStore() {
        viewModelScope.launch {
            DataStoreInstance.saveAllPreferences(
                getApplication(),
                _uiState.value.name,
                _uiState.value.role,
                _uiState.value.year,
                _uiState.value.phone,
                _uiState.value.handle,
                _uiState.value.email,
                _uiState.value.showContactInfo
            )
        }
    }

    fun updateName(value: String) = _uiState.update { it.copy(name = value) }
    fun updateRole(value: String) = _uiState.update { it.copy(role = value) }
    fun updateYear(value: Int) = _uiState.update { it.copy(year = value) }
    fun updatePhone(value: String) = _uiState.update { it.copy(phone = value) }
    fun updateHandle(value: String) = _uiState.update { it.copy(handle = value) }
    fun updateEmail(value: String) = _uiState.update { it.copy(email = value) }
    fun toggleContactInfo() = _uiState.update { it.copy(showContactInfo = !it.showContactInfo) }
}

class DataStoreViewModelFactory(
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DataStoreViewModel::class.java)) {
            return DataStoreViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}