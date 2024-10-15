package com.example.tictactoegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tictactoegame.ui.theme.Black300
import com.example.tictactoegame.ui.theme.Black500
import com.example.tictactoegame.ui.theme.Blue300
import com.example.tictactoegame.ui.theme.Orange
import com.example.tictactoegame.ui.theme.TicTacToeGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TicTacToeGameTheme {
                val navController = rememberNavController()
                val viewModel = GameViewModel()
                NavHost(navController = navController, startDestination = "MainScreen") {
                    composable("MainScreen") {
                        viewModel.botScore.value = 0
                        viewModel.humanScore.value = 0
                        viewModel.opponentScore.value = 0
                        viewModel.botSymbol.value = MoveList.O
                        viewModel.humanSymbol.value = MoveList.X
                        viewModel.opponentSymbol.value = MoveList.O
                        viewModel.startNewRound()
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.linearGradient(
                                        colors = listOf(Black300, Black500),
                                        start = Offset(0f, 0f),
                                        end = Offset(
                                            Float.POSITIVE_INFINITY,
                                            Float.POSITIVE_INFINITY
                                        )
                                    )
                                )
                        ) {
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.tictactoelogo),
                                    contentDescription = "TicTacToe",
                                    modifier = Modifier.size(280.dp)
                                )
                            }
                            Row(
                                modifier = Modifier
                                    .weight(1f)
                                    .fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Button(
                                    onClick = { navController.navigate("PlayerVSPlayer") },
                                    modifier = Modifier.padding(24.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(Blue300)
                                ) {
                                    GoButton(
                                        image1 = R.drawable.baseline_player_24,
                                        image2 = R.drawable.baseline_player_24
                                    )
                                }
                                Button(
                                    onClick = { navController.navigate("PlayerVSAndroid") },
                                    modifier = Modifier.padding(24.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(Orange)
                                ) {
                                    GoButton(
                                        image1 = R.drawable.baseline_player_24,
                                        image2 = R.drawable.baseline_android_24
                                    )
                                }
                            }
                        }
                    }
                    composable("PlayerVSPlayer") {
                        TicTacToeScreen(viewModel = viewModel, vsBot = false)
                    }
                    composable("PlayerVSAndroid") {
                        TicTacToeScreen(viewModel = viewModel, vsBot = true)
                    }
                }
            }
        }
    }
}
