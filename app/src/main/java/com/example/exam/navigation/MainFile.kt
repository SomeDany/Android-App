package com.example.exam.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.exam.tasks.About
import com.example.exam.tasks.Home
import com.example.exam.tasks.Task1
import com.example.exam.tasks.Task2
import com.example.exam.tasks.Task3
import com.example.exam.tasks.Task4

@Composable
fun ScreenMain(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = Routes.Home.route)
    {
        composable(Routes.Home.route){
            Home(navController = navController)
        }
        composable(Routes.Task1.route){
            Task1()
        }
        composable(Routes.Task2.route){
            Task2()
        }
        composable(Routes.Task3.route){
            Task3()
        }
        composable(Routes.Task4.route){
            Task4()
        }
        composable(Routes.About.route){
            About()
        }
    }
}