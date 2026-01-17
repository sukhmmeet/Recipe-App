package com.android.recipieapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.recipieapp.databinding.ActivityCategoryBinding
import com.android.recipieapp.databinding.ActivityHomeBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCategoryBinding
    private lateinit var categoryAdapter : CategoryAdapter
    private lateinit var datalist : ArrayList<Recipe>
    private lateinit var category : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.tittle.text = intent.getStringExtra("TITLE")
        category = intent.getStringExtra("CATEGORY") ?: ""
        setUpRecyclerView()

        binding.backBtn.setOnClickListener {
            finish()
        }
    }
    private fun setUpRecyclerView() {
        datalist = java.util.ArrayList()
        binding.RVCategory.layoutManager = LinearLayoutManager(this )
        var db = Room.databaseBuilder(this@CategoryActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()
        var daoObject = db.getDao()
        var recipes = daoObject.getAll()
        for (i in recipes!!.indices) {
            if (recipes[i]?.category?.contains(category) == true) {
                recipes[i]?.let { datalist.add(it) }
            }
            categoryAdapter = CategoryAdapter(datalist , this)
            binding.RVCategory.adapter = categoryAdapter
        }

    }
}