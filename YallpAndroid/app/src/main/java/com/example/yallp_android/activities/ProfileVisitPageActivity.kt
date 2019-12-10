package com.example.yallp_android.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import com.example.yallp_android.R

class ProfileVisitPageActivity : AppCompatActivity() {


    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.profile_page_visit)

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)


    }

}
