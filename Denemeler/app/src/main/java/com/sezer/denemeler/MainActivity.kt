import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import androidx.core.app.ActivityCompat


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Create Calısti")
        setContent {

            Deneme()

        }
    }


    @Composable
    fun Deneme() {
        Text(text = "Sezerrrrrrrrrrr")
    }

    @Composable
    fun GalleryPermissionDemo() {
        val context = LocalContext.current
        // Remember the permission launcher
        val permissionLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            // Handle the permission result here if needed
            if (isGranted) {
                // Permission granted, perform actions to open gallery
            } else {
                // Permission not granted, handle accordingly
            }
        }

        Surface(

            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                // Request gallery permission when the button is clicked
                Button(
                    onClick = {
                        // Check if the permission is already granted before requesting
                        if (PermissionUtils.isGalleryPermissionGranted(context)) {
                            // Permission is already granted, perform actions to open gallery
                        } else {
                            // Permission is not granted, request the permission
                            permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    },

                    ) {
                    Text("Open Gallery")
                }
            }
        }
    }


    // Utility function to check if the gallery permission is granted
    object PermissionUtils {
        fun isGalleryPermissionGranted(context: Context): Boolean {
            return ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        }
    }
}
