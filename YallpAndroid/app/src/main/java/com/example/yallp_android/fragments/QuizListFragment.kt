package com.example.yallp_android.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yallp_android.R
import com.example.yallp_android.activities.QuizActivity
import com.example.yallp_android.adapters.QuizListAdapter
import com.example.yallp_android.models.QuizListElement
import com.example.yallp_android.util.RetroClients.QuizRetroClient
import com.example.yallp_android.util.RetroClients.SearchRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList

class QuizListFragment : Fragment(), QuizListAdapter.QuizListAdapterClickListener {

    private var sharedPref: SharedPreferences? = null
    private lateinit var searchView: SearchView
    private lateinit var quizRecyclerView: RecyclerView
    private var quizList = ArrayList<QuizListElement>()
    private var adapter: QuizListAdapter? = null
    private var langId =1


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sharedPref = this.activity?.getSharedPreferences("yallp", Context.MODE_PRIVATE)
        langId = this.activity?.intent!!.getIntExtra("languageId", 1)
        val root = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        searchView = root.findViewById(R.id.searchView) as SearchView
        quizRecyclerView = root.findViewById(R.id.listView) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(this.activity?.applicationContext)
        quizRecyclerView.layoutManager = linearLayoutManager
        adapter = QuizListAdapter(this.activity?.applicationContext, quizList, this)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
               if (query=="" || query=="  ")  {
                    listAllQuizzes()
                } else {
                    submitQuery(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (newText.equals("")) {
                    listAllQuizzes()
                }
                return false
            }
        })
        listAllQuizzes()

        return root
    }

    fun listAllQuizzes() {

        val call: Call<Array<QuizListElement>> = QuizRetroClient.getInstance().quizApi.getQuizForLevelOrLowerAndLanguage("Bearer " + sharedPref?.getString("token", null)!!,
                this.activity?.intent!!.getIntExtra("level", 1),
                this.activity?.intent!!.getIntExtra("languageId", 1))

        call.enqueue(object : Callback<Array<QuizListElement>> {
            override fun onResponse(call: Call<Array<QuizListElement>>, response: Response<Array<QuizListElement>>) {
                if (response.isSuccessful) {
                    quizList.clear()
                    Collections.addAll(quizList, *response.body())
                    quizRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Array<QuizListElement>>, t: Throwable) {
            }
        })
    }

    fun submitQuery(query: String) {
        val call: Call<Array<QuizListElement>> = SearchRetroClient.getInstance().searchApi.searchQuiz("Bearer " + sharedPref?.getString("token", null)!!,
                this.activity?.intent!!.getIntExtra("languageId", 1),
                query)
        call.enqueue(object : Callback<Array<QuizListElement>> {

            override fun onResponse(
                    call: Call<Array<QuizListElement>>,
                    response: Response<Array<QuizListElement>>
            ) {
                if (response.isSuccessful) {
                    quizList.clear()
                    Collections.addAll(quizList, *response.body())
                    quizRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                }else{
                    Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Array<QuizListElement>>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


    override fun quizListAdapterClick(topic: String?, quizId: String?) {
        val intent = Intent(this.activity?.applicationContext, QuizActivity::class.java)
        intent.putExtra("quizId", quizId)
        intent.putExtra("langId", langId)
        startActivity(intent)
        this.activity?.finish()
    }

    companion object {

        @JvmStatic
        fun newInstance(): QuizListFragment {
            return QuizListFragment()
        }
    }

}