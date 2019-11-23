package com.example.yallp_android.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.yallp_android.R
import com.example.yallp_android.activities.QuizActivity
import com.example.yallp_android.adapters.QuizListAdapter
import com.example.yallp_android.models.Quiz
import com.example.yallp_android.util.RetroClients.QuizRetroClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class QuizListFragment : Fragment()  , QuizListAdapter.QuizListAdapterClickListener{

    private var sharedPref: SharedPreferences? = null
    private lateinit var searchView: SearchView
    private lateinit var quizRecyclerView: RecyclerView
    private var quizList = ArrayList<Quiz>()
    private var adapter: QuizListAdapter? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        sharedPref = this.activity?.getSharedPreferences("yallp", Context.MODE_PRIVATE)

        val root = inflater.inflate(R.layout.fragment_quiz_list, container, false)
        searchView = root.findViewById(R.id.searchView) as SearchView
        quizRecyclerView = root.findViewById(R.id.listView) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(this.activity?.applicationContext)
        quizRecyclerView.layoutManager = linearLayoutManager
        adapter = QuizListAdapter(this.activity?.applicationContext, quizList,this)
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

        val call: Call<Array<Quiz>> = QuizRetroClient.getInstance().quizApi.getQuizForLevelOrLowerAndLanguage("Bearer " + sharedPref?.getString("token", null)!!,
                this.activity?.intent!!.getIntExtra("level", 1),
                this.activity?.intent!!.getIntExtra("languageId", 1))

        call.enqueue(object : Callback<Array<Quiz>> {
            override fun onResponse(call: Call<Array<Quiz>>, response: Response<Array<Quiz>>) {
                if (response.isSuccessful) {
                    Collections.addAll(quizList, *response.body())
                    quizRecyclerView.adapter = adapter
                    adapter?.notifyDataSetChanged()
                    Log.e("e", "resp")
                } else {
                    Log.e("e", "er")
                    //    Toast.makeText(getBaseContext(), "There has been an error!", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Array<Quiz>>, t: Throwable) {
                Log.e("e", "er2")

            }
        })

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
                       quizRecyclerView.adapter = adapter
                   } else {
                       list.clear()
                       adapter = context?.let {
                           CardAdapter(
                                   it,
                                   list
                           )
                       }
                       quizRecyclerView.adapter = adapter
                   }
               }

               override fun onFailure(call: Call<ArrayList<Card>>, t: Throwable) {
                   Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
               }
           })*/
    }


    override fun quizListAdapterClick(topic: String?, quizId: String?) {
        val builder = AlertDialog.Builder(this.activity?.applicationContext!!)
        builder.setTitle("Last one step")
                .setMessage("Do you want to start solving $topic $quizId ?")
                .setIcon(R.drawable.penguin)
                .setCancelable(true)
                .setPositiveButton("Yes") { _, _ ->
                    val intent = Intent(this.activity?.applicationContext, QuizActivity::class.java)
                    intent.putExtra("quizId", quizId)
                    startActivity(intent)
                }
                .setNegativeButton("No") { _, _ -> }
        val dialog = builder.create()
        dialog.show()
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