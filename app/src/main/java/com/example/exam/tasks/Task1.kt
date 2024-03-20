package com.example.exam.tasks

import android.widget.Toast
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.exam.R
import kotlin.math.pow
import kotlin.math.sqrt


@Composable
fun Task1(){
    Column(Modifier.verticalScroll(ScrollState(0))) {
        ReceiveY()
        Spacer(modifier = Modifier.height(20.dp))

    }
    
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReceiveY(){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)) {
            Column(
                Modifier
                    .border(1.dp, Color.Black)
                    .padding(2.dp)
            ) {
                Text(
                    "Задача 1. \n Построить график функции \n"
                )
                Image(
                    bitmap = ImageBitmap.imageResource(R.drawable.z1),
                    contentDescription = "Graph",
                    Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(5.dp))
            var x by remember { mutableStateOf(TextFieldValue()) }
            var result by remember { mutableStateOf("") }
            var showCode by remember { mutableStateOf(false) }
            TextField(
                value = x,
                onValueChange = { x = it },
                label = {Text("Значение X") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )


            val context = LocalContext.current
            Row {
                Button(
                    onClick = {
                        val xz = x.text.toDoubleOrNull()
                        if (xz != null){
                            if (xz<-10.0 || xz>6.0){
                                result = "В данной точке нет значений Y"
                            }
                            else {
                                var y: Double
                                if (xz >= -10 && xz <= 0) {
                                    y = -0.5 * xz - 3
                                    result = y.toString()
                                }
                                if (xz > 0 && xz < 3) {
                                    y = -sqrt(9 - xz.pow(2))
                                    result = y.toString()
                                }
                                if (xz in 3.0..6.0) {
                                    y = sqrt(9 - (xz - 6).pow(2))
                                    result = y.toString()
                                }
                            }
                        }
                        else{
                            Toast.makeText(context, "Значение Х должно быть разделено точкой", Toast.LENGTH_SHORT).show()
                        }

                    },
                    enabled = x.text.isNotEmpty()
                ) {
                    Text("Вычислить Y", fontSize = 13.sp)
                }

                // Кнопка показа кода
                Button(onClick = {
                    showCode = !showCode
                }) {
                    Text(if(!showCode)"Показать код" else "Убрать код", fontSize = 13.sp)
                }
            }

            Text(text = "Значение y = $result", fontSize = 20.sp, fontWeight = FontWeight.Bold)
            CoordinatePlane()

            if (showCode){
                Code1()
            }
        }
}


@Composable
fun CoordinatePlane() {

        val textMeasurer = rememberTextMeasurer()
        Canvas(
            modifier = Modifier.size(400.dp)

        ) {
            val sizec = size.width

            // ось Х
            drawLine(
                start = Offset(0f, sizec/2),
                end = Offset(sizec, sizec/2),
                color = Color.Black,
                strokeWidth = 1.dp.toPx()
            )
            // ось Y
            drawLine(
                start = Offset(11*sizec/20, sizec/20*5),
                end = Offset(11*sizec/20, sizec-sizec/20*5),
                color = Color.Black,
                strokeWidth = 1.dp.toPx()
            )

            /// Радиусы
            drawLine(
                start = Offset(11*sizec/20, sizec/2),
                end = Offset(13*sizec/20, sizec/2+2.5f*sizec/20-15f),
                color = Color.Black,
                strokeWidth = 1.dp.toPx()
            )
            drawLine(
                start = Offset(17*sizec/20, sizec/2),
                end = Offset(15f*sizec/20, 7.8f*sizec/20),
                color = Color.Black,
                strokeWidth = 1.dp.toPx()
            )
            drawText(textMeasurer,
                text = "R",
                topLeft = Offset(16f*sizec/20,8f*sizec/20),
                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)
            )
            val trianglePathR1 = Path().apply {
                moveTo(11*sizec/20, sizec/2)
                lineTo(11.18f*sizec/20, 10.53f*sizec/20)
                lineTo(11.5f*sizec/20, 10.25f*sizec/20)
                close()
            }
            drawPath(trianglePathR1, color = Color.Black)
            val trianglePathR2 = Path().apply {
                moveTo(15*sizec/20, 7.8f*sizec/20)
                lineTo(15.2f*sizec/20, 8.3f*sizec/20)
                lineTo(15.45f*sizec/20, 8.05f*sizec/20)
                close()
            }
            drawPath(trianglePathR2, color = Color.Black)

            // Отметки на оси X
            for (i in 1..19) {
                val x = i * sizec/20
                drawLine(
                    start = Offset(x, sizec/2-10),
                    end = Offset(x, sizec/2 + 10),
                    color = Color.Black
                )

                drawText(textMeasurer,
                    text = (i-11).toString(),
                    topLeft = Offset(x-5,sizec/2+10),
                    style = TextStyle(fontSize = 5.sp)
                )

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

            // Отметки на оси Y
            for (i in 6..14) {
                val y = i * sizec/20
                drawLine(
                    start = Offset(11*sizec/20 - 10f, y),
                    end = Offset(11*sizec/20 + 10f, y),
                    color = Color.Black
                )

                drawText(textMeasurer,
                    text = (10-i).toString(),
                    topLeft = Offset(11*sizec/20 - 25f,y-12f),
                    style = TextStyle(fontSize = 5.sp)
                )
            }

            val trianglePathY = Path().apply {
                moveTo(11*sizec/20, sizec/20*5)
                lineTo(11*sizec/20-6f, sizec/20*5+14f)
                lineTo(11*sizec/20+6f, sizec/20*5+14f)
                close()
            }
            drawPath(trianglePathY, color = Color.Black)

            drawText(textMeasurer,
                text = "Y",
                topLeft = Offset(sizec/2+20f,sizec/20*5),
                style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)
            )

            /// Рисовка графиков


            val points1 = mutableListOf<Offset>()
            val points2 = mutableListOf<Offset>()
            val points3 = mutableListOf<Offset>()

            var x1 = 3f
            while(x1<=6){
                val y1 = -sqrt(9-(x1-6).pow(2))
                points1.add(Offset(x1*sizec/20+11*sizec/20,y1*sizec/20 + sizec/2))
                x1 += 0.001f
            }
            var x2 = 0f
            while(x2<=3){
                val y2 = sqrt(9- (x2).pow(2))
                points2.add(Offset(x2*sizec/20+11*sizec/20,y2*sizec/20 + sizec/2))
                x2 += 0.001f
            }
            var x3 = -10f
            while(x3<=0){
                val y3 = 3+x3/2
                points3.add(Offset(x3*sizec/20+11*sizec/20,y3*sizec/20+sizec/2))
                x3+=0.01f
            }

            drawPoints(
                points = points1,
                strokeWidth = 3f,
                pointMode = PointMode.Points,
                color = Color.Black
            )

            drawPoints(
                points = points2,
                strokeWidth = 3f,
                pointMode = PointMode.Points,
                color = Color.Black
            )
            drawPoints(
                points = points3,
                strokeWidth = 3f,
                pointMode = PointMode.Points,
                color = Color.Black
            )

        }
}

@Composable
fun Code1(){
    Text(text = "@OptIn(ExperimentalMaterial3Api::class)\n" +
            "@Composable\n" +
            "fun ReceiveY(){\n" +
            "        Column(modifier = Modifier\n" +
            "            .fillMaxSize()\n" +
            "            .padding(10.dp)) {\n" +
            "            Column(\n" +
            "                Modifier\n" +
            "                    .border(1.dp, Color.Black)\n" +
            "                    .padding(2.dp)\n" +
            "            ) {\n" +
            "                Text(\n" +
            "                    \"Задача 1. \\n Построить график функции \\n\"\n" +
            "                )\n" +
            "                Image(\n" +
            "                    bitmap = ImageBitmap.imageResource(R.drawable.z1),\n" +
            "                    contentDescription = \"Graph\",\n" +
            "                    Modifier.fillMaxSize()\n" +
            "                )\n" +
            "            }\n" +
            "\n" +
            "            Spacer(modifier = Modifier.height(5.dp))\n" +
            "            var x by remember { mutableStateOf(TextFieldValue()) }\n" +
            "            var result by remember { mutableStateOf(\"\") }\n" +
            "            var showCode by remember { mutableStateOf(false) }\n" +
            "            TextField(\n" +
            "                value = x,\n" +
            "                onValueChange = { x = it },\n" +
            "                label = {Text(\"Значение X\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "\n" +
            "\n" +
            "            val context = LocalContext.current\n" +
            "            Row {\n" +
            "                Button(\n" +
            "                    onClick = {\n" +
            "                        val xz = x.text.toDoubleOrNull()\n" +
            "                        if (xz != null){\n" +
            "                            if (xz<-10.0 || xz>6.0){\n" +
            "                                result = \"В данной точке нет значений Y\"\n" +
            "                            }\n" +
            "                            else {\n" +
            "                                var y: Double\n" +
            "                                if (xz >= -10 && xz <= 0) {\n" +
            "                                    y = -0.5 * xz - 3\n" +
            "                                    result = y.toString()\n" +
            "                                }\n" +
            "                                if (xz > 0 && xz < 3) {\n" +
            "                                    y = -sqrt(9 - xz.pow(2))\n" +
            "                                    result = y.toString()\n" +
            "                                }\n" +
            "                                if (xz in 3.0..6.0) {\n" +
            "                                    y = sqrt(9 - (xz - 6).pow(2))\n" +
            "                                    result = y.toString()\n" +
            "                                }\n" +
            "                            }\n" +
            "                        }\n" +
            "                        else{\n" +
            "                            Toast.makeText(context, \"Значение Х должно быть разделено точкой\", Toast.LENGTH_SHORT).show()\n" +
            "                        }\n" +
            "\n" +
            "                    },\n" +
            "                    enabled = x.text.isNotEmpty()\n" +
            "                ) {\n" +
            "                    Text(\"Вычислить Y\", fontSize = 13.sp)\n" +
            "                }\n" +
            "\n" +
            "                // Кнопка показа кода\n" +
            "                Button(onClick = {\n" +
            "                    showCode = !showCode\n" +
            "                }) {\n" +
            "                    Text(if(!showCode)\"Показать код\" else \"Убрать код\", fontSize = 13.sp)\n" +
            "                }\n" +
            "            }\n" +
            "\n" +
            "            Text(text = \"Значение y = result\", fontSize = 20.sp, fontWeight = FontWeight.Bold)\n" +
            "            CoordinatePlane()\n" +
            "\n" +
            "            if (showCode){\n" +
            "                Code1()\n" +
            "            }\n" +
            "        }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "@Composable\n" +
            "fun CoordinatePlane() {\n" +
            "\n" +
            "        val textMeasurer = rememberTextMeasurer()\n" +
            "        Canvas(\n" +
            "            modifier = Modifier.size(400.dp)\n" +
            "\n" +
            "        ) {\n" +
            "            val sizec = size.width\n" +
            "\n" +
            "            // ось Х\n" +
            "            drawLine(\n" +
            "                start = Offset(0f, sizec/2),\n" +
            "                end = Offset(sizec, sizec/2),\n" +
            "                color = Color.Black,\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "            // ось Y\n" +
            "            drawLine(\n" +
            "                start = Offset(11*sizec/20, sizec/20*5),\n" +
            "                end = Offset(11*sizec/20, sizec-sizec/20*5),\n" +
            "                color = Color.Black,\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "\n" +
            "            /// Радиусы\n" +
            "            drawLine(\n" +
            "                start = Offset(11*sizec/20, sizec/2),\n" +
            "                end = Offset(13*sizec/20, sizec/2+2.5f*sizec/20-15f),\n" +
            "                color = Color.Black,\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "            drawLine(\n" +
            "                start = Offset(17*sizec/20, sizec/2),\n" +
            "                end = Offset(15f*sizec/20, 7.8f*sizec/20),\n" +
            "                color = Color.Black,\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "            drawText(textMeasurer,\n" +
            "                text = \"R\",\n" +
            "                topLeft = Offset(16f*sizec/20,8f*sizec/20),\n" +
            "                style = TextStyle(fontSize = 14.sp, fontWeight = FontWeight.Bold)\n" +
            "            )\n" +
            "            val trianglePathR1 = Path().apply {\n" +
            "                moveTo(11*sizec/20, sizec/2)\n" +
            "                lineTo(11.18f*sizec/20, 10.53f*sizec/20)\n" +
            "                lineTo(11.5f*sizec/20, 10.25f*sizec/20)\n" +
            "                close()\n" +
            "            }\n" +
            "            drawPath(trianglePathR1, color = Color.Black)\n" +
            "            val trianglePathR2 = Path().apply {\n" +
            "                moveTo(15*sizec/20, 7.8f*sizec/20)\n" +
            "                lineTo(15.2f*sizec/20, 8.3f*sizec/20)\n" +
            "                lineTo(15.45f*sizec/20, 8.05f*sizec/20)\n" +
            "                close()\n" +
            "            }\n" +
            "            drawPath(trianglePathR2, color = Color.Black)\n" +
            "\n" +
            "            // Отметки на оси X\n" +
            "            for (i in 1..19) {\n" +
            "                val x = i * sizec/20\n" +
            "                drawLine(\n" +
            "                    start = Offset(x, sizec/2-10),\n" +
            "                    end = Offset(x, sizec/2 + 10),\n" +
            "                    color = Color.Black\n" +
            "                )\n" +
            "\n" +
            "                drawText(textMeasurer,\n" +
            "                    text = (i-11).toString(),\n" +
            "                    topLeft = Offset(x-5,sizec/2+10),\n" +
            "                    style = TextStyle(fontSize = 5.sp)\n" +
            "                )\n" +
            "\n" +
            "            }\n" +
            "            drawText(textMeasurer,\n" +
            "                text = \"X\",\n" +
            "                topLeft = Offset(sizec-23,sizec/2-30),\n" +
            "                style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)\n" +
            "            )\n" +
            "\n" +
            "            val trianglePathX = Path().apply {\n" +
            "                moveTo(sizec, sizec/2)\n" +
            "                lineTo(sizec-14, sizec/2-6)\n" +
            "                lineTo(sizec-14, sizec/2+6)\n" +
            "                close()\n" +
            "            }\n" +
            "            drawPath(trianglePathX, color = Color.Black)\n" +
            "\n" +
            "            // Отметки на оси Y\n" +
            "            for (i in 6..14) {\n" +
            "                val y = i * sizec/20\n" +
            "                drawLine(\n" +
            "                    start = Offset(11*sizec/20 - 10f, y),\n" +
            "                    end = Offset(11*sizec/20 + 10f, y),\n" +
            "                    color = Color.Black\n" +
            "                )\n" +
            "\n" +
            "                drawText(textMeasurer,\n" +
            "                    text = (10-i).toString(),\n" +
            "                    topLeft = Offset(11*sizec/20 - 25f,y-12f),\n" +
            "                    style = TextStyle(fontSize = 5.sp)\n" +
            "                )\n" +
            "            }\n" +
            "\n" +
            "            val trianglePathY = Path().apply {\n" +
            "                moveTo(11*sizec/20, sizec/20*5)\n" +
            "                lineTo(11*sizec/20-6f, sizec/20*5+14f)\n" +
            "                lineTo(11*sizec/20+6f, sizec/20*5+14f)\n" +
            "                close()\n" +
            "            }\n" +
            "            drawPath(trianglePathY, color = Color.Black)\n" +
            "\n" +
            "            drawText(textMeasurer,\n" +
            "                text = \"Y\",\n" +
            "                topLeft = Offset(sizec/2+20f,sizec/20*5),\n" +
            "                style = TextStyle(fontSize = 7.sp, fontWeight = FontWeight.Bold)\n" +
            "            )\n" +
            "\n" +
            "            /// Рисовка графиков\n" +
            "\n" +
            "\n" +
            "            val points1 = mutableListOf<Offset>()\n" +
            "            val points2 = mutableListOf<Offset>()\n" +
            "            val points3 = mutableListOf<Offset>()\n" +
            "\n" +
            "            var x1 = 3f\n" +
            "            while(x1<=6){\n" +
            "                val y1 = -sqrt(9-(x1-6).pow(2))\n" +
            "                points1.add(Offset(x1*sizec/20+11*sizec/20,y1*sizec/20 + sizec/2))\n" +
            "                x1 += 0.001f\n" +
            "            }\n" +
            "            var x2 = 0f\n" +
            "            while(x2<=3){\n" +
            "                val y2 = sqrt(9- (x2).pow(2))\n" +
            "                points2.add(Offset(x2*sizec/20+11*sizec/20,y2*sizec/20 + sizec/2))\n" +
            "                x2 += 0.001f\n" +
            "            }\n" +
            "            var x3 = -10f\n" +
            "            while(x3<=0){\n" +
            "                val y3 = 3+x3/2\n" +
            "                points3.add(Offset(x3*sizec/20+11*sizec/20,y3*sizec/20+sizec/2))\n" +
            "                x3+=0.01f\n" +
            "            }\n" +
            "\n" +
            "            drawPoints(\n" +
            "                points = points1,\n" +
            "                strokeWidth = 3f,\n" +
            "                pointMode = PointMode.Points,\n" +
            "                color = Color.Black\n" +
            "            )\n" +
            "\n" +
            "            drawPoints(\n" +
            "                points = points2,\n" +
            "                strokeWidth = 3f,\n" +
            "                pointMode = PointMode.Points,\n" +
            "                color = Color.Black\n" +
            "            )\n" +
            "            drawPoints(\n" +
            "                points = points3,\n" +
            "                strokeWidth = 3f,\n" +
            "                pointMode = PointMode.Points,\n" +
            "                color = Color.Black\n" +
            "            )\n" +
            "\n" +
            "        }\n" +
            "}",
        Modifier
            .border(1.dp, Color.Red)
            .background(Color.Black)
            .horizontalScroll(ScrollState(0)),
        color = Color.Green,
        fontSize = 14.sp
    )
}
