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
        addStartImages()
        setContentView(R.layout.activity_main)

        mainView.layoutManager = LinearLayoutManager(this)
        mainView.adapter = ImageAdapter(records)

        setRecyclerViewItemSwipeListener()

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

    private fun setRecyclerViewItemSwipeListener() {
        val swipeHandler = object : SwipeToDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = mainView.adapter as ImageAdapter
                adapter.deleteItem(viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(mainView)
    }


    private fun addStartImages() {
        if (records.isEmpty()) {
            records.add(Record("https://http.cat/404.jpg", "404", "11.11.2010", ArrayList()))
            records.add(Record("https://http.cat/100.jpg", "100", "10.6.2016", ArrayList()))
            records.add(Record("https://http.cat/101", "101", "2.3.2016", ArrayList()))
            records.add(Record("https://http.cat/426", "426", "11.11.2019", ArrayList()))
            records.add(Record("https://http.cat/429", "429", "11.7.2089", ArrayList()))
            records.add(Record("https://http.cat/400", "400", "2.6.2013", ArrayList()))
            records.add(Record("https://http.cat/202", "202", "13.21.2011", ArrayList()))
            records.add(Record("https://http.cat/510", "510", "19.11.1991", ArrayList()))
            records.add(Record("https://www.wykop.pl/cdn/c3201142/comment_UZV5a7PXQxXZsP0zdguaonrWU0bT2eRR.jpg", "Android Studio meme", "21.5.2019", ArrayList()))
            records.add(Record("https://img.devrant.com/devrant/rant/r_651554_B2KTq.jpg", "XDD", "1.9.2018", ArrayList()))


        }
    }

    companion object {
        const val KEY_FPF_RECORDS_INTENT = "KEY_FPF_RECORDS_INTENT"
        const val KEY_FPF_ADD = "KEY_FPF_ADD_RECORDS"
        const val KEY_FPF_RECEIVE = "KEY_FPF_RECEIVE_RECORDS"
        const val KEY_FPF_CONTACT_REQUEST = 1337
    }

}
