package com.example.android.unscramble.ui.test

import com.example.android.unscramble.data.SCORE_INCREASE
import com.example.android.unscramble.ui.GameViewModel
import com.example.android.unscramble.ui.data.getUnscrambledWord
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Test
import org.testng.Assert.assertNotEquals
import org.testng.Assert.assertTrue

class GameViewModelTest {
    private val viewModel = GameViewModel()

    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)
        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        // Assert that checkUserGuess() method updates isGuessedWordWrong is updated correctly.
        assertFalse(currentGameUiState.isGuessedWordWrong)
        // Assert that score is updated correctly.
        assertEquals(20, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        // Given an incorrect word as input
        val incorrectPlayerWord = "and"

        viewModel.updateUserGuess(incorrectPlayerWord)
        viewModel.checkUserGuess()

        val currentGameUiState = viewModel.uiState.value

        //score is unchanged
        assertEquals(0, currentGameUiState.score)
        //assert that checkUserGuess() method updates isGuessedWordWrong correctly.
        assertTrue(currentGameUiState.isGuessedWordWrong)
    }

    //Boundary test case
    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambledWord)

        assertNotEquals(unScrambledWord, gameUiState.currentScrambledWord)
        assertTrue(gameUiState.currentWordCount == 1)
        assertTrue(gameUiState.score == 0)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
    }

    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

}