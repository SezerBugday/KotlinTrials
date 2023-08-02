package com.sezer.foodbooks

import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView.FindListener
import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.EditText
import android.widget.TextView
import androidx.navigation.Navigation
import java.io.ByteArrayOutputStream


class RecipeFragment : Fragment() {
var secilenGorsel : Uri? = null
    var secilenBitMap : Bitmap? = null
    lateinit var Gorsel : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        println("Sistem Olustu")

        val button = view.findViewById<Button>(R.id.button)
        button.setOnClickListener {
            SaveFood(it)
        }

      Gorsel = view.findViewById<ImageView>(R.id.imageView)

        Gorsel.setOnClickListener {
           AddImage(it)
        }
    }

    fun SaveFood(view: View) {

       val yemekAdi = view.findViewById<TextView>(R.id.food_name)
        yemekAdi?.let {
            println("Yemegin adi :"+yemekAdi.text.toString())
        }

        if (secilenBitMap != null)
        {
            println("secilenbit null degil")
            val kucukBitMap = MakeSmallerBitmap(secilenBitMap!!,250)
            val OutputStream = ByteArrayOutputStream()
            kucukBitMap!!.compress(Bitmap.CompressFormat.PNG,50,OutputStream)
            val byteDizisi = OutputStream.toByteArray()

            try {

                context?.let {
                    println("context null degil")
                    val dataBase = it.openOrCreateDatabase("Foods",Context.MODE_PRIVATE,null)
                    dataBase.execSQL("Create Table if not exists  YemekTablo(id Integer Primary Key ," +
                            " YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)")
                    val SqlKomut ="Insert into YemekTablo(YemekIsmi,Malzemeler,GorselVerisi) VALUES (?,?,?)"
                    val statment = dataBase.compileStatement(SqlKomut)

                    //statment.bindString(1,view.findViewById<EditText>(R.id.food_name).text.toString()) // for first question mark in (?,?,?)
                    //statment.bindString(2,view.findViewById<EditText>(R.id.food_recipe).text.toString()) // for second question mark in (?,?,?)

                    statment.bindString(1,"sezer") // for first question mark in (?,?,?)
                    statment.bindString(2,"deneme")

                    statment.bindBlob(3,byteDizisi) // for third question mark in (?,?,?)
                    statment.execute()
                }
                println("Action dan once")
                val action = RecipeFragmentDirections.actionRecipeFragmentToFoodList()
                Navigation.findNavController(view).navigate(action)
                println("Action dan sonra")
            }
            catch (e:Exception)
            {
                println(e.message)
            }
        }
    }
    fun AddImage(view: View)
    {
        println("Clicked to image")
        activity?.let {
            if(ContextCompat.checkSelfPermission(it.applicationContext,Manifest.permission.READ_MEDIA_IMAGES
                )!= PackageManager.PERMISSION_GRANTED )
            {
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES),1)

            }
            else
            {
                val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }


        }



    }

    override fun onRequestPermissionsResult( // izin sonucunda çalışacak fonkisyon
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if(requestCode ==1)
        {
            if(grantResults.size> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                val galeriIntent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                startActivityForResult(galeriIntent,2)
            }
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
       if(requestCode==2 && resultCode == Activity.RESULT_OK && data != null)
       {
           secilenGorsel = data.data
           try {
               context?.let {
                   if(secilenGorsel != null)
                   {
                       if (Build.VERSION.SDK_INT >= 28)
                       {
                         val source =  ImageDecoder.createSource(it.contentResolver,secilenGorsel!!)
                            secilenBitMap = ImageDecoder.decodeBitmap(source)
                            Gorsel!!.setImageBitmap(secilenBitMap)
                       }
                       else
                       {
                           secilenBitMap = MediaStore.Images.Media.getBitmap(it.contentResolver,secilenGorsel)
                           Gorsel!!.setImageBitmap(secilenBitMap)
                       }
                   }
               }


           }
           catch (e:Exception)
           {
               println(e.message)
           }

       }
        super.onActivityResult(requestCode, resultCode, data)
    }
    fun MakeSmallerBitmap(secilenBitMap:Bitmap, maxBoyut :Int): Bitmap? {
        var widht = secilenBitMap.width
        var height = secilenBitMap.height
        var oran :Double = widht.toDouble() / height.toDouble()
        if(oran>1)
        {
            widht =maxBoyut
            var UpdatedSizeHeight = widht / oran
            height = UpdatedSizeHeight.toInt()
        }
        else
        {
            height =maxBoyut
            var UpdatedSizeSize = height * oran
            height = UpdatedSizeSize.toInt()
        }

        return Bitmap.createScaledBitmap(secilenBitMap,widht,height,true)
    }
}