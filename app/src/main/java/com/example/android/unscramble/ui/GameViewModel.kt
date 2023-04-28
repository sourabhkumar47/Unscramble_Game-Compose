package com.example.android.unscramble.ui

import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//Game UI state
private val _uiState = MutableStateFlow(GameUiState())

//Backing property
val iState: StateFlow<GameUiState> = _uiState.asStateFlow()

class GameViewModel : ViewModel() {
    private lateinit var currentWord: String
    private var usedWords: MutableSet<String> = mutableSetOf()

    private fun pickUpRandomWordAndShuffle(): String {
        currentWord = allWords.random()

        if (usedWords.contains(currentWord)) {
            return pickUpRandomWordAndShuffle()
        } else
            usedWords.add(currentWord)
        return pickUpRandomWordAndShuffle()
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()
        tempWord.shuffle()

        while (String(tempWord) == word) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    //fun that take user guessed word.
    //Inside the function, update the userGuess with the passed in guessedWord.
    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickUpRandomWordAndShuffle())
    }

    init {
        resetGame()
    }
}