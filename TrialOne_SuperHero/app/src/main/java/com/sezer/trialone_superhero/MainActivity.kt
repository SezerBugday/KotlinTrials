@file:OptIn(ExperimentalMaterial3Api::class)

package com.sezer.trialone_superhero

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.trialone_superhero.ui.theme.TrialOne_SuperHeroTheme
import androidx.compose.material3.Text as Text

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                CreatHero()
            }
        }
    }




@Preview(showBackground = true)
@Composable
fun CreatHero()
{
    var isim by remember { mutableStateOf("") }
    var yas by remember { mutableStateOf("")   }
    var meslek by remember { mutableStateOf("") }



    var yazi by remember { mutableStateOf("")   }


    Column() {
        TextField(
            value = isim,
            placeholder = {},
            label  = { Text(text = "Enter Hero's Name")},
            onValueChange = {isim = it},
            )
        TextField(
            value = yas,
            placeholder = {},
            label  = { Text(text = "Enter Hero's Age")},
            onValueChange = {yas = it},
        )
        TextField(
            meslek, {meslek = it},
            placeholder = {},
            label  = { Text(text = "Enter Hero's Job")},
        )


        Button(onClick = {
            val superKah  = SuperHero(isim, yas.toIntOrNull() ?:-1,meslek)
            yazi = superKah.Name + superKah.Age + superKah.Job
        }) {
            Text(text = "Save")
        }


            Text(text = "Hero : ${yazi}")



    }
}




