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

    // 유저의 아이디 가져오기
    fun getLoginId(): String? {
        return sharedPreferences.getString("loginId", null)
    }

 /*   fun getUserInfo(): Triple<String?, String?, String?> {
        val loginId = getLoginId()
        val name = getName()
        val birthDate = getBirthDate()

        // 만약 하나라도 null이라면 서버에서 가져오고 저장
        if (loginId == null || name == null || birthDate == null) {
            val userId = getLoginId()
            if (userId != null) {
                val userInfo = webSocketManager.getUserInfoFromServer(userId)
                if (userInfo != null) {
                    // 서버에서 가져온 정보를 저장
                    saveUserCredentials(
                        userId,
                        userInfo.optString("name", ""),
                        userInfo.optString("birthDate", "")
                    )
                }
            }
        }

        return Triple(getLoginId(), getName(), getBirthDate())
    }*/

    fun isLoggedIn(): Boolean {
        val loginId = getLoginId()
        return !loginId.isNullOrEmpty()
    }
}