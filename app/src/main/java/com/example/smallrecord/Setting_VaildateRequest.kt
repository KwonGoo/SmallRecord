package com.example.smallrecord

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class Setting_VaildateRequest(
    userName: String,
    listener: Response.Listener<String>,
) : StringRequest(Request.Method.POST, URL, listener, null) { // 에러 리스너 추가

    companion object {
        private const val URL = "http://10.0.2.2/uservaildate.php"
    }

    private val parameters: MutableMap<String, String> = HashMap()

    init {
        parameters["userName"] = userName
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }
}
