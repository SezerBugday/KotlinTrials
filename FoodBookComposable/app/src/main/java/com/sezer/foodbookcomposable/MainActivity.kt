package com.sezer.foodbookcomposable


import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.graphics.fonts.FontFamily
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.*
import androidx.lifecycle.LifecycleEventObserver
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import java.io.ByteArrayOutputStream
import java.util.Collections.list

class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {


            // PickImageFromGallery()
            NavigateScreens()
            // ImagePermission()
        }
    }
    @Composable
    fun FoodList(navController: NavController) {

        var FoodListName = remember { mutableStateListOf<String>() }
        var FoodListId = remember { mutableStateListOf<Int>() }
        val intValue = remember {
            val backStackEntry = navController.currentBackStackEntry
            val defaultValue = 0 // Default value if the parameter is not provided or cannot be converted
            backStackEntry?.arguments?.getString("intValue")?.toIntOrNull() ?: defaultValue
        }
        println("Received parameter: $intValue")

        try {
            val dataBase = openOrCreateDatabase("Foods", Context.MODE_PRIVATE, null)
            dataBase.execSQL(
                "Create Table if not exists  YemekTablo(id Integer Primary Key ," +
                        " YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)"
            )
            val cursor = dataBase.rawQuery("Select * From YemekTablo", null)
            var idColumnIndex = cursor.getColumnIndex("id")
            var IsimColumnIndex = cursor.getColumnIndex("YemekIsmi")

            while (cursor.moveToNext()) {

                if (!FoodListId.contains(cursor.getInt(idColumnIndex))) {
                    println("Is True :"+ cursor.getInt(idColumnIndex).toString())
                    println("Is True :"+FoodListId.contains(cursor.getInt(idColumnIndex)))
                    FoodListName.add(cursor.getString(IsimColumnIndex).toString())
                    FoodListId.add(cursor.getInt(idColumnIndex))

                }
            }

        } catch (e: Exception) {
            println(e)
        }


        Column {
            ToolbarWithMenu(navController)

            Text(text = "Food's List",
                fontSize = 32.sp, // Replace with your desired font size in SP
                lineHeight = 32.sp, // Adjust line height for better readability
                color = Color.Red )//
            LazyColumn(modifier =Modifier
                ) {

                this.items(FoodListName) { it ->
                    Row {
                        Text(text = it,
                            modifier = Modifier

                                .clickable { navController.navigate("DisplayFoodDetails/${it}") },


                            fontSize = 28.sp, // Replace with your desired font size in SP
                            lineHeight = 32.sp, // Adjust line height for better readability
                            color = Color.Black //

                        )
                    }



                }


            }
            /*
            Button(onClick = { FoodListId.clear()
                FoodListName.clear()
            }) {
                Text(text = "Delete all foods")

            }
            */

        }
    }
    @Composable
    fun NavigateScreens() {

        var navCollector = rememberNavController()
        NavHost(navController = navCollector, startDestination = "first/{intValue}")
        {
            composable("first/{intValue}") {
                    backStackEntry ->
                FoodList(navCollector)
            }

            composable("AddFood") {
                AddFood(navCollector)
            }

            composable("izinAl") {
                ImagePermission(navCollector)
            }
            composable("DisplayFoodDetails/{WhichFood}")
            {
                DisplayFoodDetails(navCollector)
            }


        }
    }

    private @Composable
    fun DisplayFoodDetails(navController: NavController) {
        val which_food = remember {
            val backStackEntry = navController.currentBackStackEntry
            val defaultValue = 0 // Default value if the parameter is not provided or cannot be converted
            backStackEntry?.arguments?.getString("WhichFood")?: defaultValue
        }
        println("secilen yemek ${which_food}")

        var  secilenYemek =""
        var secilenYemekTarif =""
        lateinit var bitmap : Bitmap

       // var bitmapSelected = remember { mutableStateOf<Bitmap?>(null) }
        try {
            val dataBase = openOrCreateDatabase("Foods", Context.MODE_PRIVATE, null)
            dataBase.execSQL(
                "Create Table if not exists  YemekTablo(id Integer Primary Key ," +
                        " YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)"
            )
            val cursor = dataBase.rawQuery("Select * From YemekTablo Where YemekIsmi = '${which_food}' ", null)
            var RecipeColumnIndex = cursor.getColumnIndex("Malzemeler")
            var IsimColumnIndex = cursor.getColumnIndex("YemekIsmi")
            var ImageColumnIndex = cursor.getColumnIndex("GorselVerisi")

            while (cursor.moveToNext()) {

                secilenYemek =cursor.getString(IsimColumnIndex).toString()
                secilenYemekTarif =cursor.getString(RecipeColumnIndex).toString()

                val ByteDizisi = cursor.getBlob(ImageColumnIndex)

                 bitmap = BitmapFactory.decodeByteArray(ByteDizisi, 0, ByteDizisi.size)



            }

        } catch (e: Exception) {
            println(e)
        }
        Column {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                modifier = Modifier
                    .size(350.dp)
                    .padding(20.dp)

            )


            Text(text ="Food's name : ${secilenYemek} " ,
                    fontSize =  32.sp, // Replace with your desired font size in SP
                lineHeight = 32.sp, // Adjust line height for better readability
                color = Color.Blue //
            )
            Text(text ="Food's Recipe : ${secilenYemekTarif} " ,
                fontSize =  28.sp, // Replace with your desired font size in SP
                lineHeight = 32.sp, // Adjust line height for better readability
                color = Color.DarkGray //
            )

        }





    }

    @OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun AddFood(navController: NavController) {

        var imageUri by remember { mutableStateOf<Uri?>(null) }
        val context = LocalContext.current
        val bitmap = remember { mutableStateOf<Bitmap?>(null) }
        var input_one by remember { mutableStateOf("")    }
        var input_two by remember { mutableStateOf("")    }
        val launcher =
            rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
                imageUri = uri
            }


        Column(

            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally


        ) {

            imageUri?.let {
                if (Build.VERSION.SDK_INT < 28) {
                    bitmap.value = MediaStore.Images
                        .Media.getBitmap(context.contentResolver, it)
                } else {
                    val source = ImageDecoder.createSource(context.contentResolver, it)
                    bitmap.value = ImageDecoder.decodeBitmap(source)
                }





            }

                bitmap.value?.let { btm ->
                    val kucukBitMap = MakeSmallerBitmap(bitmap.value!!,250)
                    val OutputStream = ByteArrayOutputStream()
                    kucukBitMap!!.compress(Bitmap.CompressFormat.PNG,50,OutputStream)
                    val byteDizisi = OutputStream.toByteArray()

                    // bitmap'i sayısal veriye donustur
                    Image(
                        bitmap = btm.asImageBitmap(),
                        contentDescription = null,
                        modifier = Modifier
                            .size(400.dp)
                            .padding(20.dp)
                            .clickable {
                                launcher.launch("image/*")

                            }
                    )
                    TextField(value = input_one, onValueChange ={input_one = it} ,label ={ Text(text = "Enter first number")} )
                    TextField(value = input_two, onValueChange ={input_two = it} ,label ={ Text(text = "Enter first number")} )
                    Button(onClick = {
                        if(input_one != "" && input_two != "")
                        {
                            SaveFoodSql(input_one,input_two,byteDizisi)
                            navController.navigate("first/54")

                        }
                        else{

                            Toast.makeText(baseContext,"Lutfen gerekli yerleri doldurun",Toast.LENGTH_LONG).show()
                        }
                                }) {
                        Text(text = "Save Food")
                    }
                }

        if(bitmap.value==null)
        {
            println(" Bitmaap value is null")
            Image(


                painterResource(id = R.drawable.download),
                contentDescription = null,
                modifier = Modifier
                    .size(400.dp)
                    .padding(20.dp)
                    .clickable {
                        launcher.launch("image/*")

                    }
            )
            TextField(value = input_one, onValueChange ={input_one = it} ,label ={ Text(text = "Enter first number")} )
            TextField(value = input_two, onValueChange ={input_two = it} ,label ={ Text(text = "Enter first number")} )
        }



        }




            Spacer(modifier = Modifier.height(12.dp))



    }


    @SuppressLint("SuspiciousIndentation")
    fun SaveFoodSql(isim: String, tarif: String, byteDizisi: ByteArray) {
        println("context null degil")
        val dataBase = openOrCreateDatabase("Foods",Context.MODE_PRIVATE,null)
        dataBase.execSQL("Create Table if not exists  YemekTablo(id Integer Primary Key ," +
                " YemekIsmi Varchar,Malzemeler Varchar,GorselVerisi Blob)")
        val SqlKomut ="Insert into YemekTablo(YemekIsmi,Malzemeler,GorselVerisi) VALUES (?,?,?)"
        val statment = dataBase.compileStatement(SqlKomut)


            try {
                statment.bindString(1,isim) // for first question mark in (?,?,?)
                statment.bindString(2,tarif)
                statment.bindBlob(3,byteDizisi) // for third question mark in (?,?,?)
                statment.execute()
               println("KAydedilen byte dizisi${byteDizisi}")
                Toast.makeText(baseContext,"Veritabanına kayıt edildi",Toast.LENGTH_LONG).show()
            }
            catch (e:Exception)
            {
                Toast.makeText(baseContext,"Bir sorun olustu",Toast.LENGTH_LONG).show()
                println(e)
            }




    }


    /*  fun UploadImage() {
    println("Clicked to image")


    if (ContextCompat.checkSelfPermission(
            applicationContext, Manifest.permission.READ_MEDIA_IMAGES
        ) != PackageManager.PERMISSION_GRANTED
    ) {
        requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)


    } else {
        val galeriIntent =
            Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galeriIntent, 2)

    }


}
*/

        @OptIn(ExperimentalMaterial3Api::class)
        @Composable
        fun ToolbarWithMenu(navController: NavController) {

            var mDisplayMenu by remember { mutableStateOf(false) }




            // Creating a Top bar
            TopAppBar(
                title = {
                    Text("Food Book SQLLite by Sezer")

                },

                actions = {
                    IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "More",
                        )
                    }
                    DropdownMenu(
                        expanded = mDisplayMenu,
                        onDismissRequest = { mDisplayMenu = false }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text("Add Food")
                            },
                            onClick = { navController.navigate("izinAl") },
                        )
                        /*
                       DropdownMenuItem(
                    text = {
                        Text("Refresh")
                    },
                    onClick = { /* TODO */ },
                )
                 */


                    }
                }
            )
        }







    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun ImagePermission(navController: NavController) {



        var yazi by remember {
            mutableStateOf("")
        }

        val permissionState =
            rememberPermissionState(permission = Manifest.permission.CAMERA)
        val LifecycleOwner = LocalLifecycleOwner.current
        DisposableEffect(key1 = LifecycleOwner)
        {
            val observer = LifecycleEventObserver { source, event ->
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        permissionState.launchPermissionRequest()
                    }

                    else -> {

                    }
                }

            }

            LifecycleOwner.lifecycle.addObserver(observer)
            onDispose {
                LifecycleOwner.lifecycle.removeObserver(observer)
            }

        }
        Column(
            modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            if (permissionState.status.isGranted) {
                yazi = "Recording Permission Granted"
                AddFood(navController = navController)

            } else if ((permissionState.status.shouldShowRationale)) {

                yazi = "Permission Required for Recording Audio"

            } else {
                yazi = "Please Provide the recording permission"


            }
            Text(text = yazi)

        }

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











