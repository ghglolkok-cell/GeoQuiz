package com.example.geoquiz

import android.os.Bundle
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.widget.Toast
import androidx.compose.ui.text.font.FontWeight
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

                val currentIndex = remember { mutableStateOf(0) }

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

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(onClick = {
                            currentIndex.value = (currentIndex.value + 1) % questions.size
                        }) {
                            Text("True")
                        }

                        Button(onClick = {
                            currentIndex.value = (currentIndex.value + 1) % questions.size
                        }) {
                            Text("False")
                        }
                    }

                    Button(
                        onClick = {
                            currentIndex.value = (currentIndex.value + 1) % questions.size
                        },
                        modifier = Modifier.padding(top = 16.dp)
                    ) {
                        Text("Next Question")
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