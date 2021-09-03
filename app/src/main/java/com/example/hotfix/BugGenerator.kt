package com.example.hotfix

import android.util.Log

class BugGenerator {
    fun tryToMakeBug() {
        val anyString = "abcd"
        Log.d("HotFixApp", (anyString.length + 1).toString())
    }
}