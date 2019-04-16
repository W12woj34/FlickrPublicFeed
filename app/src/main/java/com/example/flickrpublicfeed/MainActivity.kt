package com.example.flickrpublicfeed

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private var records = ArrayList<Record>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainView.layoutManager = LinearLayoutManager(this)
        mainView.adapter = ImageAdapter(records)

        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = mainView.adapter as ImageAdapter
                adapter.removeAt(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(mainView)
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        val id = item!!.itemId


        if (id == R.id.add) {

            val dataIntent = Intent(this, AddActivity::class.java)
            dataIntent.putParcelableArrayListExtra(KEY_FPF_ADD, records)
                startActivityForResult(dataIntent, KEY_FPF_CONTACT_REQUEST)
            }

        return super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelableArrayList(KEY_FPF_RECORDS_INTENT, records)

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            records = savedInstanceState.getParcelableArrayList(KEY_FPF_RECORDS_INTENT)
            mainView.adapter = ImageAdapter(records)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (data != null && resultCode == RESULT_OK && requestCode == KEY_FPF_CONTACT_REQUEST) {
            records.add(data.getParcelableExtra(KEY_FPF_RECEIVE))
            mainView.adapter = ImageAdapter(records)
        }


    }

    companion object {
        const val KEY_FPF_RECORDS_INTENT = "KEY_FPF_RECORDS_INTENT"
        const val KEY_FPF_ADD = "KEY_FPF_ADD_RECORDS"
        const val KEY_FPF_RECEIVE = "KEY_FPF_RECEIVE_RECORDS"
        const val KEY_FPF_CONTACT_REQUEST = 1337
    }

}
