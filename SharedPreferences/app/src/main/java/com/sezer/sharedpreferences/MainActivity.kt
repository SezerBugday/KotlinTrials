package com.sezer.sharedpreferences

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
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
import androidx.compose.ui.text.style.TextDirection.Companion.Content
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.sharedpreferences.ui.theme.SharedPreferencesTheme

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
   
 var KayitEdilenVeri : String? = null
  lateinit var sharedPreferences : SharedPreferences // Lateinit is I just defined it then I will initialize it
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            sharedPreferences = this.getSharedPreferences("com.sezer.sharedpreferences",
              MODE_PRIVATE) // name is changeable. It is name of xml file
            KayitEdilenVeri = sharedPreferences.getString("user_name","there is no data")
            // check user_name is avaliable otherwise return this value "therei s no data"
            SaveAndRead()

        }
    }



    @Composable
    fun SaveAndRead()
    {
        var yazi by remember{ mutableStateOf("") }
        
        Column() {
            TextField(value = yazi, onValueChange ={yazi = it}  , label = { Text(text = "Enter your name...")} )

            Row() {
                Button(onClick = {
                    if (yazi== "")
                    {
                        Toast.makeText(this@MainActivity,"Enter a name", Toast.LENGTH_LONG).show()

                    }
                    else {
                        sharedPreferences.edit().putString("user_name", yazi).apply()

                        Toast.makeText(this@MainActivity,"Data is saved", Toast.LENGTH_LONG).show()
                    }
                }) {
                    Text(text = "Save")
                }

                Button(onClick = {
                    if(KayitEdilenVeri != null)
                    {
                        sharedPreferences.edit().remove("user_name").apply()
                    }
                }) {
                    Text(text ="Delete")
                }
            }
            if(KayitEdilenVeri != null)
            {
                Text(text = KayitEdilenVeri!!)
            }

        }



    }

}

