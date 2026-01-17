package com.android.recipieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recipieapp.databinding.CategoryRvBinding
import com.bumptech.glide.Glide

class CategoryAdapter (var dataList : ArrayList<Recipe> , var context : Context) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {


    inner class ViewHolder(var binding : CategoryRvBinding ): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding = CategoryRvBinding.inflate(LayoutInflater.from(context), parent , false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList.get(position).img).into(holder.binding.img)
        holder.binding.foodTitle.text = dataList.get(position).tittle

        var time = dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        holder.binding.timeTxt.text = time.get(0)

        holder.binding.forwardBtn.setOnClickListener {
            var intent = Intent(context , RecipeActivity::class.java)
            intent.putExtra("IMG", dataList.get(position).img)
            intent.putExtra("TITLE", dataList.get(position).tittle)
            intent.putExtra("DES", dataList.get(position).des)
            intent.putExtra("ING", dataList.get(position).ing)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }
    }
}