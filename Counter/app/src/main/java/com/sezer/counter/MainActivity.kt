package com.sezer.counter

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.counter.ui.theme.CounterTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    var num by remember { mutableStateOf(15) } // Initial value of 15 seconds

    LaunchedEffect(Unit) {  //LauchedEffect provide to run once in composable
        object : CountDownTimer((15000).toLong(), 1000) {

            override fun onTick(p0: Long) {
                num = (p0 / 1000).toInt()
            }

            override fun onFinish() {
                // Timer finished, you can handle any post-timer actions here.
            }
        }.start()
    }

    Counter(num)
}

@Composable
fun Counter(sayi: Int) {
    Text(text = (sayi).toString())
}