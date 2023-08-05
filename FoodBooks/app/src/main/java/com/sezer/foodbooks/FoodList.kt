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