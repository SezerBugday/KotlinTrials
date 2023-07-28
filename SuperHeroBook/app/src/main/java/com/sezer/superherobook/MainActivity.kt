package com.sezer.superherobook

import android.os.Bundle
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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigateScreens()
        }
    }
}
@Composable
fun HeroList(HeroList:List<HeroSinifi>,onHeroSelected: (HeroSinifi) -> Unit)
{


    LazyColumn{
        this.items(HeroList) {
                item -> Text(text = item.Name,
            modifier = Modifier
                .clickable { onHeroSelected(item) }
        )



        }
    }
}
@Composable
fun HeroDetails(Hero : HeroSinifi)
{
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = Hero.Name,
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(16.dp)
        )
        Image(
            painter = painterResource(id = Hero.Id),
            contentDescription = null,
            modifier = Modifier.size(200.dp).clip(shape = RoundedCornerShape(8.dp))
        )
    }
}

@Composable
fun NavigateScreens()
{
    var  listeKahramanlar = listOf(
        HeroSinifi("batman",R.drawable.batman),
        HeroSinifi("superman",R.drawable.superman)

    )


    val navCollector = rememberNavController()
    NavHost(navController = navCollector, startDestination = "first" )
    {
        composable("first")
        {
            HeroList(listeKahramanlar){
                    lambdaF ->   navCollector.navigate("second/${lambdaF.Name}")

            }
        }
        composable(
            route = "second/{Deneme}",
        ) { backStackEntry ->
            val heroName = backStackEntry.arguments?.getString("Deneme")
            val hero = listeKahramanlar.find { it.Name == heroName }
            hero?.let { HeroDetails(it) }
        }
    }
}
