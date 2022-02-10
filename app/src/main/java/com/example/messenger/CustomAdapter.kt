package com.example.messenger

import android.content.Intent
import android.os.Build.VERSION_CODES.P
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext



class CustomAdapter(private val mList: List<ItemsViewModel>) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {


    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)

    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        Log.v("CustomAdapter"," onBindViewHolder")

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(R.drawable.icon)

        // sets the text to the textview from our itemHolder class
        holder.tvTitle.text = ItemsViewModel.title
        holder.tvDescription.text=ItemsViewModel.description
        holder.tvPrice.text=ItemsViewModel.Price
        //   val button:Button=Button(mContext)
        Picasso.get().load(ItemsViewModel.image).into(holder.imageView)


    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView), View.OnClickListener {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val tvTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val tvDescription: TextView = itemView.findViewById(R.id.tvDescription)
        val tvPrice:TextView = itemView.findViewById(R.id.tvPrice)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(p0: View?) {
            val position = adapterPosition


            val intent = Intent(itemView.context, ProductDescription::class.java).apply {
                putExtra("Data",position.toString())
            }
                itemView.context.startActivity(intent)

        }

    }




}


