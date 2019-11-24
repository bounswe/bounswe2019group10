package com.example.yallp_android.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yallp_android.R
import com.example.yallp_android.activities.WritingActivity
import com.example.yallp_android.adapters.WritingListAdapter
import com.example.yallp_android.models.WritingListElement
import com.example.yallp_android.util.RetroClients.SearchRetroClient
import com.example.yallp_android.util.RetroClients.WritingRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class WritingListFragment : Fragment(), WritingListAdapter.WritingListAdapterClickListener {
    private var sharedPref: SharedPreferences? = null
    private lateinit var searchView: SearchView
    private lateinit var writingRecyclerView: RecyclerView
    private var writingList = ArrayList<WritingListElement>()
    private var adapter: WritingListAdapter? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sharedPref = this.activity?.getSharedPreferences("yallp", Context.MODE_PRIVATE)

        val root = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        searchView = root.findViewById(R.id.searchView) as SearchView
        writingRecyclerView = root.findViewById(R.id.listView) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(this.activity?.applicationContext)
        writingRecyclerView.layoutManager = linearLayoutManager
        adapter = WritingListAdapter(this.activity?.applicationContext, writingList,this)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.equals("") ) {
                    listAllWritings()
                } else {
                    submitQuery(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("") ) {
                    listAllWritings()
                }
                return false
            }
        })
        listAllWritings()

        return root
    }

    fun listAllWritings() {
        val call: Call<Array<WritingListElement>> = WritingRetroClient.getInstance().writingApi.getWritingListForLanguage("Bearer " + sharedPref?.getString("token", null)!!,
                this.activity?.intent!!.getIntExtra("languageId", 1))

        call.enqueue(object : Callback<Array<WritingListElement>> {
            override fun onResponse(call: Call<Array<WritingListElement>>, response: Response<Array<WritingListElement>>) {
                if (response.isSuccessful) {
                    writingList.clear()
                    Collections.addAll(writingList, *response.body())
                    writingRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                } else {

                    //    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Array<WritingListElement>>, t: Throwable) {
            }
        })

    }

    fun submitQuery(query: String) {
        val call: Call<Array<WritingListElement>> = SearchRetroClient.getInstance().searchApi.searchWriting("Bearer " + sharedPref?.getString("token", null)!!,
                this.activity?.intent!!.getIntExtra("languageId", 1),
                query)

        call.enqueue(object : Callback<Array<WritingListElement>> {

            override fun onResponse(
                    call: Call<Array<WritingListElement>>,
                    response: Response<Array<WritingListElement>>
            ) {
                if (response.isSuccessful) {
                    writingList.clear()
                    Collections.addAll(writingList, *response.body())
                    writingRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                } else {
                }
            }

            override fun onFailure(call: Call<Array<WritingListElement>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun writingListAdapterClick() {

      /* val builder = AlertDialog.Builder(this.activity?.applicationContext!!)
        builder.setTitle("Last one step")
                .setMessage("Do you want to start solving $topic $quizId ?")
                .setIcon(R.drawable.penguin)
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this.activity?.applicationContext, WritingActivity::class.java)
                    intent.putExtra("quizId", quizId)
                    startActivity(intent)
                }
                .setNegativeButton("No") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
*/
    }

    companion object {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        @JvmStatic
        fun newInstance(): WritingListFragment {
            return WritingListFragment()
        }
    }

}