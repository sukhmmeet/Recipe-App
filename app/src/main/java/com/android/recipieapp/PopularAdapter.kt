package com.android.recipieapp

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PopularAdapter (private val dataList: ArrayList<Recipe> , var context : Context) : RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_item_popular_recipe, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val data = dataList[position]
        var popularImg = holder.img
        var popularTxt = holder.txt
        var popularTime = holder.time

        popularTxt.text = dataList.get(position).tittle
        var time = dataList.get(position).ing.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        popularTime.text = time.get(0)

        Glide.with(context).load(dataList.get(position).img).into(popularImg)

        holder.itemView.setOnClickListener {
            var intent = Intent(context , RecipeActivity::class.java)
            intent.putExtra("IMG", dataList.get(position).img)
            intent.putExtra("TITLE", dataList.get(position).tittle)
            intent.putExtra("DES", dataList.get(position).des)
            intent.putExtra("ING", dataList.get(position).ing)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }

//        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(ItemsViewModel.image)
//
//        // sets the text to the textview from our itemHolder class
//        holder.textView.text = ItemsViewModel.text


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return dataList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
//        val textView: TextView = itemView.findViewById(R.id.textView)
        val img = itemView.findViewById<ImageView>(R.id.popular_img)
        val txt = itemView.findViewById<TextView>(R.id.popular_text)
        val time = itemView.findViewById<TextView>(R.id.popular_time)

    }
}
// (var dataList : ArrayList<Recipe> , var context : Context) :
//    RecyclerView.Adapter<PopularAdapter.ViewHolder>() {
//
//    inner class ViewHolder(var binding: PopularAdapter): RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        var binding = PopularAdapter.inflate(LayoutInflater.from(context),parent , false)
//        return ViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        TODO("Not yet implemented")
//    }
//
//    override fun getItemCount(): Int {
//        return dataList.size
//    }
//}