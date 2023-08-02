package com.sezer.foodbooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //nokta menuyu olustur buraya bağla
        val menuInflater = menuInflater
        menuInflater.inflate(R.menu.yemek_ekle_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.yemek_ekle_item)
            // menude acılan seceneklerden yemek_ekle_item olanına mı tıkladık diye kontrl ediyoruz
        {
            val action = FoodListDirections.actionFoodListToRecipeFragment()
            Navigation.findNavController(this,R.id.fragmentContainerView2).navigate(action)
        }
            return super.onOptionsItemSelected(item)
    }


}