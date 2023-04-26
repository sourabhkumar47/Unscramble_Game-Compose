package com.example.android.unscramble.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//Game UI state
private val _uiState = MutableStateFlow(GameUiState())

//Backing property
val iState: StateFlow<GameUiState> = _uiState.asStateFlow()

class GameViewModel : ViewModel() {
}