package com.example.tictactoegame

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tictactoegame.ui.theme.Blue500
import com.example.tictactoegame.ui.theme.Pink500

@Composable
fun TicTacToeScreen(viewModel: GameViewModel, vsBot: Boolean) {
    val board = viewModel.board

    viewModel.vsBot.value = vsBot

    val winner = viewModel.winner.value
    val winnerSymbol = viewModel.winnerSymbol.value

    val botScore = viewModel.botScore.value
    val humanScore = viewModel.humanScore.value
    val opponentScore = viewModel.opponentScore.value

    val botSymbol = viewModel.botSymbol.value
    val humanSymbol = viewModel.humanSymbol.value
    val opponentSymbol = viewModel.opponentSymbol.value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(top = 24.dp)
                .align(Alignment.TopCenter)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(2f),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfileItem(
                    name = if (vsBot) "You" else "P1",
                    avatar = R.drawable.baseline_player_24,
                    symbol = humanSymbol,
                    score = humanScore
                )
                Text(text = "VS", fontSize = 36.sp, fontWeight = FontWeight.Bold)
                if (vsBot) {
                    ProfileItem(
                        name = "Android",
                        avatar = R.drawable.baseline_android_24,
                        symbol = botSymbol,
                        score = botScore
                    )
                } else {
                    ProfileItem(
                        name = "P2",
                        avatar = R.drawable.baseline_player_24,
                        symbol = opponentSymbol,
                        score = opponentScore
                    )
                }
            }

            Box(
                modifier = Modifier
                    .size(315.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .weight(3f)
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(315.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    LazyVerticalGrid(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .aspectRatio(1f),
                        columns = GridCells.Fixed(3)
                    ) {
                        board.forEachIndexed { pos, value ->
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .aspectRatio(1f)
                                        .clickable(
                                            interactionSource = MutableInteractionSource(),
                                            indication = null
                                        ) {
                                            viewModel.finishTurn(pos)
                                        }
                                        .drawBehind {
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(0f, 15f),
                                                end = Offset(0f, size.height - 15f),
                                                strokeWidth = 7f,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    floatArrayOf(
                                                        20f,
                                                        10f
                                                    ), 0f
                                                )
                                            )
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(size.width, 15f),
                                                end = Offset(size.width, size.height - 15f),
                                                strokeWidth = 7f,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    floatArrayOf(
                                                        20f,
                                                        10f
                                                    ), 0f
                                                )
                                            )
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(0f, 15f),
                                                end = Offset(size.width, 15f),
                                                strokeWidth = 7f,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    floatArrayOf(
                                                        20f,
                                                        10f
                                                    ), 0f
                                                )
                                            )
                                            drawLine(
                                                color = Color.Black,
                                                start = Offset(0f, size.height - 15f),
                                                end = Offset(size.width, size.height - 15f),
                                                strokeWidth = 7f,
                                                pathEffect = PathEffect.dashPathEffect(
                                                    floatArrayOf(
                                                        20f,
                                                        10f
                                                    ), 0f
                                                )
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    val animDuration = 500
                                    Column {
                                        AnimatedVisibility(
                                            visible = board[pos] != "",
                                            enter = scaleIn(tween(animDuration)) + fadeIn(
                                                tween(animDuration)
                                            ),
                                            exit = scaleOut(tween(animDuration)) + fadeOut(
                                                tween(animDuration)
                                            )
                                        ) {
                                            Icon(
                                                painter = painterResource(id = if (value == MoveList.O) R.drawable.baseline_circle_24 else R.drawable.baseline_cross_24),
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp),
                                                tint = if (value == MoveList.O) Blue500 else Pink500
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = winner,
                modifier = Modifier.weight(1f),
                fontSize = 32.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.ExtraBold,
                color = if (winnerSymbol == MoveList.O) Blue500 else if (winnerSymbol == MoveList.X) Pink500 else Color.Black
            )
            Button(
                onClick = { viewModel.startNewRound() },
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp),
                colors = ButtonDefaults.buttonColors(Color.White),
                border = BorderStroke(2.dp, Color.Black)
            ) {
                Text(text = "Reset")
            }

        }
    }
}