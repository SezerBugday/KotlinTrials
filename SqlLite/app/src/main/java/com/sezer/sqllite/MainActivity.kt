package com.sezer.sqllite

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.sqllite.ui.theme.SqlLiteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            try {
                val veriTabani =this.openOrCreateDatabase("DatabaseOne", Context.MODE_PRIVATE,null)
                veriTabani.execSQL("Create Table if not exists tablom (id Integer Primary Key,name Varchar, fiyat Int )")
                //veriTabani.execSQL("Insert into tablom (name,fiyat) Values ('Kulaklık',50)")

                val cursor = veriTabani.rawQuery("Select * From tablom",null)
                println(cursor.toString())

            }
            catch (e:Exception)
            {
                e.printStackTrace()
            }
        }
    }
}

