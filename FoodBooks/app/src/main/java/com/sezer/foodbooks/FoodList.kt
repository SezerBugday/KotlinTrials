package com.sezer.foodbooks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument


class FoodList : Fragment() {

    var FoodListName = ArrayList<String>()
    var FoodListId = ArrayList<Int>()
    private lateinit var ListeAdapter: ListRyclclerAdaptor
    lateinit var recyclerView : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_food_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        ListeAdapter = ListRyclclerAdaptor( FoodListName,FoodListId)

            recyclerView= view.findViewById(R.id.reycle_textView)

            recyclerView = LinearLayoutManager(context)
        recyclerView.adapter = ListeAdapter
        SqlGetData()
        super.onViewCreated(view, savedInstanceState)
    }

fun SqlGetData()
    {
        try {
            context?.let {
                println("context null degil")
                val dataBase = it.openOrCreateDatabase("Foods", Context.MODE_PRIVATE, null)
                dataBase.execSQL("Create Table if not exists  YemekTablo(id Integer Primary Key , YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)")
                var cursor = dataBase.rawQuery("Select * From YemekTablo",null)
                val yemekIsmiIndex =  cursor.getColumnIndex("YemekIsmi")
                val yemekIDIndex =  cursor.getColumnIndex("id")

                while (cursor.moveToNext())
                {
                    FoodListName.add(cursor.getString(yemekIsmiIndex))
                    FoodListId.add(cursor.getInt(yemekIDIndex))

                }
                ListeAdapter.notifyDataSetChanged()
                cursor.close()

            }
        }
        catch (e:Exception)
        {
            println(e.message)
        }
    }
}