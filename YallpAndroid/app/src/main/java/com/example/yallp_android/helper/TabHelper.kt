package com.example.yallp_android.helper

import android.os.Handler
import com.google.android.material.tabs.TabLayout

class TabHelper {

    companion object {
        val PROFILE_TAB_NUMBER = 0
        val LANGUAGE_TAB_NUMBER = 1
        val MESSAGE_TAB_NUMBER = 2

        fun scrollToSelectedTab(tabs: TabLayout, tabNumber: Int) {
            Handler().postDelayed(
                    { tabs.getTabAt(tabNumber)!!.select() }, 100)
        }
    }

}