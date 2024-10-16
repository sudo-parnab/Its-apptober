package com.example.tictactoegame

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GameViewModel: ViewModel() {
    var board = mutableStateListOf("", "", "", "", "", "", "", "", "")

    var vsBot = mutableStateOf(false)

    val winner = mutableStateOf("")
    val winnerSymbol = mutableStateOf("")

    val botScore = mutableStateOf(0)
    val humanScore = mutableStateOf(0)
    val opponentScore = mutableStateOf(0)

    private val isHumansTurn = mutableStateOf(true)
    private val isOpponentsTurn = mutableStateOf(false)

    val botSymbol = mutableStateOf(MoveList.X)
    val humanSymbol = mutableStateOf(MoveList.O)
    val opponentSymbol = mutableStateOf(MoveList.X)

    private fun getWinningPlayer(board:List<String>): String {
        return when {
            (board[0] != "" && board[0] == board[1] && board[1] == board[2]) -> board[0]
            (board[3] != "" && board[3] == board[4] && board[4] == board[5]) -> board[3]
            (board[6] != "" && board[6] == board[7] && board[7] == board[8]) -> board[6]
            (board[0] != "" && board[0] == board[3] && board[3] == board[6]) -> board[0]
            (board[1] != "" && board[1] == board[4] && board[4] == board[7]) -> board[1]
            (board[2] != "" && board[2] == board[5] && board[5] == board[8]) -> board[2]
            (board[0] != "" && board[0] == board[4] && board[4] == board[8]) -> board[0]
            (board[2] != "" && board[2] == board[4] && board[4] == board[6]) -> board[2]
            (board.none { it == "" }) -> "D"
            else -> "N"
        }
    }

    fun finishTurn(pos: Int) {
        if (vsBot.value) {
            viewModelScope.launch {
                if (isHumansTurn.value && board[pos] == "" && winner.value.isEmpty()) {
                    board[pos] = humanSymbol.value
                    when (getWinningPlayer(board)) {
                        humanSymbol.value -> {
                            winner.value = "You Won"
                            winnerSymbol.value = humanSymbol.value
                            humanScore.value += 2
                        }
                        "D" -> {
                            winner.value = "Draw"
                            botScore.value += 1
                            humanScore.value += 1
                        }
                        else -> {
                            isHumansTurn.value = false
                            delay(listOf(100L, 300L, 500L, 700L, 900L).random())
                            androidBot()
                        }
                    }
                }
            }
        } else {
            viewModelScope.launch {
                if (isHumansTurn.value && board[pos] == "" && winner.value.isEmpty()) {
                    board[pos] = humanSymbol.value
                    when (getWinningPlayer(board)) {
                        humanSymbol.value -> {
                            winner.value = "P1 Won"
                            winnerSymbol.value = humanSymbol.value
                            humanScore.value += 2
                        }
                        "D" -> {
                            winner.value = "Draw"
                            humanScore.value += 1
                            opponentScore.value += 1
                        }
                        else -> {
                            isHumansTurn.value = false
                            isOpponentsTurn.value = true
                        }
                    }
                }
                else if (isOpponentsTurn.value && board[pos] == "" && winner.value.isEmpty()) {
                    board[pos] = opponentSymbol.value
                    when (getWinningPlayer(board)) {
                        opponentSymbol.value -> {
                            winner.value = "P2 Won"
                            winnerSymbol.value = opponentSymbol.value
                            opponentScore.value += 2
                        }
                        "D" -> {
                            winner.value = "Draw"
                            opponentScore.value += 1
                            humanScore.value += 1
                        }
                        else -> {
                            isOpponentsTurn.value = false
                            isHumansTurn.value = true
                        }
                    }
                }
            }
        }
    }

    fun startNewRound(){
        board = mutableStateListOf("", "", "", "", "", "", "", "", "")
        winner.value = ""
        winnerSymbol.value = ""
        if (vsBot.value) {
            botSymbol.value = humanSymbol.value.also{ humanSymbol.value = botSymbol.value }
            isHumansTurn.value = humanSymbol.value == MoveList.O
            if(!isHumansTurn.value) {
                board[listOf(0, 1, 2, 3, 4, 5, 6, 7, 8).random()] = botSymbol.value
                isHumansTurn.value = true
            }
        } else {
            opponentSymbol.value = humanSymbol.value.also{ humanSymbol.value = opponentSymbol.value }
            isHumansTurn.value = humanSymbol.value == MoveList.O
            isOpponentsTurn.value = !isHumansTurn.value
        }
    }

    private fun androidBot(){
        viewModelScope.launch {
            var moveValue = -1
            var bestEval = -1000
            board.toList().forEachIndexed { index, move ->
                if(move==""){
                    val copyBoard = board.toMutableList()
                    copyBoard[index] = botSymbol.value
                    val eval = minimax(copyBoard,false)
                    if(eval>bestEval){
                        bestEval = eval
                        moveValue = index
                    }
                }
            }

            board[moveValue] = botSymbol.value
            when (getWinningPlayer(board)) {
                botSymbol.value -> {
                    winner.value = "Android Won"
                    winnerSymbol.value = botSymbol.value
                    botScore.value += 2
                }
                "D" -> {
                    winner.value = "Draw"
                    botScore.value += 1
                    humanScore.value += 1
                }
                else -> {
                    isHumansTurn.value = true
                }
            }
        }

    }


    private fun minimax(board:List<String>, isMaximizing:Boolean):Int{
        val win = getWinningPlayer(board)
        if(win==botSymbol.value) return 1
        if(win==humanSymbol.value) return -1
        if(win=="D") return 0
        if(isMaximizing){
            var bestScore = -1000
            var i = 0
            while(i<9){
                if(board[i] == ""){
                    val newBoard = board.toMutableList()
                    newBoard[i] = botSymbol.value
                    val score = minimax(newBoard,false)
                    bestScore = maxOf(score,bestScore)


                }
                i++
            }
            return bestScore
        }else{
            var i = 0
            var bestScore = 1000
            while(i<9){
                if(board[i] == ""){
                    val newBoard = board.toMutableList()
                    newBoard[i] = humanSymbol.value
                    val score = minimax(newBoard,true)
                    bestScore = minOf(score,bestScore)
                }
                i++
            }
            return bestScore
        }

    }
}