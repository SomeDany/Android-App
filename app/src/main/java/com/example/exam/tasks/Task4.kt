package com.example.exam.tasks

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.abs
import kotlin.math.pow

@Composable
fun Task4(){
    Column {
        Bisection()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Bisection(){
    var epsilon by remember { mutableStateOf("0.01") }
    var result by remember { mutableStateOf("0.0") }
    var animate by remember { mutableStateOf(false) }
    Column(modifier = Modifier
        .padding(5.dp)
        .verticalScroll(ScrollState(0)))
    {
        Column(
            Modifier
                .border(1.dp, Color.Black)
                .padding(2.dp)){
            Text(
                text = "Задание 4. Дано действительное положительное число ε." +
                        "Методом бисекций найти приближённое значение корня уравнения f (x) = 0." +
                        "Абсолютная погрешность найденного значения не должна превосходить ε.\n" +
                        "\t x^3 - 0.2*x^2 - 0.2*x - 1.2 = 0",
                fontSize = 14.sp,
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        TextField(
            value = epsilon,
            onValueChange = { epsilon = it },
            label = {Text("Значение ε") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(5.dp))

        val xs = 0f
        val xe = 1.6f
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Button(onClick = {
                val e = epsilon.toDouble()
                result = bisection(xs,xe,e).toString()
                animate = !animate
            }) {
                Text("Вычислить", fontSize = 13.sp )
            }
        }

        Text(text = "Приближенное значение корня : $result", fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(5.dp))

        // Отображение графика
        if(animate) {
            val e= epsilon.toDouble()
            Graph(e,xs,xe)
        }


        var showCode by remember { mutableStateOf(false) }

        // Показ кода
        Button(onClick = {
            showCode = !showCode
        }) {
            Text(if(!showCode)"Показать код" else "Убрать код", fontSize = 13.sp)
        }
        if (showCode){
            Code4()
        }
    }
}


@Composable
fun Graph(epsilon:Double,xs:Float,xe:Float) {

    val textMeasurer = rememberTextMeasurer()
    // Переменные для х-координаты линий бисекции
    var a by remember { mutableFloatStateOf(xs) }
    var b by remember { mutableFloatStateOf(xe) }
    var c by remember { mutableFloatStateOf((xs+xe/2)) }

    // Списки для координат x линий бисекции
    val lista = mutableListOf<Float>()
    val listb = mutableListOf<Float>()
    val listc = mutableListOf<Float>()

    // Вычисление координат линий бисекции
    var al = xs
    var bl = xe
    var cl = (al+ bl)/2
    while (abs(bl - al) > epsilon) {

        if (f(al) * f(cl) < 0) {
            bl = cl
            listb.add(bl)
        } else {
            al = cl
            lista.add(al)
        }
        cl = (bl + al) / 2
        listc.add(cl)
    }

    // Переменные анимации
    val animateA by animateFloatAsState(
        targetValue = a,
        animationSpec = tween(durationMillis = 600), label = ""
    )
    val animateB by animateFloatAsState(
        targetValue = b,
        animationSpec = tween(durationMillis = 600), label = ""
    )
    val animateC by animateFloatAsState(
        targetValue = c,
        animationSpec = tween(durationMillis = 500), label = ""
    )


    // Поток данных из списков координат линий (Исправление зацикливания анимации линиц)
    val listaSnapshotFlow = snapshotFlow { lista }
        .collectAsState(initial = emptyList())

    val listbSnapshotFlow = snapshotFlow { listb }
        .collectAsState(initial = emptyList())

    val listcSnapshotFlow = snapshotFlow { listc }
        .collectAsState(initial = emptyList())

    LaunchedEffect(listaSnapshotFlow.value) {
        for (i in listaSnapshotFlow.value.indices) {
            delay(2000)
            a = listaSnapshotFlow.value[i]
        }
    }

    // Анимации при запуске или изменении переменных
    LaunchedEffect(listcSnapshotFlow.value) {
        for (i in listcSnapshotFlow.value.indices) {
            delay(1800)
            c = listcSnapshotFlow.value[i]
        }
    }

    LaunchedEffect(listbSnapshotFlow.value) {
        for (i in listbSnapshotFlow.value.indices) {
            delay(2300)
            b = listbSnapshotFlow.value[i]
        }
    }
    

    Canvas(modifier = Modifier
        .size(400.dp)
    ) {
        val size = this.size.width


        ///// Ось X
        drawLine(
            color = Color.Black,
            start = Offset(0f, size / 2),
            end = Offset(size, size / 2),
            strokeWidth = 1.dp.toPx()
        )

        // Метки на оси X
        for (i in 0..3) {
            val xm = (i+1) * size / 5
            drawLine(
                color = Color.Black,
                start = Offset( xm,size / 2 - 15f),
                end = Offset(xm , size / 2 + 15f),
                strokeWidth = 1.dp.toPx()
            )
            drawText(textMeasurer, i.toString(), Offset(xm, size / 2 + 40))
        }

        ///// Ось Y
        drawLine(
            color = Color.Black,
            start = Offset(size/5, 0f),
            end = Offset(size/5, size),
            strokeWidth = 1.dp.toPx()
        )
        /// Метки на оси Y
        for (i in -3..1) {
            val ym = (i+3) * size / 5
            drawLine(
                color = Color.Black,
                start = Offset( size/5 - 15f,ym+size/10),
                end = Offset(size/5 + 15f , ym+size/10),
                strokeWidth = 1.dp.toPx()
            )
            drawText(textMeasurer, (-i-1).toString(), Offset(size/5 - 30f, ym+size/10))
        }


        //  График
        val points = mutableListOf<Offset>()
        var x = xs

        while (x <= xe) {
            val y = -(x.pow(3) - 0.2f * x.pow(2)-0.2f * x-1.2f)
            points.add(Offset(x*size/5+size/5, y*size/5+size/2))
            x += 0.001f
        }
        drawPoints(
            points = points,
            strokeWidth = 3f,
            pointMode = PointMode.Points,
            color = Color.Black
        )


        // Отрисовка линий бисекции

        drawLine(
            Color.Blue,
            start = Offset((animateA*size/5 + size/5),size / 5),
            end = Offset((animateA*size/5 + size/5), size / 5 + size/2),
            strokeWidth = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)

        )
        drawLine(
            Color.Blue,
            start = Offset((animateB*size/5 + size/5),size / 5),
            end = Offset((animateB*size/5 + size/5), size / 5 + size/2),
            strokeWidth = 1.5.dp.toPx(),
            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
        )
        drawLine(
            Color.Red ,
            start = Offset((animateC*size/5 + size/5),size / 5),
            end = Offset((animateC*size/5 + size/5), size / 5 + size/2),
            strokeWidth = 1.5.dp.toPx(),
        )

    }

}


// Метод бисекции
fun  bisection(xs:Float,xe:Float,epsilon:Double):Float{
    var a = xs
    var b = xe
    var c = (a+ b)/2

    while (abs(b - a) > epsilon) {
        if (f(a) * f(c) < 0) {
            b = c
        } else {
            a = c
        }
        c = (b + a) / 2
    }
    return c
}

// Функция графика
fun f(x:Float): Double {
    return x.pow(3) - 0.2 * x.pow(2) - 0.2 * x -1.2
}


@Composable
fun Code4(){
    Text(text = "@Composable\n" +
            "fun Task4(){\n" +
            "    Column {\n" +
            "        Bisection()\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "@OptIn(ExperimentalMaterial3Api::class)\n" +
            "@Composable\n" +
            "fun Bisection(){\n" +
            "    var epsilon by remember { mutableStateOf(\"0.01\") }\n" +
            "    var result by remember { mutableStateOf(\"0.0\") }\n" +
            "    var animate by remember { mutableStateOf(false) }\n" +
            "    Column(modifier = Modifier\n" +
            "        .padding(5.dp)\n" +
            "        .verticalScroll(ScrollState(0)))\n" +
            "    {\n" +
            "        Column(\n" +
            "            Modifier\n" +
            "                .border(1.dp, Color.Black)\n" +
            "                .padding(2.dp)){\n" +
            "            Text(\n" +
            "                text = \"Задание 4. Дано действительное положительное число ε.\" +\n" +
            "                        \"Методом бисекций найти приближённое значение корня уравнения f (x) = 0.\" +\n" +
            "                        \"Абсолютная погрешность найденного значения не должна превосходить ε.\\n\" +\n" +
            "                        \"\\t x^3 - 0.2*x^2 - 0.2*x - 1.2 = 0\",\n" +
            "                fontSize = 14.sp,\n" +
            "            )\n" +
            "        }\n" +
            "\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        TextField(\n" +
            "            value = epsilon,\n" +
            "            onValueChange = { epsilon = it },\n" +
            "            label = {Text(\"Значение ε\") },\n" +
            "            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "        )\n" +
            "\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        val xs = 0f\n" +
            "        val xe = 1.6f\n" +
            "        Column(horizontalAlignment = Alignment.CenterHorizontally) {\n" +
            "            Button(onClick = {\n" +
            "                val e = epsilon.toDouble()\n" +
            "                result = bisection(xs,xe,e).toString()\n" +
            "                animate = !animate\n" +
            "            }) {\n" +
            "                Text(\"Вычислить\", fontSize = 13.sp )\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        Text(text = \"Приближенное значение корня : result\", fontWeight = FontWeight.Bold)\n" +
            "\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        // Отображение графика\n" +
            "        if(animate) {\n" +
            "            val e= epsilon.toDouble()\n" +
            "            Graph(e,xs,xe)\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        var showCode by remember { mutableStateOf(false) }\n" +
            "\n" +
            "        // Показ кода\n" +
            "        Button(onClick = {\n" +
            "            showCode = !showCode\n" +
            "        }) {\n" +
            "            Text(if(!showCode)\"Показать код\" else \"Убрать код\", fontSize = 13.sp)\n" +
            "        }\n" +
            "        if (showCode){\n" +
            "            Code4()\n" +
            "        }\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "@Composable\n" +
            "fun Graph(epsilon:Double,xs:Float,xe:Float) {\n" +
            "\n" +
            "    val textMeasurer = rememberTextMeasurer()\n" +
            "    var a by remember { mutableFloatStateOf(xs) }\n" +
            "    var b by remember { mutableFloatStateOf(xe) }\n" +
            "    var c by remember { mutableFloatStateOf((xs+xe/2)) }\n" +
            "\n" +
            "    val lista = mutableListOf<Float>()\n" +
            "    val listb = mutableListOf<Float>()\n" +
            "    val listc = mutableListOf<Float>()\n" +
            "    var al = xs\n" +
            "    var bl = xe\n" +
            "    var cl = (al+ bl)/2\n" +
            "    while (abs(bl - al) > epsilon) {\n" +
            "\n" +
            "        if (f(al) * f(cl) < 0) {\n" +
            "            bl = cl\n" +
            "            listb.add(bl)\n" +
            "        } else {\n" +
            "            al = cl\n" +
            "            lista.add(al)\n" +
            "        }\n" +
            "        cl = (bl + al) / 2\n" +
            "        listc.add(cl)\n" +
            "    }\n" +
            "\n" +
            "    val animateA by animateFloatAsState(\n" +
            "        targetValue = a,\n" +
            "        animationSpec = tween(durationMillis = 600), label = \"\"\n" +
            "    )\n" +
            "    val animateB by animateFloatAsState(\n" +
            "        targetValue = b,\n" +
            "        animationSpec = tween(durationMillis = 600), label = \"\"\n" +
            "    )\n" +
            "    val animateC by animateFloatAsState(\n" +
            "        targetValue = c,\n" +
            "        animationSpec = tween(durationMillis = 500), label = \"\"\n" +
            "    )\n" +
            "\n" +
            "\n" +
            "    val listaSnapshotFlow = snapshotFlow { lista }\n" +
            "        .collectAsState(initial = emptyList())\n" +
            "\n" +
            "    val listbSnapshotFlow = snapshotFlow { listb }\n" +
            "        .collectAsState(initial = emptyList())\n" +
            "\n" +
            "    val listcSnapshotFlow = snapshotFlow { listc }\n" +
            "        .collectAsState(initial = emptyList())\n" +
            "\n" +
            "    LaunchedEffect(listaSnapshotFlow.value) {\n" +
            "        for (i in listaSnapshotFlow.value.indices) {\n" +
            "            delay(2000)\n" +
            "            a = listaSnapshotFlow.value[i]\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    LaunchedEffect(listbSnapshotFlow.value) {\n" +
            "        for (i in listbSnapshotFlow.value.indices) {\n" +
            "            delay(2000)\n" +
            "            b = listbSnapshotFlow.value[i]\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "    LaunchedEffect(listcSnapshotFlow.value) {\n" +
            "        for (i in listcSnapshotFlow.value.indices) {\n" +
            "            delay(1800)\n" +
            "            c = listcSnapshotFlow.value[i]\n" +
            "        }\n" +
            "    }\n" +
            "\n" +
            "\n" +
            "\n" +
            "    Canvas(modifier = Modifier\n" +
            "        .size(400.dp)\n" +
            "    ) {\n" +
            "        val size = this.size.width\n" +
            "\n" +
            "\n" +
            "        ///// Ось X\n" +
            "        drawLine(\n" +
            "            color = Color.Black,\n" +
            "            start = Offset(0f, size / 2),\n" +
            "            end = Offset(size, size / 2),\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "\n" +
            "        // Метки на оси X\n" +
            "        for (i in 0..3) {\n" +
            "            val xm = (i+1) * size / 5\n" +
            "            drawLine(\n" +
            "                color = Color.Black,\n" +
            "                start = Offset( xm,size / 2 - 15f),\n" +
            "                end = Offset(xm , size / 2 + 15f),\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "            drawText(textMeasurer, i.toString(), Offset(xm, size / 2 + 40))\n" +
            "        }\n" +
            "\n" +
            "        ///// Ось Y\n" +
            "        drawLine(\n" +
            "            color = Color.Black,\n" +
            "            start = Offset(0f, 0f),\n" +
            "            end = Offset(0f, size),\n" +
            "            strokeWidth = 1.dp.toPx()\n" +
            "        )\n" +
            "        /// Метки на оси Y\n" +
            "        for (i in -3..1) {\n" +
            "            val ym = (i+3) * size / 5\n" +
            "            drawLine(\n" +
            "                color = Color.Black,\n" +
            "                start = Offset( 0f,ym+size/10),\n" +
            "                end = Offset(15f , ym+size/10),\n" +
            "                strokeWidth = 1.dp.toPx()\n" +
            "            )\n" +
            "            drawText(textMeasurer, (-i-1).toString(), Offset(15f, ym+size/10))\n" +
            "        }\n" +
            "\n" +
            "\n" +
            "        //  График\n" +
            "        val points = mutableListOf<Offset>()\n" +
            "        var x = xs\n" +
            "\n" +
            "        while (x <= xe) {\n" +
            "            val y = -(x.pow(3) - 0.2f * x.pow(2)-0.2f * x-1.2f)\n" +
            "            points.add(Offset(x*size/5+size/5, y*size/5+size/2))\n" +
            "            x += 0.001f\n" +
            "        }\n" +
            "        drawPoints(\n" +
            "            points = points,\n" +
            "            strokeWidth = 3f,\n" +
            "            pointMode = PointMode.Points,\n" +
            "            color = Color.Black\n" +
            "        )\n" +
            "\n" +
            "\n" +
            "        // Отрисовка линий бисекции\n" +
            "\n" +
            "        drawLine(\n" +
            "            Color.Blue,\n" +
            "            start = Offset((animateA*size/5 + size/5),size / 5),\n" +
            "            end = Offset((animateA*size/5 + size/5), size / 5 + size/2),\n" +
            "            strokeWidth = 1.dp.toPx(),\n" +
            "            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)\n" +
            "\n" +
            "        )\n" +
            "        drawLine(\n" +
            "            Color.Blue,\n" +
            "            start = Offset((animateB*size/5 + size/5),size / 5),\n" +
            "            end = Offset((animateB*size/5 + size/5), size / 5 + size/2),\n" +
            "            strokeWidth = 1.dp.toPx(),\n" +
            "            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)\n" +
            "        )\n" +
            "        drawLine(\n" +
            "            Color.Red ,\n" +
            "            start = Offset((animateC*size/5 + size/5),size / 5),\n" +
            "            end = Offset((animateC*size/5 + size/5), size / 5 + size/2),\n" +
            "            strokeWidth = 1.dp.toPx(),\n" +
            "        )\n" +
            "\n" +
            "    }\n" +
            "\n" +
            "}\n" +
            "\n" +
            "\n" +
            "// Метод бисекции\n" +
            "fun  bisection(xs:Float,xe:Float,epsilon:Double):Float{\n" +
            "    var a = xs\n" +
            "    var b = xe\n" +
            "    var c = (a+ b)/2\n" +
            "\n" +
            "    while (abs(b - a) > epsilon) {\n" +
            "        if (f(a) * f(c) < 0) {\n" +
            "            b = c\n" +
            "        } else {\n" +
            "            a = c\n" +
            "        }\n" +
            "        c = (b + a) / 2\n" +
            "    }\n" +
            "    return c\n" +
            "}\n" +
            "\n" +
            "// Функция графика\n" +
            "fun f(x:Float): Double {\n" +
            "    return x.pow(3) - 0.2 * x.pow(2) - 0.2 * x -1.2\n" +
            "}\n",

        Modifier
            .border(1.dp, Color.Red)
            .background(Color.Black)
            .horizontalScroll(ScrollState(0)),
        color = Color.Green,
        fontSize = 14.sp
    )
}