package com.caren.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  //set content view to activity_main.xml file

        //When user long click task remove it
        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //1. Remove the item from the list
                listOfTasks.removeAt(position)
                //2. Notify adapter data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        //When user click on task can edit it
        val onClickListener = object : TaskItemAdapter.OnClickListener {
            override fun onItemClicked(position: Int) {
                //1. Allow to edit item
                listOfTasks.get(position) //work on it
                //2. Notify adapater data set has changed

            }
        }

          //1. Detect when user clicks on add button
//        findViewById<Button>(R.id.addButton).setOnClickListener{
//        Log.i("Caren", "User clicked on the button")    //Allows log view messages in Logcat tab at the bottom
//        }

        loadItems()

        //Look up the recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)

        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter

        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up button and input field, so user can enter a task and add it to the list
        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.addButton).setOnClickListener{
            //1. Grab text user inpputed into @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()

            //2. Add string to our list of tasks: listOfTasks
            listOfTasks.add(userInputtedTask)
            //Notify adapter that data has been updated
            adapter.notifyItemInserted(listOfTasks.size - 1)

            //3. Reset text field
            inputTextField.setText("")

            saveItems()
        }

    }

    //Save the data the user has inputted by writing and reading from a file

    //Get data file we need
    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    //Load the items (read) every line in the data file
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    //Save the items (write) into data file
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

}