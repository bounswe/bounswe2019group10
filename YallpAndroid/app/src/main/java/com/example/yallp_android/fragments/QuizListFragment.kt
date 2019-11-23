package com.example.yallp_android.fragments

import android.content.Context
import android.content.Intent.getIntent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.yallp_android.R
import com.example.yallp_android.adapters.QuizListAdapter
import com.example.yallp_android.models.Quiz
import com.example.yallp_android.util.RetroClients.QuizRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QuizListFragment : Fragment() {

    private var sharedPref: SharedPreferences? = null
    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
    // private  var list =  ArrayList<Card>()
    private var adapter: QuizListAdapter? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //sharedPref = getSharedPreferences("yallp", Context.MODE_PRIVATE)

        val root = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        listView = root.findViewById(R.id.listView) as ListView
        searchView = root.findViewById(R.id.searchView) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.length >= 0) {
                    listAllQuizzes()
                } else {
                    submitQuery(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
        listAllQuizzes()

        return root
    }

    fun listAllQuizzes() {

    /*    val call: Call<Array<Quiz>>
        call = QuizRetroClient.getInstance().quizApi.getQuizForLevelOrLowerAndLanguage("Bearer " + sharedPref.getString("token", null)!!,
                getIntent().getIntExtra("level", 1),
                getIntent().getIntExtra("languageId", 1))

        call.enqueue(object : Callback<Array<Quiz>> {
            override fun onResponse(call: Call<Array<Quiz>>, response: Response<Array<Quiz>>) {
                if (response.isSuccessful) {
                    Collections.addAll(quizzes, *response.body())
                    adapter.notifyDataSetChanged()
                    quizList.setAdapter(adapter)
                } else {
                    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Array<Quiz>>, t: Throwable) {

            }
        })*/

    }

    fun submitQuery(query: String) {
        /*   val call: Call<ArrayList<Card>> = CardRetroClient.getInstance().cardApi.search(query)

           call.enqueue(object : Callback<ArrayList<Card>> {
               override fun onResponse(
                       call: Call<ArrayList<Card>>,
                       response: Response<ArrayList<Card>>
               ) {
                   if (response.isSuccessful) {
                       list = response.body()!!
                       if (list.size >= 10)
                           list = ArrayList(list.subList(0, 9))
                       adapter = context?.let {
                           CardAdapter(
                                   it,
                                   list
                           )
                       }
                       listView.adapter = adapter
                   } else {
                       list.clear()
                       adapter = context?.let {
                           CardAdapter(
                                   it,
                                   list
                           )
                       }
                       listView.adapter = adapter
                   }
               }

               override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                   Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
               }
           })*/
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
        fun newInstance(): QuizListFragment {
            return QuizListFragment()
        }
    }

}