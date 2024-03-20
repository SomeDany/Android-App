package com.example.exam.tasks

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlin.math.abs
import kotlin.math.roundToInt
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.sp
import com.example.exam.R
import kotlin.math.pow


@Composable
fun Task3(){
    TaylorSeriesTable()
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaylorSeriesTable() {
    var step by remember { mutableStateOf("0.1") }  // значение шага
    var precision by remember { mutableStateOf("0.001") }  // точность
    var xn by remember { mutableStateOf("-0.9") }  // начальная точка
    var xk by remember { mutableStateOf("0.9") }  // конечная точка
    var showTable by remember { mutableStateOf(false) }
    var showCode by remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .padding(5.dp)
        .verticalScroll(ScrollState(0))) {
        Column(
            Modifier
                .border(1.dp, Color.Black)
                .padding(2.dp)){
            Text(
                text = "Задание 3. Вычислить и вывести на экран в виде таблицы значения функции, заданной с помощью ряда Тейлора," +
                        " на интервале от xнач до xкон с шагом dx с точностью ε.\n" +
                        "Таблицу снабдить заголовком и шапкой. Каждая строка таблицы должна содержать значение аргумента," +
                        " значение функции и количество просуммированных членов ряда.",
                fontSize = 14.sp
            )

            Image(
                bitmap = ImageBitmap.imageResource(R.drawable.z3),
                contentDescription ="ln(x+1)",Modifier.fillMaxSize()
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        /// Поля ввода данных
        Row {
            // Ввод значения шага
            TextField(
                modifier = Modifier.weight(0.5f),
                value = step,
                onValueChange = { step = it },
                label = {Text("Шаг dx") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(5.dp))
            // Ввод значения точности
            TextField(
                modifier = Modifier.weight(0.5f),
                value = precision,
                onValueChange = { precision = it },
                label = {Text("Точность ε") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }

        Spacer(modifier = Modifier.height(5.dp))

        Row {
            TextField(
                modifier = Modifier.weight(0.5f),
                value = xn,
                onValueChange = { xn = it },
                label = {Text("Начальный Х > -1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Spacer(modifier = Modifier.width(5.dp))
            TextField(
                modifier = Modifier.weight(0.5f),
                value = xk,
                onValueChange = { xk = it },
                label = {Text("Конечный Х < 1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
        }
        Spacer(modifier = Modifier.height(5.dp))


        /// Вычисление данных
        Row {
            val context = LocalContext.current
            val ncount = remember { mutableIntStateOf(0) }
            // Кнопка вычисления значений
            Button(onClick = {
                // Очищаем данные
                tableData.clear()
                val epsilon = precision.toDouble()
                val st = step.toDouble()
                val xstart = xn.toDouble()
                val xend = xk.toDouble()
                if (st > 0) {
                    if (-1 < xstart && xstart < 1){
                        if( -1 < xend && xend < 1){
                            if(xstart < xend ){
                                if (epsilon > 0){
                                    // Вычисление значений
                                    var x = xstart
                                    while (x <= xend+ epsilon){
                                        val n = 0
                                        var sum = lnXpl1(x,n,epsilon,ncount)
                                        x = (x * 1000.0).roundToInt()/1000.0
                                        sum = (sum / epsilon).roundToInt()*epsilon
                                        tableData.add(Triple(x, sum, ncount.intValue))
                                        ncount.intValue = 0

                                        x += st

                                    }
                                }

                                else{
                                    Toast.makeText(context, "Значение точности ε должно быть больше 0", Toast.LENGTH_SHORT).show()
                                }
                            }
                            else{
                                Toast.makeText(context, "Значение начального Х должно быть меньше значения конечного Х", Toast.LENGTH_SHORT).show()
                            }
                        }
                        else{
                            Toast.makeText(context, "Значение начального Х должно быть больше -1 и меньше 1", Toast.LENGTH_SHORT).show()
                        }
                    }
                    else{
                        Toast.makeText(context, "Значение начального Х должно быть больше -1 и меньше 1", Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    Toast.makeText(context, "Значение Шага должно быть больше 0", Toast.LENGTH_SHORT).show()
                }
            },
                Modifier.weight(0.3f)) {
                Text("Вычислить", fontSize = 13.sp)
            }

            // Кнопка создания таблицы
            Button(onClick = {
                showTable = !showTable
            },
                Modifier.weight(0.3f)) {
                Text(if(!showTable)"Отобразить таблицу" else "Удалить таблицу", fontSize = 13.sp)
            }

            // Кнопка показа кода
            Button(onClick = {
                showCode = !showCode
            },
                Modifier.weight(0.3f)) {
                Text(if(!showCode)"Показать код" else "Убрать код", fontSize = 13.sp)
            }

        }

        // Таблица со значениями
        if(showTable){
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp)) {
                    // Шапка таблицы
                    Row(modifier = Modifier.background(Color.LightGray)) {
                        TableCell(text = "Функция", weight = 0.32f)
                        TableCell(text = "Значение", weight = 0.33f)
                        TableCell(text = "Члены ряда", weight = 0.35f)
                    }
                for (tableDatum in tableData) {
                    // Элемент таблицы
                    Row(modifier = Modifier.fillMaxWidth()) {
                        TableCell(text = "ln(${tableDatum.first} + 1)", weight = 0.32f)
                        TableCell(text = tableDatum.second.toString(), weight = 0.33f)
                        TableCell(text = tableDatum.third.toString(), weight = 0.35f)
                    }
                }
            }
        }

        /// Отображение кода
        if (showCode){
            Code3()
        }

    }
}


/// Ячейки таблицы
@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float
) {
    Text(
        text = text,
        Modifier
            .border(1.dp, Color.Black)
            .weight(weight)
            .padding(8.dp)
    )
}

val tableData = mutableListOf<Triple<Double, Double, Int>>()

// Вычисление логарифма
fun lnXpl1(x:Double,n:Int,eps:Double,nCount:MutableState<Int>): Double {
    val term = ((-1.0).pow(n = n) * x.pow(n=n+1)) / (n+1)
    if(abs(term)<eps){
        return term
    }
    nCount.value += 1
    return term + lnXpl1(x,n+1,eps,nCount)

}

@Composable
fun Code3(){
    Text(text = "@Composable\n" +
            "fun Task3(){\n" +
            "    TaylorSeriesTable()\n" +
            "}\n" +
            "\n" +
            "\n" +
            "@SuppressLint(\"UnrememberedMutableState\")\n" +
            "@OptIn(ExperimentalMaterial3Api::class)\n" +
            "@Composable\n" +
            "fun TaylorSeriesTable() {\n" +
            "    var step by remember { mutableStateOf(\"0.1\") }  // значение шага\n" +
            "    var precision by remember { mutableStateOf(\"0.001\") }  // точность\n" +
            "    var xn by remember { mutableStateOf(\"-0.9\") }  // начальная точка\n" +
            "    var xk by remember { mutableStateOf(\"0.9\") }  // конечная тока\n" +
            "    var showTable by remember { mutableStateOf(false) }\n" +
            "    var showCode by remember { mutableStateOf(false) }\n" +
            "\n" +
            "    Column(modifier = Modifier\n" +
            "        .padding(5.dp)\n" +
            "        .verticalScroll(ScrollState(0))) {\n" +
            "        Column(\n" +
            "            Modifier\n" +
            "                .border(1.dp, Color.Black)\n" +
            "                .padding(2.dp)){\n" +
            "            Text(\n" +
            "                text = \"Задание 3. Вычислить и вывести на экран в виде таблицы значения функции, заданной с помощью ряда Тейлора,\" +\n" +
            "                        \" на интервале от xнач до xкон с шагом dx с точностью ε.\\n\" +\n" +
            "                        \"Таблицу снабдить заголовком и шапкой. Каждая строка таблицы должна содержать значение аргумента,\" +\n" +
            "                        \" значение функции и количество просуммированных членов ряда.\",\n" +
            "                fontSize = 14.sp\n" +
            "            )\n" +
            "\n" +
            "            Image(\n" +
            "                bitmap = ImageBitmap.imageResource(R.drawable.z3),\n" +
            "                contentDescription =\"ln(x+1)\",Modifier.fillMaxSize()\n" +
            "            )\n" +
            "        }\n" +
            "\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        /// Поля ввода данных\n" +
            "        Row {\n" +
            "            // Ввод значения шага\n" +
            "            TextField(\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                value = step,\n" +
            "                onValueChange = { step = it },\n" +
            "                label = {Text(\"Шаг dx\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "            Spacer(modifier = Modifier.width(5.dp))\n" +
            "            // Ввод значения точности\n" +
            "            TextField(\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                value = precision,\n" +
            "                onValueChange = { precision = it },\n" +
            "                label = {Text(\"Точность ε\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "        }\n" +
            "\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "        Row {\n" +
            "            TextField(\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                value = xn,\n" +
            "                onValueChange = { xn = it },\n" +
            "                label = {Text(\"Начальный Х > -1\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "            Spacer(modifier = Modifier.width(5.dp))\n" +
            "            TextField(\n" +
            "                modifier = Modifier.weight(0.5f),\n" +
            "                value = xk,\n" +
            "                onValueChange = { xk = it },\n" +
            "                label = {Text(\"Конечный Х < 1\") },\n" +
            "                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)\n" +
            "            )\n" +
            "        }\n" +
            "        Spacer(modifier = Modifier.height(5.dp))\n" +
            "\n" +
            "\n" +
            "        /// Вычисление данных\n" +
            "        Row {\n" +
            "            val context = LocalContext.current\n" +
            "            val ncount = remember { mutableIntStateOf(0) }\n" +
            "            // Кнопка вычисления значений\n" +
            "            Button(onClick = {\n" +
            "                // Очищаем данные\n" +
            "                tableData.clear()\n" +
            "                val epsilon = precision.toDouble()\n" +
            "                val st = step.toDouble()\n" +
            "                val xstart = xn.toDouble()\n" +
            "                val xend = xk.toDouble()\n" +
            "                if (st > 0) {\n" +
            "                    if (-1 < xstart && xstart < 1){\n" +
            "                        if( -1 < xend && xend < 1){\n" +
            "                            if(xstart < xend ){\n" +
            "                                if (epsilon > 0){\n" +
            "                                    // Вычисление значений\n" +
            "                                    var x = xstart\n" +
            "                                    while (x <= xend+ epsilon){\n" +
            "                                        val n = 0\n" +
            "                                        var sum = lnXpl1(x,n,epsilon,ncount)\n" +
            "                                        x = (x * 1000.0).roundToInt()/1000.0\n" +
            "                                        sum = (sum / epsilon).roundToInt()*epsilon\n" +
            "                                        tableData.add(Triple(x, sum, ncount.intValue))\n" +
            "                                        ncount.intValue = 0\n" +
            "\n" +
            "                                        x += st\n" +
            "\n" +
            "                                    }\n" +
            "                                }\n" +
            "\n" +
            "                                else{\n" +
            "                                    Toast.makeText(context, \"Значение точности ε должно быть больше 0\", Toast.LENGTH_SHORT).show()\n" +
            "                                }\n" +
            "                            }\n" +
            "                            else{\n" +
            "                                Toast.makeText(context, \"Значение начального Х должно быть меньше значения конечного Х\", Toast.LENGTH_SHORT).show()\n" +
            "                            }\n" +
            "                        }\n" +
            "                        else{\n" +
            "                            Toast.makeText(context, \"Значение начального Х должно быть больше -1 и меньше 1\", Toast.LENGTH_SHORT).show()\n" +
            "                        }\n" +
            "                    }\n" +
            "                    else{\n" +
            "                        Toast.makeText(context, \"Значение начального Х должно быть больше -1 и меньше 1\", Toast.LENGTH_SHORT).show()\n" +
            "                    }\n" +
            "                }\n" +
            "                else{\n" +
            "                    Toast.makeText(context, \"Значение Шага должно быть больше 0\", Toast.LENGTH_SHORT).show()\n" +
            "                }\n" +
            "            },\n" +
            "                Modifier.weight(0.3f)) {\n" +
            "                Text(\"Вычислить\", fontSize = 13.sp)\n" +
            "            }\n" +
            "\n" +
            "            // Кнопка создания таблицы\n" +
            "            Button(onClick = {\n" +
            "                showTable = !showTable\n" +
            "            },\n" +
            "                Modifier.weight(0.3f)) {\n" +
            "                Text(if(!showTable)\"Отобразить таблицу\" else \"Удалить таблицу\", fontSize = 13.sp)\n" +
            "            }\n" +
            "\n" +
            "            // Кнопка показа кода\n" +
            "            Button(onClick = {\n" +
            "                showCode = !showCode\n" +
            "            },\n" +
            "                Modifier.weight(0.3f)) {\n" +
            "                Text(if(!showCode)\"Показать код\" else \"Убрать код\", fontSize = 13.sp)\n" +
            "            }\n" +
            "\n" +
            "        }\n" +
            "\n" +
            "        // Таблица со значениями\n" +
            "        if(showTable){\n" +
            "            Column(\n" +
            "                Modifier\n" +
            "                    .fillMaxSize()\n" +
            "                    .padding(16.dp)) {\n" +
            "                    // Шапка таблицы\n" +
            "                    Row(modifier = Modifier.background(Color.LightGray)) {\n" +
            "                        TableCell(text = \"Функция\", weight = 0.32f)\n" +
            "                        TableCell(text = \"Значение\", weight = 0.33f)\n" +
            "                        TableCell(text = \"Члены ряда\", weight = 0.35f)\n" +
            "                    }\n" +
            "                for (tableDatum in tableData) {\n" +
            "                    // Элемент таблицы\n" +
            "                    Row(modifier = Modifier.fillMaxWidth()) {\n" +
            "                        TableCell(text = \"ln({tableDatum.first} + 1)\", weight = 0.32f)\n" +
            "                        TableCell(text = tableDatum.second.toString(), weight = 0.33f)\n" +
            "                        TableCell(text = tableDatum.third.toString(), weight = 0.35f)\n" +
            "                    }\n" +
            "                }\n" +
            "            }\n" +
            "        }\n" +
            "\n" +
            "        /// Отображение кода\n" +
            "        if (showCode){\n" +
            "            Code3()\n" +
            "        }\n" +
            "\n" +
            "    }\n" +
            "}\n" +
            "\n" +
            "\n" +
            "/// Ячейки таблицы\n" +
            "@Composable\n" +
            "fun RowScope.TableCell(\n" +
            "    text: String,\n" +
            "    weight: Float\n" +
            ") {\n" +
            "    Text(\n" +
            "        text = text,\n" +
            "        Modifier\n" +
            "            .border(1.dp, Color.Black)\n" +
            "            .weight(weight)\n" +
            "            .padding(8.dp)\n" +
            "    )\n" +
            "}\n" +
            "\n" +
            "val tableData = mutableListOf<Triple<Double, Double, Int>>()\n" +
            "\n" +
            "// Вычисление логарифма\n" +
            "fun lnXpl1(x:Double,n:Int,eps:Double,nCount:MutableState<Int>): Double {\n" +
            "    val term = ((-1.0).pow(n = n) * x.pow(n=n+1)) / (n+1)\n" +
            "    if(abs(term)<eps){\n" +
            "        return term\n" +
            "    }\n" +
            "    nCount.value += 1\n" +
            "    return term + lnXpl1(x,n+1,eps,nCount)\n" +
            "\n" +
            "}",
        Modifier
            .border(1.dp, Color.Red)
            .background(Color.Black)
            .horizontalScroll(ScrollState(0)),
        color = Color.Green,
        fontSize = 14.sp
    )
}
