package com.example.hotfix

import android.util.Log

class BugGenerator {
    /**
     *  Fix Point
     */
    fun tryToMakeBug() {
        val anyString = "abcd"
        Log.d("HotFixApp", (anyString.length + 1).toString())
    }

    /**
     * Bug Point
     */
//    fun tryToMakeBug() {
//        val anyString: String? = null
//        Log.d("HotFixApp", (anyString!!.length + 1).toString())
//    }
}