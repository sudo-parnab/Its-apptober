package com.example.calculatorapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculatorapp.ui.theme.CalculatorAppTheme
import com.example.calculatorapp.ui.theme.GradientColor1
import com.example.calculatorapp.ui.theme.GradientColor2
import com.example.calculatorapp.ui.theme.GradientColor3
import com.example.calculatorapp.ui.theme.MediumGray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorAppTheme {
                val viewModel = viewModel<CalculatorViewModel>()
                val state = viewModel.state
                val buttonSpacing = 8.dp
                Calculator(
                    state = state,
                    colors = listOf(
                        GradientColor1,
                        GradientColor2,
                        GradientColor3
                    ),
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MediumGray),
                    buttonSpacing = buttonSpacing,
                    onAction = viewModel::onAction
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorAppPreview() {
    CalculatorAppTheme {
        val viewModel = viewModel<CalculatorViewModel>()
        val state = viewModel.state
        val buttonSpacing = 8.dp
        Calculator(
            state = state,
            colors = listOf(
                GradientColor1,
                GradientColor2,
                GradientColor3
            ),
            modifier = Modifier
                .fillMaxSize()
                .background(MediumGray),
            buttonSpacing = buttonSpacing,
            onAction = viewModel::onAction
        )
    }
}