package com.caren.simpletodo

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Bridge to tell recyclerview how to display data we give it
 */
class TaskItemAdapter(val listOfItems: List<String>, val longClickListener: OnLongClickListener):
    RecyclerView.Adapter<TaskItemAdapter.ViewHolder>(){

    //created to help the MainActivity to eventually be able to delete a task
    interface OnLongClickListener{
        fun onItemLongClicked(position: Int)
    }

    interface OnClickListener {
        fun onItemClicked(position: Int)
    }

    /**
     * Usually involves inflating a layout from XML and returning the holder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        // Inflate the custom layout
        val contactView = inflater.inflate(android.R.layout.simple_list_item_1, parent, false)  //Use "CTRL + B" to open up simple_list_item_1 xml file layout
        // Return a new holder instance
        return ViewHolder(contactView)
    }

    /**
     * Involves populating data into the item through the ViewHolder
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Get the data model of the listOfItems based on position
        val item = listOfItems.get(position)
        // Set item views based on your views and data model
        holder.textView.text = item

    }

    /**
     * Returns the total count of items in the list
     */
    override fun getItemCount(): Int {
        return listOfItems.size
    }


    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Store references to elements in our layout view
        val textView: TextView  //val textView of type TextView

        init{   //means the first block of code to be executed when class is instantiated and will run everytime class is instantiated
            textView = itemView.findViewById(android.R.id.text1)

            itemView.setOnLongClickListener {
                longClickListener.onItemLongClicked(adapterPosition)
                true
            }
        }
    }
}