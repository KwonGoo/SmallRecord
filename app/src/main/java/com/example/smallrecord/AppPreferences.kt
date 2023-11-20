package com.example.smallrecord

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    //유저의 회원값 저장
    fun saveUserCredentials(loginId: String) {
        val editor = sharedPreferences.edit()
        editor.putString("loginId", loginId)
        editor.apply()
    }

    //유저의 회원값 삭제
    fun clearUserCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("loginId")
        editor.apply()
    }

    fun getUserId(): String? {
        return sharedPreferences.getString("loginId", null)
    }

    fun isLoggedIn(): Boolean {
        val loginId = getUserId()
        return !loginId.isNullOrEmpty()
    }
}