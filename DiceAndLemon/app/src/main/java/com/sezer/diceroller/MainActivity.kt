package com.sezer.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.sezer.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background

                ) {
                    //Dice()
                    LemonLife()
                   /* LambdaDene("bu bir yazidir"){
                        println("SetActive içinden çağırıldı")
                    }

                    */
                    println("saezer")
                }
            }
        }
    }
}

@Composable
fun Dice()
{

var imageResponce by remember {
    mutableStateOf(R.drawable.one)
}

    Column(modifier = Modifier,
    horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = imageResponce ), contentDescription ="" )



        Button(onClick = {
            var num =(1..6).random()
            when(num)
            {
                1-> imageResponce = R.drawable.one
                2->imageResponce = R.drawable.two
                3 ->imageResponce = R.drawable.three
                4->imageResponce = R.drawable.four
                5->imageResponce = R.drawable.five
                6->imageResponce = R.drawable.six

            }
        }) {
            Text(text = "Zar at")
        }
    }

}

@Composable
fun LemonLife()
{
    var lemonResouce by remember {
        mutableStateOf( R.drawable.lemon_tree)
    }
    var counter by remember {
        mutableStateOf(1)
    }
    var ramdomNum by remember {
        mutableStateOf((1..5).random())
    }
    var ClickedCount by remember {
        mutableStateOf(0)
    }

    Image(
        painter = painterResource(id = lemonResouce), contentDescription = null,
        modifier = Modifier
            .clickable{
                if (counter>=4)
                {
                    counter = 0

                }
                if(counter ==2)
                {
                    ClickedCount++
                    println("ClickedCount ${ClickedCount}  random num : ${ramdomNum}")
                    if (ClickedCount!=ramdomNum)
                    {
                        lemonResouce= R.drawable.lemon_squeeze
                    }
                    else
                    {
                        counter++
                      ramdomNum = (1..5).random()
                        ClickedCount = 0
                    }

                }
                else
                {
                    counter++
                }
                println(counter)
                when(counter)
                {

                    1-> lemonResouce= R.drawable.lemon_tree
                    2 -> lemonResouce= R.drawable.lemon_squeeze
                    3-> lemonResouce= R.drawable.lemon_drink
                    4->lemonResouce= R.drawable.lemon_restart
                }
            }
    )
}

@Composable
fun LambdaDene(name:String, sezer: () -> Unit)
{
   println("Lamda dene ici ${name}")
    Button(onClick = sezer) {
        Text(text = "sdffffffffff")
    }
    
}
