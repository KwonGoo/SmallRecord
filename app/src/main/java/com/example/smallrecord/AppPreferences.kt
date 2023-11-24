package com.example.smallrecord

import android.content.Context
import android.content.SharedPreferences

class AppPreferences(context: Context, private val webSocketManager: WebSocketManager) {


    private val sharedPreferences = context.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)

    //유저의 회원값 저장
    fun saveUserCredentials(loginId: String, name: String, birthDate: String) {
        val editor = sharedPreferences.edit()
        editor.putString("loginId", loginId)
        editor.putString("name", name)
        editor.putString("birthDate", birthDate)
        editor.apply()
    }

    //유저의 회원값 삭제
    fun clearUserCredentials() {
        val editor = sharedPreferences.edit()
        editor.remove("loginId")
        editor.remove("name")
        editor.remove("birthDate")
        editor.apply()
    }

    // 유저의 아이디 가져오기
    fun getLoginId(): String? {
        return sharedPreferences.getString("loginId", null)
    }

    // 유저의 이름 가져오기
    fun getName(): String? {
        return sharedPreferences.getString("name", null)
    }

    // 유저의 생년월일 가져오기
    fun getBirthDate(): String? {
        return sharedPreferences.getString("birthDate", null)
    }

    fun getUserInfo(): Triple<String?, String?, String?> {
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
    }

    fun isLoggedIn(): Boolean {
        val loginId = getLoginId()
        return !loginId.isNullOrEmpty()
    }
}