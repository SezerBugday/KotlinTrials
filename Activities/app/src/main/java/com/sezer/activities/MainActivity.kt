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





class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Calısti")
        setContent {

            ControlScreens()

        }
    }

    override fun onStart() {
        super.onStart()
        println("Onstart Calısti")
    }

    override fun onResume() {
        super.onResume()
        println("OnResume Calısti")
    }

    override fun onPause() {
        super.onPause()
        println("OnPause Calısti")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("OnDestroy Calısti")
    }


}
@Composable
fun ControlScreens()
{
    val navController = rememberNavController()


    NavHost(  navController,  "value1" )
    {
        composable("value1"){
            Homepage(navController)
        }
        composable("value2"){
            SecondPage(navController)
        }
        composable("value3"){
          ThirdPage  ( navController )
        }
    }
}
@Composable
fun Homepage(navController: NavController)
{
    Column() {
        Text(text = "Page 1")
        Button(onClick = { navController.navigate("value2")})
        {
            Text(text = "Go to second page")
        }

    }

}

@Composable
fun SecondPage(navController: NavController)
{
    Column() {
        Text(text = "Page 2")
        Button(onClick = { navController.navigate("value3")}) {
            Text(text = "Go to third page")
        }

    }
}
@Composable
fun ThirdPage(navController: NavController)
{
    Column() {
        Text(text = "Page 3")
        Button(onClick = { navController.navigate("value1")}) {
            Text(text = "Go to first page")
        }

    }
}