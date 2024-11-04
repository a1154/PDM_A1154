package com.example.novacalculadora

class CalculatorOperations {
    fun add(a: Double, b: Double): Double = a + b
    fun subtract(a: Double, b: Double): Double = a - b
    fun multiply(a: Double, b: Double): Double = a * b
    fun divide(a: Double, b: Double): Double = if (b != 0.0) a / b else Double.NaN
}