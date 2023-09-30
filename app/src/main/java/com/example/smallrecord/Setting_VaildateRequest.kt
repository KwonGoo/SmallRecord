package com.example.smallrecord

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class Setting_VaildateRequest(
    userEmail: String,
    listener: Response.Listener<String>,
    errorListener: Response.ErrorListener  // 추가: 에러 리스너를 받을 수 있도록 변경
) : StringRequest(Request.Method.POST, URL, listener, errorListener) { // 에러 리스너 추가

    companion object {
        private const val URL = "http://10.0.2.2/uservalidate.php"
    }

    private val parameters: MutableMap<String, String> = HashMap()

    init {
        parameters["userEmail"] = userEmail
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }
}
