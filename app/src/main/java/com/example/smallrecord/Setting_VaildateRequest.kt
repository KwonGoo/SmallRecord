package com.example.smallrecord

import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest

class Setting_VaildateRequest (UserEmail: String, listener: Response.Listener<String>) : StringRequest(
    Request.Method.POST,
    URL,
    listener,
    null
) {

    companion object {
        private const val URL = "http://10.0.2.2/Uservalidate.php"
    }

    private val parameters: MutableMap<String, String> = HashMap()

    init {
        parameters["userEmail"] = UserEmail
    }

    override fun getParams(): Map<String, String> {
        return parameters
    }
}