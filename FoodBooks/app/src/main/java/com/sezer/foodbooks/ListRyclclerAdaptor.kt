package com.sezer.foodbooks

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.zip.Inflater


class ListRyclclerAdaptor(val yemekListe: ArrayList<String>,val idList :ArrayList<Int>): RecyclerView.Adapter<ListRyclclerAdaptor.YemekHolder>() {
    class YemekHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YemekHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recyler_ldesign,parent,false)
        return YemekHolder(view)

    }

    override fun getItemCount(): Int {
        return yemekListe.size
    }

    override fun onBindViewHolder(holder: YemekHolder, position: Int) {
       holder.itemView.findViewById<TextView>(R.id.reycle_textView).text = yemekListe[position]
    }
}