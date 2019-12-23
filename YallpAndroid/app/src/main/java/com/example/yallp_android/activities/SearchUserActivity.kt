package com.example.yallp_android.activities


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yallp_android.R
import com.example.yallp_android.adapters.UserListAdapter
import com.example.yallp_android.helper.TabHelper
import com.example.yallp_android.models.UserListElement
import com.example.yallp_android.util.RetroClients.SearchRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class SearchUserActivity : AppCompatActivity(), UserListAdapter.UserListAdapterClickListener {


    private lateinit var sharedPref: SharedPreferences
    private lateinit var searchView: SearchView
    private lateinit var userListView: RecyclerView
    private var userList = ArrayList<UserListElement>()
    private var adapter: UserListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        supportActionBar!!.hide()
        setContentView(R.layout.activity_search_user)

        sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)

        userListView = findViewById(R.id.listView)
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        userListView.layoutManager = linearLayoutManager
        adapter = UserListAdapter(applicationContext, userList, this)

        searchView = findViewById(R.id.searchView)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                clearList()
                if (query != "" || query != " ") {
                    submitQuery(query)
                }

                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                clearList()
                if (newText != "" || newText != " ") {
                    submitQuery(newText)
                }
                return false
            }
        })
    }

    fun submitQuery(query: String) {
        val call: Call<Array<UserListElement>> = SearchRetroClient.getInstance().searchApi.searchUser("Bearer " + sharedPref.getString("token", null)!!, query)
        call.enqueue(object : Callback<Array<UserListElement>> {

            override fun onResponse(
                    call: Call<Array<UserListElement>>,
                    response: Response<Array<UserListElement>>
            ) {
                if (response.isSuccessful) {
                    userList.clear()
                    Collections.addAll(userList, *response.body())
                    userListView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Array<UserListElement>>, t: Throwable) {
                if (applicationContext != null) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun clearList() {
        userList.clear()
        userListView.adapter = adapter
        adapter?.notifyDataSetChanged()
    }

    override fun userListAdapterClick(id: Int) {
        val i = Intent(this.applicationContext,ProfileVisitPageActivity::class.java)
        i.putExtra("memberId",id)
        startActivity(i)
    }

    override fun onBackPressed() {
        val i = Intent(this, HomePageActivity::class.java)
        i.putExtra("tabNumber", TabHelper.MESSAGE_TAB_NUMBER)
        startActivity(i)
        finish()
    }
}
