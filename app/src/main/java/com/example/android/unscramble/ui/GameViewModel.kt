package com.example.android.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    //Game UI state
    private val _uiState = MutableStateFlow(GameUiState())

    //Backing property
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    var userGuess by mutableStateOf("")
        private set

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
    fun updateUserGuess(guessedWord: String) {
        userGuess = guessedWord
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickUpRandomWordAndShuffle())
    }

    init {
        resetGame()
    }

    //check the user input
    fun checkUserGuess() {
        if (userGuess.equals(currentWord, ignoreCase = true)) {
            //increase score
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore)
        } else {
            //user guess word wrong, show error
            _uiState.update { currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                isGuessedWordWrong = false,
                currentScrambledWord = pickUpRandomWordAndShuffle(),
                currentWordCount = currentState.currentWordCount.inc(),
                score = updatedScore
            )
        }
    }
}