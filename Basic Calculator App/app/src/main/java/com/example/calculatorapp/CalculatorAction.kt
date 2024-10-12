package com.example.calculatorapp

sealed class CalculatorAction {
    data class Number(val number: Int): CalculatorAction()
    object Calculate: CalculatorAction()
    object Clear: CalculatorAction()
    object Decimal: CalculatorAction()
    object Delete: CalculatorAction()
    data class Operation(val operation: CalculatorOperation): CalculatorAction()
    object Percentage: CalculatorAction()
}
