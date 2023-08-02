package com.sezer.foodbooks

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup



class FoodList : Fragment() {


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

        super.onViewCreated(view, savedInstanceState)
    }

fun SqlGetData()
    {
        try {
            context?.let {
                println("context null degil")
                val dataBase = it.openOrCreateDatabase("Foods", Context.MODE_PRIVATE, null)
                dataBase.execSQL("Create Table if not exists  YemekTablo(id Integer Primary Key , YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)")



            }
        }
        catch (e:Exception)
        {
            println(e.message)
        }
    }
}