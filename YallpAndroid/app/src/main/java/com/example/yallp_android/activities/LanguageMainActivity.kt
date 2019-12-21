package com.example.yallp_android.activities

import android.content.Intent
import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.yallp_android.R
import com.example.yallp_android.adapters.SectionsPagerAdapter
import com.example.yallp_android.helper.TabHelper
import com.google.android.material.tabs.TabLayout

class LanguageMainActivity   : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_language)
        val sectionsPagerAdapter = SectionsPagerAdapter(
                this,
                supportFragmentManager
        )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)
    }

    override fun onBackPressed() {
        val i = Intent(this, HomePageActivity::class.java)
        i.putExtra("tabNumber", TabHelper.LANGUAGE_TAB_NUMBER)
        startActivity(i)
        finish()
    }

}