package com.example.exam.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.navigation.NavHostController
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exam.navigation.Routes

@Composable
fun Home(navController: NavHostController){
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ){

        Column(modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("Мои задачи",
                textAlign = TextAlign.Justify,
                fontWeight = FontWeight.Bold,
                fontSize = 30.sp,
                textDecoration = TextDecoration.Underline
                )

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate(Routes.Task1.route)},
                modifier = Modifier.size(width = 200.dp, height = 60.dp)) {
                Text("Задача 1")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate(Routes.Task2.route)},
                modifier = Modifier.size(width = 200.dp, height = 60.dp)) {
                Text("Задача 2")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate(Routes.Task3.route)},
                modifier = Modifier.size(width = 200.dp, height = 60.dp)) {
                Text("Задача 3")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate(Routes.Task4.route)},
                modifier = Modifier.size(width = 200.dp, height = 60.dp)) {
                Text("Задача 4")
            }

            Spacer(modifier = Modifier.height(20.dp))

            Button(onClick = { navController.navigate(Routes.About.route)},
                modifier = Modifier.size(width = 200.dp, height = 60.dp)) {
                Text("Об авторе")
            }
        }
    }
}