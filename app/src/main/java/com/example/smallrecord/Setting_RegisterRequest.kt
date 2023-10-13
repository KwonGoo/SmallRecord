package com.example.smallrecord

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class Setting_RegisterRequest (
    userName: String,
    userEmail: String,
    userPwd: String,
    listener: Response.Listener<String>
) : StringRequest(Request.Method.POST, URL, listener, null) {

    companion object {
        // 서버 URL 설정(php 파일 연동)
        private const val URL = "http://10.0.2.2/userregister.php"
    }

    private val map: Map<String, String>

    init {
        map = mapOf(
            "userEmail" to userEmail,
            "userName" to userName,
            "userPassword" to userPwd
        )
    }

    override fun getParams(): Map<String, String> {
        return map
    }
}