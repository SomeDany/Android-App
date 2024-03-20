package com.example.exam.navigation

sealed class Routes(val route:String) {
    object Home:Routes("Home")
    object Task1:Routes("Task1")
    object Task2:Routes("Task2")
    object Task3:Routes("Task3")
    object Task4:Routes("Task4")
    object About:Routes("About")
}