package com.example.exam.tasks

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Task2(){

    var points by remember { mutableStateOf(emptyList<Offset>()) }
    var xCoord by remember { mutableStateOf("") }
    var yCoord by remember { mutableStateOf("") }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.verticalScroll(ScrollState(0))
    ) {
        Column(
            Modifier
                .border(1.dp, Color.Black)
                .padding(10.dp)) {
            Text(
                text = "Задача 2. Написать программу, которая определяет," +
                        " попадает ли точка с заданными координатами в область," +
                        " закрашенную на рисунке черным цветом. Задать координаты 10 точек с помощью датчика случайных чисел." +
                        " Если точка попадает в область, то цвет точки зеленый, если нет – красный, а если на границе – синий.",
                fontSize = 14.sp
            )
        }
        Spacer(modifier = Modifier.height(5.dp))

        Graph(points)
        Row {
            TextField(
                value = xCoord,
                modifier = Modifier.weight(0.5f),
                onValueChange = { newText ->
                    xCoord = newText
                },
                label = {Text(text = "Координата X") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            TextField(
                value = yCoord,
                modifier = Modifier.weight(0.5f),
                onValueChange = { newText -> yCoord = newText },
                label = { Text(text = "Координата Y") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(Modifier.height(2.dp))
        Row {
            val context = LocalContext.current

            Button(
                onClick = {
                    val x = xCoord.toFloat()
                    val y = yCoord.toFloat()

                    if (x >= 10f){
                        Toast.makeText(context, "Введите значение Х меньше 10", Toast.LENGTH_SHORT).show()
                    }
                    else{
                        if( y >= 10f){
                            Toast.makeText(context, "Введите значение Y меньше 10", Toast.LENGTH_SHORT).show()
                        }
                        else{
                            if (x <= -10f){
                                Toast.makeText(context, "Введите значение Х больше -10", Toast.LENGTH_SHORT).show()
                            }
                            else{
                                if (y <= -10f){
                                    Toast.makeText(context, "Введите значение Y больше -10", Toast.LENGTH_SHORT).show()
                                }
                                else{
                                    points = points + listOf(Offset(x, y))
                                }
                            }
                        }
                    }
                },
                Modifier.weight(0.5f),
                enabled = xCoord.isNotEmpty() && yCoord.isNotEmpty()
            ){
                Text("Разместить точку")
            }

            Button(
                onClick = {
                    var list = emptyList<Offset>()

                    for (i in 1..10) {
                        val randomX = Random.nextDouble(-3.0, 3.0)
                        val randomY = Random.nextDouble(-3.0, 3.0)
                        list = list + (Offset(randomX.toFloat(), randomY.toFloat()))
                    }
                    points = list
                },
                Modifier.weight(0.5f)
            ){
                Text( "Случайные точки")
            }
        }
        var showCode by remember { mutableStateOf(false) }
        // Кнопка показа кода
        Button(onClick = {
            showCode = !showCode
        }) {
            Text(if(!showCode)"Показать код" else "Убрать код", fontSize = 13.sp)
        }
        if (showCode){
            Code2()
        }

    }

}

@Composable
fun Graph(points: List<Offset>) {
    val textMeasurer = rememberTextMeasurer()

    Canvas(
        modifier = Modifier
            .size(400.dp)
    ) {
        val sizec = size.width

        drawRect(
            color = Color.Black,
            topLeft = Offset(7 * sizec / 20, 7*sizec / 20),
            size = Size((6 * sizec / 20), (6 * sizec / 20))
        )

        drawCircle(Color.White, radius = 3*sizec/20, center = Offset((7*sizec/20),(7*sizec/20)))
        drawCircle(Color.White, radius = 3*sizec/20, center = Offset((13*sizec/20),(13*sizec/20)))

        drawCircle(Color.Black, radius = 3*sizec/20, center = Offset((7*sizec/20),(7*sizec/20)), style = Stroke(width = 1.dp.toPx()))
        drawCircle(Color.Black, radius = 3*sizec/20, center = Offset((13*sizec/20),(13*sizec/20)), style = Stroke(width = 1.dp.toPx()))

        drawRect(
            color = Color.Black,
            topLeft = Offset(7.03f * sizec / 20, 7.03f*sizec / 20),
            size = Size((5.94f * sizec / 20), (5.94f * sizec / 20)),
            style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))

        )
        drawLine(
            start = Offset(7*sizec/20, 7*sizec/20),
            end = Offset(sizec/2, sizec/2),
            color = Color.Black,
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            start = Offset(sizec/2, sizec/2),
            end = Offset(13*sizec/20, 13*sizec/20),
            color = Color.Black,
            strokeWidth = 1.dp.toPx()
        )

        /// Радиус
        val trianglePathR = Path().apply {
            moveTo(9*sizec/20+10f, 9*sizec/20+10f)
            lineTo(9*sizec/20+5f, 9*sizec/20-14f)
            lineTo(9*sizec/20-14f, 9*sizec/20+5f)
            close()
        }
        drawPath(trianglePathR, color = Color.Black)
        val trianglePathR1 = Path().apply {
            moveTo(11*sizec/20-10f, 11*sizec/20-10f)
            lineTo(11*sizec/20-5f, 11*sizec/20+14f)
            lineTo(11*sizec/20+14f, 11*sizec/20-5f)
            close()
        }
        drawPath(trianglePathR1, color = Color.Black)
        drawText(textMeasurer,
            text = "R",
            topLeft = Offset(8*sizec/20,8.5f*sizec/20),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )
        drawText(textMeasurer,
            text = "R",
            topLeft = Offset(10.5f*sizec/20,11.2f*sizec/20),
            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
        )

        // ось X
        drawLine(
            color = Color.Black,
            start = Offset(0f, sizec / 2),
            end = Offset(sizec, sizec / 2),
            strokeWidth = 1.dp.toPx()
        )

        // Рисование меток на оси X
        for (i in -9..9) {
            val x = (i + 10) * sizec / 20
            drawLine(
                color = Color.Black,
                start = Offset(x,  sizec / 2 -10f),
                end = Offset(x + 1f, sizec/2 +10f)
            )
            drawText(textMeasurer,
                i.toString(),
                Offset(x, sizec / 2 + 20),
                TextStyle(fontSize = 5.sp))
        }
        drawText(textMeasurer,
            text = "X",
            topLeft = Offset(sizec-23,sizec/2-30),
            style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)
        )
        val trianglePathX = Path().apply {
            moveTo(sizec, sizec/2)
            lineTo(sizec-14, sizec/2-6)
            lineTo(sizec-14, sizec/2+6)
            close()
        }
        drawPath(trianglePathX, color = Color.Black)

        // ось Y
        drawLine(
            color = Color.Black,
            start = Offset(sizec / 2, 0f),
            end = Offset(sizec / 2, sizec),
            strokeWidth = 1.dp.toPx()
        )

        // Рисование меток на оси Y
        for (i in -9..9) {
            val y = (10 - i) * sizec / 20
            drawLine(
                color = Color.Black,
                start = Offset(10 * sizec / 20 - 10f / 2, y),
                end = Offset(10 * sizec / 20 + 10f, y + 1f)
            )
            drawText(textMeasurer,
                i.toString(),
                Offset(sizec / 2 - 20f, y),
                TextStyle(fontSize = 5.sp)
            )
        }
        drawText(textMeasurer,
            text = "Y",
            topLeft = Offset(sizec/2-30f,15f),
            style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)
        )
        val trianglePathY = Path().apply {
            moveTo(sizec/2, 0f)
            lineTo(sizec/2-6f,14f)
            lineTo(sizec/2+6f,14f)
            close()
        }
        drawPath(trianglePathY, color = Color.Black)

        for (i in points) {
            val randomX = i.x
            val randomY = i.y


            if ((randomY > -3) && (randomY < 3) && (randomX > -3) && (randomX < 3) && ((randomX - 3) * (randomX - 3) + (randomY + 3) * (randomY + 3) > 9) && ((randomX + 3) * (randomX + 3) + (randomY - 3) * (randomY - 3) > 9)) {
                drawCircle(
                    color = Color.Green,
                    radius = 3f,
                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)
                )
            } else if (((randomX + 3) * (randomX + 3) + (randomY - 3) * (randomY - 3) >= 9f) && (randomY <= 3) && (randomY >= -3) && (randomX >= -3) && (randomX <= 3) && ((randomX - 3) * (randomX - 3) + (randomY + 3) * (randomY + 3) >= 9f)) {
                drawCircle(
                    color = Color.Blue,
                    radius = 3f,
                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)
                )
            } else {
                drawCircle(
                    color = Color.Red,
                    radius = 3f,
                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)
                )
            }
        }
    }
}


@Composable
fun Code2(){
    Text(text = "@OptIn(ExperimentalMaterial3Api::class)\n" +
            "@Composable\n" +
            "fun Task2(){\n" +
            "\n" +
            "    var points by remember { mutableStateOf(emptyList<Offset>()) }\n" +
            "    var xCoord by remember { mutableStateOf(\"\") }\n" +
            "    var yCoord by remember { mutableStateOf(\"\") }\n" +
            "\n" +
            "    Column(\n" +
            "        horizontalAlignment = Alignment.CenterHorizontally,\n" +
            "        modifier = Modifier.verticalScroll(ScrollState(0))\n" +
            "    ) {\n" +
            "        Column(\n" +
            "            Modifier\n" +
            "                .border(1.dp, Color.Black)\n" +
            "                .padding(10.dp)) {\n" +
            "            Text(\n" +
            "                text = \"Задача 2. Написать программу, которая определяет,\" +\n" +
            "                        \" попадает ли точка с заданными координатами в область,\" +\n" +
            "                        \" закрашенную на рисунке черным цветом. Задать координаты 10 точек с помощью датчика случайных чисел.\" +\n" +
            "                        \" Если точка попадает в область, то цвет точки зеленый, если нет – красный, а если на границе – синий.\",\n" +
            "                fontSize = 14.sp\n" +
            "            )\n" +
            "        }\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        Graph(points)\n" +
            "        Row {\n" +
            "            TextField(\n" +
            "                value = xCoord,\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                onValueChange = { newText ->\n" +
            "                    xCoord = newText\n" +
            "                },\n" +
            "                label = {Text(text = \"Координата X\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "            TextField(\n" +
            "                value = yCoord,\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                onValueChange = { newText -> yCoord = newText },\n" +
            "                label = { Text(text = \"Координата Y\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "        }\n" +
            "        Spacer(Modifier.height(2.dp))\n" +
            "        Row {\n" +
            "            val context = LocalContext.current\n" +
            "\n" +
            "            Button(\n" +
            "                onClick = {\n" +
            "                    val x = xCoord.toFloat()\n" +
            "                    val y = yCoord.toFloat()\n" +
            "\n" +
            "                    if (x >= 10f){\n" +
            "                        Toast.makeText(context, \"Введите значение Х меньше 10\", Toast.LENGTH_SHORT).show()\n" +
            "                    }\n" +
            "                    else{\n" +
            "                        if( y >= 10f){\n" +
            "                            Toast.makeText(context, \"Введите значение Y меньше 10\", Toast.LENGTH_SHORT).show()\n" +
            "                        }\n" +
            "                        else{\n" +
            "                            if (x <= -10f){\n" +
            "                                Toast.makeText(context, \"Введите значение Х больше -10\", Toast.LENGTH_SHORT).show()\n" +
            "                            }\n" +
            "                            else{\n" +
            "                                if (y <= -10f){\n" +
            "                                    Toast.makeText(context, \"Введите значение Y больше -10\", Toast.LENGTH_SHORT).show()\n" +
            "                                }\n" +
            "                                else{\n" +
            "                                    points = points + listOf(Offset(x, y))\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                    }\n" +
            "                },\n" +
            "                Modifier.weight(0.5f),\n" +
            "                enabled = xCoord.isNotEmpty() && yCoord.isNotEmpty()\n" +
            "            ){\n" +
            "                Text(\"Разместить точку\")\n" +
            "            }\n" +
            "\n" +
            "            Button(\n" +
            "                onClick = {\n" +
            "                    var list = emptyList<Offset>()\n" +
            "\n" +
            "                    for (i in 1..10) {\n" +
            "                        val randomX = Random.nextDouble(-8.0, 9.0)\n" +
            "                        val randomY = Random.nextDouble(-8.0, 9.0)\n" +
            "                        list = list + (Offset(randomX.toFloat(), randomY.toFloat()))\n" +
            "                    }\n" +
            "                    points = list\n" +
            "                },\n" +
            "                Modifier.weight(0.5f)\n" +
            "            ){\n" +
            "                Text( \"Случайные точки\")\n" +
            "            }\n" +
            "        }\n" +
            "        var showCode by remember { mutableStateOf(false) }\n" +
            "        // Кнопка показа кода\n" +
            "        Button(onClick = {\n" +
            "            showCode = !showCode\n" +
            "        }) {\n" +
            "            Text(if(!showCode)\"Показать код\" else \"Убрать код\", fontSize = 13.sp)\n" +
            "        }\n" +
            "        if (showCode){\n" +
            "            Code2()\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "@Composable\n" +
            "fun Graph(points: List<Offset>) {\n" +
            "    val textMeasurer = rememberTextMeasurer()\n" +
            "\n" +
            "    Canvas(\n" +
            "        modifier = Modifier\n" +
            "            .size(400.dp)\n" +
            "    ) {\n" +
            "        val sizec = size.width\n" +
            "\n" +
            "        drawRect(\n" +
            "            color = Color.Black,\n" +
            "            topLeft = Offset(7 * sizec / 20, 7*sizec / 20),\n" +
            "            size = Size((6 * sizec / 20), (6 * sizec / 20))\n" +
            "        )\n" +
            "\n" +
            "        drawCircle(Color.White, radius = 3*sizec/20, center = Offset((7*sizec/20),(7*sizec/20)))\n" +
            "        drawCircle(Color.White, radius = 3*sizec/20, center = Offset((13*sizec/20),(13*sizec/20)))\n" +
            "\n" +
            "        drawCircle(Color.Black, radius = 3*sizec/20, center = Offset((7*sizec/20),(7*sizec/20)), style = Stroke(width = 1.dp.toPx()))\n" +
            "        drawCircle(Color.Black, radius = 3*sizec/20, center = Offset((13*sizec/20),(13*sizec/20)), style = Stroke(width = 1.dp.toPx()))\n" +
            "\n" +
            "        drawRect(\n" +
            "            color = Color.Black,\n" +
            "            topLeft = Offset(7.03f * sizec / 20, 7.03f*sizec / 20),\n" +
            "            size = Size((5.94f * sizec / 20), (5.94f * sizec / 20)),\n" +
            "            style = Stroke(width = 3f, pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f))\n" +
            "\n" +
            "        )\n" +
            "        drawLine(\n" +
            "            start = Offset(7*sizec/20, 7*sizec/20),\n" +
            "            end = Offset(sizec/2, sizec/2),\n" +
            "            color = Color.Black,\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "        drawLine(\n" +
            "            start = Offset(sizec/2, sizec/2),\n" +
            "            end = Offset(13*sizec/20, 13*sizec/20),\n" +
            "            color = Color.Black,\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "\n" +
            "        /// Радиус\n" +
            "        val trianglePathR = Path().apply {\n" +
            "            moveTo(9*sizec/20+10f, 9*sizec/20+10f)\n" +
            "            lineTo(9*sizec/20+5f, 9*sizec/20-14f)\n" +
            "            lineTo(9*sizec/20-14f, 9*sizec/20+5f)\n" +
            "            close()\n" +
            "        }\n" +
            "        drawPath(trianglePathR, color = Color.Black)\n" +
            "        val trianglePathR1 = Path().apply {\n" +
            "            moveTo(11*sizec/20-10f, 11*sizec/20-10f)\n" +
            "            lineTo(11*sizec/20-5f, 11*sizec/20+14f)\n" +
            "            lineTo(11*sizec/20+14f, 11*sizec/20-5f)\n" +
            "            close()\n" +
            "        }\n" +
            "        drawPath(trianglePathR1, color = Color.Black)\n" +
            "        drawText(textMeasurer,\n" +
            "            text = \"R\",\n" +
            "            topLeft = Offset(8*sizec/20,8.5f*sizec/20),\n" +
            "            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)\n" +
            "        )\n" +
            "        drawText(textMeasurer,\n" +
            "            text = \"R\",\n" +
            "            topLeft = Offset(10.5f*sizec/20,11.2f*sizec/20),\n" +
            "            style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)\n" +
            "        )\n" +
            "\n" +
            "        // ось X\n" +
            "        drawLine(\n" +
            "            color = Color.Black,\n" +
            "            start = Offset(0f, sizec / 2),\n" +
            "            end = Offset(sizec, sizec / 2),\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "\n" +
            "        // Рисование меток на оси X\n" +
            "        for (i in -9..9) {\n" +
            "            val x = (i + 10) * sizec / 20\n" +
            "            drawLine(\n" +
            "                color = Color.Black,\n" +
            "                start = Offset(x,  sizec / 2 -10f),\n" +
            "                end = Offset(x + 1f, sizec/2 +10f)\n" +
            "            )\n" +
            "            drawText(textMeasurer,\n" +
            "                i.toString(),\n" +
            "                Offset(x, sizec / 2 + 20),\n" +
            "                TextStyle(fontSize = 5.sp))\n" +
            "        }\n" +
            "        drawText(textMeasurer,\n" +
            "            text = \"X\",\n" +
            "            topLeft = Offset(sizec-23,sizec/2-30),\n" +
            "            style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)\n" +
            "        )\n" +
            "        val trianglePathX = Path().apply {\n" +
            "            moveTo(sizec, sizec/2)\n" +
            "            lineTo(sizec-14, sizec/2-6)\n" +
            "            lineTo(sizec-14, sizec/2+6)\n" +
            "            close()\n" +
            "        }\n" +
            "        drawPath(trianglePathX, color = Color.Black)\n" +
            "\n" +
            "        // ось Y\n" +
            "        drawLine(\n" +
            "            color = Color.Black,\n" +
            "            start = Offset(sizec / 2, 0f),\n" +
            "            end = Offset(sizec / 2, sizec),\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "\n" +
            "        // Рисование меток на оси Y\n" +
            "        for (i in -9..9) {\n" +
            "            val y = (10 - i) * sizec / 20\n" +
            "            drawLine(\n" +
            "                color = Color.Black,\n" +
            "                start = Offset(10 * sizec / 20 - 10f / 2, y),\n" +
            "                end = Offset(10 * sizec / 20 + 10f, y + 1f)\n" +
            "            )\n" +
            "            drawText(textMeasurer,\n" +
            "                i.toString(),\n" +
            "                Offset(sizec / 2 - 20f, y),\n" +
            "                TextStyle(fontSize = 5.sp)\n" +
            "            )\n" +
            "        }\n" +
            "        drawText(textMeasurer,\n" +
            "            text = \"Y\",\n" +
            "            topLeft = Offset(sizec/2-30f,15f),\n" +
            "            style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)\n" +
            "        )\n" +
            "        val trianglePathY = Path().apply {\n" +
            "            moveTo(sizec/2, 0f)\n" +
            "            lineTo(sizec/2-6f,14f)\n" +
            "            lineTo(sizec/2+6f,14f)\n" +
            "            close()\n" +
            "        }\n" +
            "        drawPath(trianglePathY, color = Color.Black)\n" +
            "\n" +
            "        for (i in points) {\n" +
            "            val randomX = i.x\n" +
            "            val randomY = i.y\n" +
            "\n" +
            "\n" +
            "            if ((randomY > -3) && (randomY < 3) && (randomX > -3) && (randomX < 3) && ((randomX - 3) * (randomX - 3) + (randomY + 3) * (randomY + 3) > 9) && ((randomX + 3) * (randomX + 3) + (randomY - 3) * (randomY - 3) > 9)) {\n" +
            "                drawCircle(\n" +
            "                    color = Color.Green,\n" +
            "                    radius = 3f,\n" +
            "                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)\n" +
            "                )\n" +
            "            } else if (((randomX + 3) * (randomX + 3) + (randomY - 3) * (randomY - 3) >= 9f) && (randomY <= 3) && (randomY >= -3) && (randomX >= -3) && (randomX <= 3) && ((randomX - 3) * (randomX - 3) + (randomY + 3) * (randomY + 3) >= 9f)) {\n" +
            "                drawCircle(\n" +
            "                    color = Color.Blue,\n" +
            "                    radius = 3f,\n" +
            "                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)\n" +
            "                )\n" +
            "            } else {\n" +
            "                drawCircle(\n" +
            "                    color = Color.Red,\n" +
            "                    radius = 3f,\n" +
            "                    center = Offset((randomX + 10) * sizec / 20, (10 - randomY) * sizec / 20)\n" +
            "                )\n" +
            "            }\n" +
            "        }\n" +
            "    }\n" +
            "}",

        Modifier
            .border(1.dp, Color.Red)
            .background(Color.Black)
            .horizontalScroll(ScrollState(0)),
        color = Color.Green,
        fontSize = 14.sp
    )
}
