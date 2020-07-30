package com.riis.foodclassifier.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.riis.foodclassifier.model.ImageItem
import  androidx.recyclerview.widget.RecyclerView
import com.riis.foodclassifier.R
import com.riis.foodclassifier.tflite.Classifier

class ImageItemAdapter(val imageItems: List<ImageItem>, val classifier: Classifier):
    RecyclerView.Adapter<ImageItemAdapter.ViewHolder>() {


    inner class ViewHolder(imageItemView: View) : RecyclerView.ViewHolder(imageItemView), View.OnClickListener {
        val dogNameView = itemView.findViewById<TextView>(R.id.dogName)
        val dogImageView = itemView.findViewById<ImageView>(R.id.dogImage)
        init{
            itemView.setOnClickListener(this)
        }
        // when the user clicks the image display the confidence and the label.
        override fun onClick(view: View?) {
            if(adapterPosition != RecyclerView.NO_POSITION){
                val item: ImageItem = imageItems[adapterPosition]
                val recognitions = classifier.recognizeImage(item.image)
                if(recognitions.isNotEmpty()){
                    item.confidence = recognitions[0].confidence
                    item.label = recognitions[0].title
                    notifyItemChanged(adapterPosition)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(R.layout.image_item, parent, false)
        // Return a new holder instance
        return ViewHolder(contactView)
    }


    // Returns the total count of items in the list
    override fun getItemCount(): Int {
        return imageItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem: ImageItem = imageItems.get(position)
        val textView = holder.dogNameView
        textView.setText(imageItem.getTitle())

        val imageView = holder.dogImageView
        imageView.setImageBitmap(imageItem.image)
    }
}