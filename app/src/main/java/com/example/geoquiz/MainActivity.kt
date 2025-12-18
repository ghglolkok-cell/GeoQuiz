package com.example.geoquiz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.geoquiz.ui.theme.GeoQuizTheme
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.example.geoquiz.ui.theme.GeoQuizTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GeoQuizTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GeoQuizApp()
                }
            }
        }
    }
}

@Composable
fun GeoQuizApp() {
    val questions = listOf(
        "Canberra is the capital of Australia.",
        "The Pacific Ocean is larger than the Atlantic Ocean.",
        "The Suez Canal connects the Red Sea and the Indian Ocean.",
        "The source of the Nile River is in Egypt.",
        "The Amazon River is the longest river in the Americas.",
        "Lake Baikal is the world's oldest and deepest freshwater lake."
    )
    val answers = listOf(true, true, false, false, true, true) // правильные ответы

    val currentIndex = remember { mutableStateOf(0) }
    val answered = remember { mutableStateOf(false) } // отслеживаем ответ
    val correctAnswers = remember { mutableStateOf(0) } // счетчик правильных ответов
    val showResult = remember { mutableStateOf(false) } // НОВОЕ: флаг для показа результатов
    val context = LocalContext.current // контекст для Toast

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = questions[currentIndex.value],
            fontSize = 18.sp,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // показываем кнопки ответов только если еще не ответили
        if (!answered.value) {
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = {
                    answered.value = true
                    // проверяем ответ True
                    val correct = answers[currentIndex.value] == true
                    if (correct) {
                        correctAnswers.value++
                        Toast.makeText(context, "Правильно!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Неправильно!", Toast.LENGTH_SHORT).show()
                    }
                    // НОВОЕ: проверяем последний вопрос
                    if (currentIndex.value == questions.size - 1) {
                        showResult.value = true
                    }
                }) {
                    Text("True")
                }

                Button(onClick = {
                    answered.value = true
                    // проверяем ответ False
                    val correct = answers[currentIndex.value] == false
                    if (correct) {
                        correctAnswers.value++
                        Toast.makeText(context, "Правильно!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Неправильно!", Toast.LENGTH_SHORT).show()
                    }
                    // НОВОЕ: проверяем последний вопрос
                    if (currentIndex.value == questions.size - 1) {
                        showResult.value = true
                    }
                }) {
                    Text("False")
                }
            }
        }

        // кнопка Next активна только после ответа
        if (answered.value && currentIndex.value < questions.size - 1) {
            Button(
                onClick = {
                    currentIndex.value++
                    answered.value = false // сбрасываем флаг ответа
                },
                modifier = Modifier.padding(top = 16.dp)
            ) {
                Text("Next Question")
            }
        }
    }
    // Диалог с результатами
    if (showResult.value) {
        val isPerfectScore = correctAnswers.value == questions.size

        Dialog(onDismissRequest = { showResult.value = false }) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Результат теста",
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    if (isPerfectScore) {
                        Text(
                            text = "Тест пройден полностью правильно!",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    } else {
                        Text(
                            text = "Правильных ответов: ${correctAnswers.value} из ${questions.size}",
                            fontSize = 16.sp,
                            modifier = Modifier.padding(bottom = 16.dp)
                        )
                    }

                    if (isPerfectScore) {
                        Button(onClick = { showResult.value = false }) {
                            Text("OK")
                        }
                    } else {
                        Button(onClick = {
                            currentIndex.value = 0
                            correctAnswers.value = 0
                            answered.value = false
                            showResult.value = false
                        }) {
                            Text("Пройти тест заново")
                        }
                    }
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable

fun GeoQuizPreview() {
    GeoQuizTheme {
        GeoQuizApp()
    }
}