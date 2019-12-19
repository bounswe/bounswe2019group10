package com.example.yallp_android.activities

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.yallp_android.CommentsHelper
import com.example.yallp_android.R
import com.example.yallp_android.adapters.UserLanguageListAdapter
import com.example.yallp_android.custom_views.ExpandableTextView
import com.example.yallp_android.models.Comment
import com.example.yallp_android.models.CommentSubmit
import com.example.yallp_android.models.UserInfo
import com.example.yallp_android.util.RetroClients.CommentRetroClient
import com.example.yallp_android.util.RetroClients.UserRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileVisitPageActivity : AppCompatActivity() {


    private lateinit var sharedPref: SharedPreferences
    lateinit var comments: Array<Comment>
    private lateinit var userInfo: UserInfo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.profile_page_visit)

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)

        if (intent.extras != null) {
            callById(intent.getIntExtra("memberId", 120))
        }

        val addComment = findViewById<Button>(R.id.addComment)
        addComment.setOnClickListener {

            val builder = AlertDialog.Builder(this@ProfileVisitPageActivity)
            val inflater = this@ProfileVisitPageActivity.getLayoutInflater()
            val view = inflater.inflate(R.layout.dialog_add_comment, null)
            val commentText = view.findViewById(R.id.commentText) as EditText
            builder.setView(view)
                    .setPositiveButton("Send", DialogInterface.OnClickListener { dialog, id ->
                        val newComment = commentText.getText().toString()
                        if (newComment.length == 0) {
                            return@OnClickListener
                        }else{
                            val call: Call<Comment>
                            val commentToSubmit = CommentSubmit(newComment,userInfo.id)
                            call = CommentRetroClient.getInstance().getCommentApi().makeComment("Bearer " + sharedPref.getString("token", null)!!,commentToSubmit)
                            call.enqueue(object : Callback<Comment> {
                                override fun onResponse(call: Call<Comment>, response: Response<Comment>) {
                                    if (response.isSuccessful) {
                                        Toast.makeText(baseContext, "Your comment has been successfully added", Toast.LENGTH_LONG).show()

                                    } else {
                                        Toast.makeText(baseContext, "There has been an error!", Toast.LENGTH_LONG).show()
                                    }
                                }

                                override fun onFailure(call: Call<Comment>, t: Throwable) {

                                }
                            })
                        }

                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, id -> })
            val dialog = builder.create()
            dialog.show()
        }

        val sendMessage = findViewById<Button>(R.id.sendMessage)
        sendMessage.setOnClickListener {
            val i = Intent(this,ConversationActivity::class.java)
            i.putExtra("sendTo", userInfo.username)

            startActivity(i)
            finish()
        }

    }

    private fun callById(id: Int) {
        val call: Call<UserInfo> = UserRetroClient
                .getInstance()
                .userApi
                .getProfileInfoWithId("Bearer " + sharedPref.getString("token", null)!!, id)

        call.enqueue(object : Callback<UserInfo> {
            override fun onResponse(call: Call<UserInfo>, response: Response<UserInfo>) {
                if (response.isSuccessful) {
                    userInfo = response.body()
                    val userNameText = findViewById<TextView>(R.id.profileUsername)
                    var username = userInfo.username
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
                    val adapter = UserLanguageListAdapter(applicationContext, languageNameList, languageLevelList, 0, sharedPref, true)
                    listView.adapter = adapter

                    val seeCommentsView = findViewById<TextView>(R.id.seeComments)
                    seeCommentsView.setOnClickListener {
                        seeComments(id)
                    }

                } else {
                    Toast.makeText(applicationContext, "There has been an error!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<UserInfo>, t: Throwable) {

            }
        })
    }

    private fun seeComments(id: Int) {

        val call: Call<Array<Comment>> = CommentRetroClient.getInstance().commentApi.getCommentsbyId("Bearer " + sharedPref.getString("token", null)!!, id)
        call.enqueue(object : Callback<Array<Comment>> {
            override fun onResponse(call: Call<Array<Comment>>, response: Response<Array<Comment>>) {
                if (response.isSuccessful) {
                    comments = response.body()
                    if(comments.isEmpty()){
                        Toast.makeText(baseContext, "No comment found for this user!", Toast.LENGTH_LONG).show()
                    }else{
                        val i = Intent(baseContext, SeeCommentsActivity::class.java)
                        CommentsHelper.comments.clear()
                        for(comment in comments){
                            CommentsHelper.comments.add(comment)
                        }
                        startActivity(i)
                    }

                } else {
                    Toast.makeText(baseContext, "Error loading comments!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Array<Comment>>, t: Throwable) {

            }
        })

    }

    override fun onBackPressed() {
        val i = Intent(this,HomePageActivity::class.java)
        startActivity(i)
        finish()
    }

}
