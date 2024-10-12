package com.example.calculatorapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculatorapp.ui.theme.Orange

@Composable
fun Calculator(
    state: CalculatorState,
    colors: List<Color>,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit
) {
    Box(
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            Text(
                text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.End)
                    .padding(vertical = 32.dp, horizontal = 16.dp)
                    .weight(1f),
                fontWeight = FontWeight.Light,
                fontSize = 48.sp,
                lineHeight = 48.sp,
                color = Color.White,
                maxLines = 2
            )
            val linearGradientBrush = Brush.linearGradient(
                colors = colors,
                start = Offset(Float.POSITIVE_INFINITY, 0f),
                end = Offset(0f, Float.POSITIVE_INFINITY),
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(shape = RoundedCornerShape(25.dp))
                    .background(linearGradientBrush)
            ) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(50.dp))
                        .padding(8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Black.copy(alpha = 0.09f)
                    )
                ) {
                    Column(verticalArrangement = Arrangement.spacedBy(buttonSpacing)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(
                                symbol = "AC",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Clear)
                                }
                            )
                            CalculatorButton(
                                symbol = "DEL",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Delete)
                                }
                            )
                            CalculatorButton(
                                symbol = "%",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Percentage)
                                }
                            )
                            CalculatorButton(
                                symbol = "÷",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Operation(CalculatorOperation.Divide))
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(
                                symbol = "7",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(7))
                                }
                            )
                            CalculatorButton(
                                symbol = "8",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(8))
                                }
                            )
                            CalculatorButton(
                                symbol = "9",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(9))
                                }
                            )
                            CalculatorButton(
                                symbol = "×",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Operation(CalculatorOperation.Multiply))
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(
                                symbol = "4",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(4))
                                }
                            )
                            CalculatorButton(
                                symbol = "5",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(5))
                                }
                            )
                            CalculatorButton(
                                symbol = "6",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(6))
                                }
                            )
                            CalculatorButton(
                                symbol = "-",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Operation(CalculatorOperation.Subtract))
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(
                                symbol = "1",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(1))
                                }
                            )
                            CalculatorButton(
                                symbol = "2",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(2))
                                }
                            )
                            CalculatorButton(
                                symbol = "3",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Number(3))
                                }
                            )
                            CalculatorButton(
                                symbol = "+",
                                color = Orange,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Operation(CalculatorOperation.Add))
                                }
                            )
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                        ) {
                            CalculatorButton(
                                symbol = "0",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(2f)
                                    .weight(2f),
                                onClick = {
                                    onAction(CalculatorAction.Number(0))
                                }
                            )
                            CalculatorButton(
                                symbol = ".",
                                color = Color.White,
                                modifier = Modifier
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Decimal)
                                }
                            )
                            CalculatorButton(
                                symbol = "=",
                                color = Color.White,
                                modifier = Modifier
                                    .background(Orange)
                                    .aspectRatio(1f)
                                    .weight(1f),
                                onClick = {
                                    onAction(CalculatorAction.Calculate)
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

