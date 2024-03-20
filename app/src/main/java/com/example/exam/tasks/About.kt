package com.example.exam.tasks

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun About(){
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Об авторе", fontSize = 30.sp, fontWeight = FontWeight.Bold )
        Spacer(modifier = Modifier.height(20.dp))
        Text("Студент ЕИ КФУ", fontSize = 20.sp)
        Text("Веклич Данил Николаевич", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text("Группа е0216", fontSize = 20.sp)
    }

}

