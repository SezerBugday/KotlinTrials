package com.sezer.foodbookcomposable


import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import androidx.activity.ComponentActivity
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.core.content.PermissionChecker
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Lifecycle.*
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class MainActivity : ComponentActivity() {


var izinVerildimi = false


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
        Column {
            ToolbarWithMenu(navController)

            Text(text = "This is first  Screen")
            LazyColumn {


            }
        }
    }

    @Composable
    fun NavigateScreens() {

        var navCollector = rememberNavController()
        NavHost(navController = navCollector, startDestination = "first")
        {
            composable("first") {
                FoodList(navCollector)
            }
            composable("AddFood") {
                AddFood(navCollector)
            }

            composable("izinAl") {
                ImagePermission(navCollector)
            }


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
                    Button(onClick = { navController.navigate("SaveFood") }) {
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
                    Text("GFG | Menu Options")

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


}




