package com.example.taskmanager.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.taskmanager.TaskApplication
import com.example.taskmanager.data.lightrepository.LightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class ScreenModeViewModel(private val lightRepository: LightRepository): ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    init {
        viewModelScope.launch {
            lightRepository.access_mode
                .collect { mode ->
                    _isDarkTheme.value = mode
                }
        }
    }

    fun toggleDarkTheme(){
        viewModelScope.launch {
            val currentMode = lightRepository.access_mode.first()
            _isDarkTheme.value = currentMode
            lightRepository.saveMode(!currentMode)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TaskApplication
                val repo = application.userPreference.lightDataContainer
                ScreenModeViewModel(repo)
            }
        }
    }
}