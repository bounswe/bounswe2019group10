package com.example.yallp_android.activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.yallp_android.R
import com.example.yallp_android.adapters.UserLanguageListAdapter
import com.example.yallp_android.custom_views.ExpandableTextView
import com.example.yallp_android.models.UserInfo
import com.example.yallp_android.util.RetroClients.UserRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileVisitPageActivity : AppCompatActivity() {


    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.profile_page_visit)

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)

        Log.e("e","${intent.extras==null}")
        if(intent.extras!=null){
            callById(intent.getIntExtra("memberId",120))
        }

    }

    private fun callById(id:Int) {
        val call: Call<UserInfo> = UserRetroClient
                .getInstance()
                .userApi
                .getProfileInfoWithId("Bearer " + sharedPref.getString("token", null)!!, id)

        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    Log.e("e","succes")
                    val userInfo = response.body()
                    val userNameText = findViewById<TextView>(R.id.profileUsername)
                    var username =  userInfo.username
                    if (!(userInfo.name == "" && userInfo.surname == "")) {
                        username += " ( "
                        username += userInfo.name
                        username += " "
                        username += userInfo.surname
                        username += " )"
                    }
                    userNameText.text = username
                    val mailText = findViewById<TextView>(R.id.profileMail)
                    mailText.text = userInfo.mail
                    val expandableText = findViewById<ExpandableTextView>(R.id.expandableTextView)
                    expandableText.text = userInfo.bio
                    val seeFullBio = findViewById<TextView>(R.id.seeFullBio)
                    seeFullBio.setOnClickListener {
                        expandableText.changeTrim()
                        expandableText.setText()
                        if (seeFullBio.text === resources.getString(R.string.see_full_bio)) {
                            seeFullBio.setText(R.string.hide_bio)
                        } else {
                            seeFullBio.setText(R.string.see_full_bio)
                        }
                    }
                    seeFullBio.callOnClick()

                    val languageNameList = ArrayList<String>()
                    val languageAndLevelId = ArrayList<String>()
                    val languageLevelList = ArrayList<String>()

                    for (element in userInfo.memberLanguages) {
                        languageNameList.add(element.language.languageName)
                        languageAndLevelId.add(element.language.id.toString() + " " + element.languageLevel)
                        if (element.levelName == null)
                            languageLevelList.add(resources.getString(R.string.not_graded_yet))
                        else
                            languageLevelList.add(element.levelName)
                    }


                    val listView = findViewById<ListView>(R.id.languages)
                    val adapter = UserLanguageListAdapter(applicationContext, languageNameList, languageLevelList, 0, sharedPref,true)
                    listView.adapter = adapter

                    val seeCommentsView = findViewById<TextView>(R.id.commentsTitle)
                    seeCommentsView.setOnClickListener{
                        seeComments(id)
                    }

                } else {
                    Toast.makeText(applicationContext, "There has been an error!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {
                Log.e("e","fail")

            }
        })
    }

    private fun seeComments(id:Int){
        
    }

}
