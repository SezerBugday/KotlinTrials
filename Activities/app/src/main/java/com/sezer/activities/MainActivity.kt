package com.sezer.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sezer.activities.ui.theme.ActivitiesTheme


@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to the First Page!",
            style = TextStyle(fontSize = 24.sp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {navController.navigate("second")  }) {
            Text(text = "Go to Second Page")
        }
    }
}

@Composable
fun SecondScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Congratulations! You are on the Second Page!",
            style = TextStyle(fontSize = 24.sp)
        )
        Button(onClick = { navController.navigate("third")}) {
            Text(text = "Go to third page")
        }
    }
}

@Composable
fun MyComposeApp() {
    val navController = rememberNavController()

    NavHost(navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("second") {
            SecondScreen(navController)
        }
        composable("third") {
            ThirdScreen()
        }
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyComposeApp()
        }
    }
}
@Composable
fun ThirdScreen()
{
    Text(text = "This is third page")
}
