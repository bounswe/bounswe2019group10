package com.example.yallp_android.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.yallp_android.helper.CommentsHelper
import com.example.yallp_android.R
import com.example.yallp_android.adapters.CommentsAdapter

class SeeCommentsActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.comments_visit_page)

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)

        setListView()

    }

    private fun setListView() {
        val listView = findViewById<ListView>(R.id.commentsListView)
        val adapter = CommentsAdapter(this, CommentsHelper.comments)
        listView.adapter = adapter

    }

}