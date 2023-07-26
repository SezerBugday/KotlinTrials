package com.sezer.runable_hander

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.sezer.runable_hander.ui.theme.Runable_HanderTheme


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposable()
        }
    }


    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MyComposable() {
        var counter by remember { mutableStateOf(0) }
        var IsWork by remember { mutableStateOf(false)        }
        var handler = Handler(Looper.myLooper()!!)
        var runnable = object : Runnable {
            override fun run() {
                if (IsWork) {
                    counter += 1
                    handler.postDelayed(this, 1000)
                }
            }
        }

        DisposableEffect(Unit) {
            onDispose {
                handler.removeCallbacks(runnable)
            }
        }

    Column() {
        Text(text = counter.toString())
        Row() {
            Button(onClick = {
                IsWork = !IsWork
                if (IsWork) {
                    handler.post(runnable)
                } else {
                    handler.removeCallbacks(runnable)
                }
            }) {
                Text(text = "Start And Stop")

            }
            Button(onClick = {
                counter = 0

                handler.removeCallbacks(runnable)
            }) {
                Text(text = "Reset")

            }
        }

    }

    }

}