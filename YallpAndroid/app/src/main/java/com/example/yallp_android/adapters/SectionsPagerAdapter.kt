package com.example.yallp_android.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.yallp_android.R
import com.example.yallp_android.fragments.QuizListFragment
import com.example.yallp_android.fragments.WritingListFragment

private val TAB_TITLES = arrayOf(
    R.string.quiz_list_tab,
    R.string.writing_list_tab
)

class SectionsPagerAdapter(private val context: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        if(position==0) return QuizListFragment.newInstance()
        return WritingListFragment.newInstance()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}