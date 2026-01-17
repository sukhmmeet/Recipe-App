package com.android.recipieapp

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.recipieapp.databinding.ActivityRecipieBinding
import com.bumptech.glide.Glide

class RecipeActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRecipieBinding
    private var imgCrop =  true

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRecipieBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImage)
        val title = intent.getStringExtra("TITLE")
        val ingredientsData = intent.getStringExtra("ING")
        val descriptionData = intent.getStringExtra("DES")
        println("Hello")
        binding.title.text = intent.getStringExtra("TITLE")
        binding.ingData.text = intent.getStringExtra("ING")
        binding.stepsData.text = intent.getStringExtra("DES")


        var time = intent.getStringExtra("ING")?.split("\n".toRegex())?.dropLastWhile { it.isEmpty() }
            ?.toTypedArray()
        if (time != null) {
            if(time[0].lowercase() !="ingredients"){
                binding.time.text = time[0]
            }else {
                binding.time.text = "None"
            }
        }

        binding.fullscreenBtn.setOnClickListener {
            if(imgCrop){
                binding.itemImage.scaleType = ImageView.ScaleType.FIT_CENTER
                Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImage)
                binding.shade.visibility = View.GONE
                binding.fullscreenBtn.setColorFilter(Color.BLACK , PorterDuff.Mode.SRC_ATOP)
                imgCrop = !imgCrop
            }else{
                binding.itemImage.scaleType = ImageView.ScaleType.CENTER_CROP
                Glide.with(this).load(intent.getStringExtra("IMG")).into(binding.itemImage)
                binding.fullscreenBtn.colorFilter = null
                binding.shade.visibility = View.VISIBLE
                imgCrop = !imgCrop
            }
        }
        binding.backBtn.setOnClickListener {
            finish()
        }





    }
}