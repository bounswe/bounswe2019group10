package com.example.yallp_android.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.SearchView
import androidx.fragment.app.Fragment
import com.example.yallp_android.R

class WritingListFragment : Fragment() {

    private lateinit var searchView: SearchView
    private lateinit var listView: ListView
   // private  var list =  ArrayList<Card>()
//    private var adapter: CardAdapter? = null


    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_writing_list, container, false)
        listView = root.findViewById(R.id.listView) as ListView
        searchView = root.findViewById(R.id.searchView) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if(query.length>=3) submitQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(newText.length>=3) submitQuery(newText)
                return false
            }
        })
        searchView.setQuery("saron", true)

        return root
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
        fun newInstance(): WritingListFragment {
            return WritingListFragment()
        }
    }

}