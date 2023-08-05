package com.sezer.bluetoothle
import android.Manifest

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.checkSelfPermission


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Calısti")
        setContent {

            Deneme()

        }
    }

    override fun onStart() {
        super.onStart()
        println("Onstart Calısti")
    }
    
    
    @Composable 
    fun Deneme()
    {
        val CamPermision = Manifest.permission.CAMERA
        var IsCamPermision by remember { mutableStateOf(CheckPermissionFor(CamPermision))  }
       val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission(), onResult ={isGranted ->

           if(isGranted)
            {
                println("Is Garanted : ${isGranted}")
                IsCamPermision =true
            }
        }

        )

        Text(text = "Camera Permisson is : ${IsCamPermision}")
        Button(onClick = {
            if(!IsCamPermision)
            {
                launcher.launch(Manifest.permission.CAMERA)
            }

        }
        ) {
            Text(text = "${IsCamPermision}")
        }
    }

    fun CheckPermissionFor(permission : String)  = checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED


}

