package com.android.recipieapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.recipieapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var RVAdapter : PopularAdapter
    private lateinit var datalist : ArrayList<Recipe>
    private lateinit var binding : ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.salad.setOnClickListener {
            var myIntent = Intent(this@HomeActivity , CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Salad")
            myIntent.putExtra("CATEGORY" , "Salad")
            startActivity(myIntent)
        }
        binding.mainDish.setOnClickListener {
            var myIntent = Intent(this@HomeActivity , CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Main Dish")
            myIntent.putExtra("CATEGORY" , "Dish")
            startActivity(myIntent)

        }
        binding.drinks.setOnClickListener {
            var myIntent = Intent(this@HomeActivity , CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Drinks")
            myIntent.putExtra("CATEGORY" , "Drinks")
            startActivity(myIntent)

        }
        binding.dessert.setOnClickListener {
            var myIntent = Intent(this@HomeActivity , CategoryActivity::class.java)
            myIntent.putExtra("TITLE", "Desserts")
            myIntent.putExtra("CATEGORY" , "Desserts")
            startActivity(myIntent)

        }

        setUpRecyclerView()

        binding.search.setOnClickListener {
            startActivity(Intent(this , SearchActivity::class.java))
        }

    }

    private fun setUpRecyclerView() {
        datalist = java.util.ArrayList()
        binding.recylerViewPopular.layoutManager = LinearLayoutManager(this , LinearLayoutManager.HORIZONTAL, false)
        var db = Room.databaseBuilder(this@HomeActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if(recipes[i]?.category?.contains("Popular") == true){
                recipes[i]?.let { datalist.add(it) }
            }

        }
        RVAdapter = PopularAdapter(datalist , this)
        binding.recylerViewPopular.adapter = RVAdapter
    }
}