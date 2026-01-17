package com.android.recipieapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.recipieapp.databinding.SearchRvBinding
import com.bumptech.glide.Glide

class SearchAdapter(private var dataList: ArrayList<Recipe>, var context : Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    inner class ViewHolder(var binding : SearchRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = SearchRvBinding.inflate(LayoutInflater.from(context), parent , false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun filterList(filterList: ArrayList<Recipe>){
        dataList = filterList
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(dataList.get(position).img).into(holder.binding.mainImage)
        holder.binding.title.text = dataList.get(position).tittle
        holder.itemView.setOnClickListener {
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