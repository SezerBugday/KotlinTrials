package com.sezer.senddatabetweenscreens

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.senddatabetweenscreens.ui.theme.SendDataBetweenScreensTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SendDataBetweenScreensTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AlertFun()
                    Toast.makeText(this, "Sezerrrr", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


    @Composable
    fun AlertFun() {
        var uyariMesaji = AlertDialog.Builder(this@MainActivity)
        uyariMesaji.setTitle("Uyarı mesaji")
        uyariMesaji.setMessage("Bu bir uyarı mesajı mdır ?")
        uyariMesaji.setNegativeButton("Hayır",DialogInterface.OnClickListener {
            dialogInterface, i ->  Toast.makeText(this,"Hayıra bastın",
            Toast.LENGTH_LONG).show()
        })
        uyariMesaji.setPositiveButton("Evet",DialogInterface.OnClickListener {
                dialogInterface, i ->  Toast.makeText(this,"Hayıra bastın",
            Toast.LENGTH_LONG).show()
        })
        uyariMesaji.show()


    }


}