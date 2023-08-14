package com.sezer.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.sezer.tipcalculator.ui.theme.TipCalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("On Created")
        setContent {
            TipCalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // TipCalculate()
                   // NextImage()
                  // Generic()
                    //SingletonDeneme()
                    //Practice()
                   // scrollablelist()

                    ActivityLifeCycle()


                }
            }
        }
    }
     fun onCreate() {
        super.onResume()
        println("On Resume")
    }

    override fun onStart() {
        super.onStart()
        println("On Start")
    }

    override fun onResume() {
        super.onResume()
        println("On Resume")
    }

    override fun onRestart() {
        super.onRestart()
       println("On Restart")
    }

    override fun onPause() {
        super.onPause()
       println("onPause")
    }

    override fun onStop() {
        super.onStop()
        println("On Stop")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("On Destroy")
    }
    private fun ActivityLifeCycle() {

    }

    @Composable
     fun scrollablelist() {
        var image1 = scrollableListPractice(R.drawable.lemon_tree, "This is a lemon tree")
        var image2 = scrollableListPractice(R.drawable.lemon_tree, "This is a lemon tree")
        var image3 = scrollableListPractice(R.drawable.lemon_tree, "This is a lemon tree")
        var image4 = scrollableListPractice(R.drawable.lemon_tree, "This is a lemon tree")
        var image_list = mutableListOf<scrollableListPractice>(image1, image2, image3, image4)

        LazyColumn {
            items(image_list) { item ->
                // Compose the content of each item here
                Row {
                    Image(painter = painterResource(id = item.imageId), contentDescription = null)
                    Text(text = item.ImageText)
                    Image(painter = painterResource(id = item.imageId), contentDescription = null)
                    Text(text = item.ImageText)
                }

            }
        }


    }

    fun Practice()
{
    println("Hello, world!")
    var etkinlik= Event("Study Kotlin","Commit to studying Kotlin at least 15 minutes per day."
        ,Daypart.Morning,15)
    var etkinlik2= Event("Study Kotlin","Commit to studying Kotlin at least 15 minutes per day."
        ,Daypart.Evening,16)
    var etkinlik3= Event("Study Kotlin","Commit to studying Kotlin at least 15 minutes per day."
        ,Daypart.Morning,17)
    var etkinlik4= Event("Study Kotlin","Commit to studying Kotlin at least 15 minutes per day."
        ,Daypart.Afternoon,18)
    var etkinlik5= Event("Study Kotlin","Commit to studying Kotlin at least 15 minutes per day."
        ,Daypart.Evening,19)
    val event_list = mutableListOf<Event>(etkinlik,etkinlik2,etkinlik3,
        etkinlik4,etkinlik5)

    val UpdatedList = event_list.filter{it.duration%2 == 0 }
        println(UpdatedList.size)


    val groupedEvents = event_list.groupBy { it.daypart }
    groupedEvents.forEach { (a, b) ->
        println("$a: ${b.size} events")
    }
    println("Last event of the day: ${event_list.last().daypart}")

    


}
    private fun SingletonDeneme() {

       println( "Object Dememe :"+ObjectDeneme.isim)
        //Also we can access object if object is at a class

       println(ObjectClass.Human.age) //companion olmadan
        println( ObjectClass.yas) //compainon ile
    }

    private fun Generic() {
        // Generic ve enum iç içe
        var sinif = GenericDeneme<String>("true",EnumDeneme.Kolay)
        var sinif2 = GenericDeneme<Boolean>(true,EnumDeneme.Orta)
        println(sinif.Yazdir("sezer"))

        var enum = EnumDeneme.Kolay
        println(enum)
        var liste = listOf<Int>(1,2,3,4,5)
        liste.forEach{
            println(it)
        }


    }

    @Composable
    fun NextImage() {



        var imageNum by remember {
            mutableStateOf(1)
        }
        var imageID by remember {
            mutableStateOf(R.drawable.lemon_tree)
        }
        Column {

            Image(painter = painterResource(id = imageID), contentDescription = "")
            Row {
                Button(onClick = {
                    if(imageNum>=1)
                    {
                        imageNum--
                    }
                }) {
                    Text(text = "Previous Image")
                }


                Button(onClick = {
                    if(imageNum<=4)
                    {
                        imageNum++
                    }

                }) {
                    Text(text = "Next Image")
                }
            }
            when (imageNum) {
                1 -> imageID = R.drawable.lemon_tree
                2->imageID = R.drawable.lemon_squeeze
                3->imageID = R.drawable.lemon_drink
                4->imageID = R.drawable.lemon_restart
            }
        }

    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TipCalculate() {
        var bill by remember {
            mutableStateOf("")
        }
        var tip_precent by remember {
            mutableStateOf("")
        }
        var workers by remember {
            mutableStateOf("")
        }
        var billWithTip by remember {
            mutableStateOf(0.0)
        }
        var tipPerWorkers by remember {
            mutableStateOf(0.0)
        }
        var swichValue by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxWidth(),

            verticalArrangement = Arrangement.Center
        ) {

            TextField(
                value = bill,
                onValueChange = { bill = it },
                label = { Text(text = "What is bill") })
            TextField(
                value = tip_precent,
                onValueChange = { tip_precent = it },
                label = { Text(text = "Tip Percent %") })
            TextField(
                value = workers,
                onValueChange = { workers = it },
                label = { Text(text = "How many workers are there ?") })

            Button(onClick = {

                billWithTip = bill.toDouble() + (bill.toDouble() * tip_precent.toDouble() / 100)
                tipPerWorkers = (bill.toDouble() * tip_precent.toDouble() / 100) / workers.toInt()

            }) {
                Text(text = "Calculate")
            }

            Text(text = "Bill with tip : ${billWithTip}")
            Text(text = "Tip per Workers : ${tipPerWorkers}")
            Switch(
                checked = swichValue,
                onCheckedChange = { swichValue = !swichValue },
            )
            Text(text = " Swich : ${swichValue}")

        }

    }


}

