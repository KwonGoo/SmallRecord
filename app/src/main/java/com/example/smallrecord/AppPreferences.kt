package com.example.smallrecord

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    //userEmail과 userName 값 저장
    fun saveUserCredentials(userEmail: String, userName: String) {
        val editor = sharedPreferences.edit()
        editor.putString("userEmail", userEmail)
        editor.putString("userName", userName)
        editor.apply()
    }

    //userEmail과 userName 값 삭제
    fun clearUserCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("userEmail")
        editor.remove("userName")
        editor.apply()
    }

    fun getUserEmail(): String? {
        return sharedPreferences.getString("userEmail", null)
    }

    fun getUserName(): String? {
        return sharedPreferences.getString("userName", null)
    }

    fun isLoggedIn(): Boolean {
        val userEmail = getUserEmail()
        val userName = getUserName()
        return !userEmail.isNullOrEmpty() && !userName.isNullOrEmpty()
    }
}