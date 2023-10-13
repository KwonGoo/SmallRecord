package com.example.smallrecord

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import java.lang.reflect.Method

class Setting_LoginRequest(
    userEmail: String,
    userPassword: String,
    listener: Response.Listener<String>
) : StringRequest(Method.POST, URL, listener, null) {

    companion object {
        // 서버 URL 설정(php 파일 연동)
        private const val URL = "http://10.0.2.2/userinfo.php"
    }

    private val map: Map<String, String>

    init {
        map = mapOf(
            "userEmail" to userEmail,
            "userPassword" to userPassword
        )
    }

    @Throws(AuthFailureError::class)
    override fun getParams(): Map<String, String> {
        return map
    }
}