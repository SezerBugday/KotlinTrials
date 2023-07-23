@file:OptIn(ExperimentalMaterial3Api::class)

package com.sezer.basiccalculator

import android.os.Bundle
import android.support.v4.app.INotificationSideChannel
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.basiccalculator.ui.theme.BasicCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CreateCalculator()
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun CreateCalculator() {
        var input_one by remember { mutableStateOf("")    }
        var input_two by remember { mutableStateOf("")    }
        var result by remember { mutableStateOf("") }
        var title by remember { mutableStateOf("")    }

        var a = input_one.toIntOrNull()
        var b = input_two.toIntOrNull()
        var IsActive = true
        if(a==null || b== null)
        {
            Column() {
                title ="Enter a valid number"
                Text(text = title)
            }
            IsActive=false
        }
        else
        {
            IsActive =true
            title = "SEZER'S CALCULATOR"
            Text(text = title)
        }

        Column() {
            Text(text = "")
            TextField(value = input_one, onValueChange ={input_one = it} ,label ={ Text(text = "Enter first number")} )
            TextField(value = input_two, onValueChange ={input_two=it}, label ={ Text(text = "Enter second number")} )
            Row() {
                Button(onClick = {  if(IsActive){
                    result= (a!! + b!!).toString() } }) {
                    Text(text = "+")
                }

                Button(onClick = {  if(IsActive){
                    result= (a!! - b!!).toString() } }) {
                    Text(text = "-")
                }
                Button(onClick = {  if(IsActive){
                    result= (a!! * b!!).toString() } }) {
                    Text(text = "*")
                }
                Button(onClick = {  if(IsActive){
                    result= (a!! / b!!).toString() } }) {
                    Text(text = "/")
                }
            }
            Text(text = result)
        }



    }

}





