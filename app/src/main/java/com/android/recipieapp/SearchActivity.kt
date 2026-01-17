package com.android.recipieapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.room.Room
import com.android.recipieapp.databinding.ActivitySearchBinding
import java.util.ArrayList

class SearchActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySearchBinding
    private lateinit var RVAdapter : SearchAdapter
    private lateinit var datalist : ArrayList<Recipe>
    private lateinit var recipes : List<Recipe?>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        var db = Room.databaseBuilder(this@SearchActivity, AppDatabase::class.java, "db_name")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .createFromAsset("recipe.db")
            .build()

        var daoObject = db.getDao()
        recipes = daoObject.getAll()!!

        binding.RVSearch.requestFocus()
        setUpRecyclerView()

        binding.searchBar.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString()!=""){
                    filterData(s.toString())
                }else  {
                    setUpRecyclerView()
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }

        })


        binding.goBackHome.setOnClickListener {
            finish()
        }

    }

    private fun filterData(filterText: String) {
        var filterData = ArrayList<Recipe>()
        for (i in recipes?.indices!!){
            if (recipes!![i]?.tittle?.lowercase()?.contains(filterText.lowercase()) == true){
                recipes!![i]?.let { filterData.add(it) }
            }
            RVAdapter.filterList(filterList = filterData)
        }
    }

    private fun setUpRecyclerView() {
        datalist = java.util.ArrayList()
        binding.RVSearch.layoutManager = LinearLayoutManager(this )

        for (i in recipes!!.indices) {
            recipes!![i]?.let { datalist.add(it) }

        }
        RVAdapter = SearchAdapter(datalist , this)
        binding.RVSearch.adapter = RVAdapter
    }
}