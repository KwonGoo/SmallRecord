package com.example.smallrecord

import com.google.gson.JsonParser
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlinx.serialization.json.JsonObject

import io.ktor.http.*
import io.ktor.http.ContentType.Application.Json
import io.ktor.http.cio.websocket.*
import kotlinx.coroutines.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.json.JSONObject
import org.json.JSONTokener
import java.lang.StringBuilder


class WebSocketManager {

    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    private var jsonResponse: JSONObject ?= null
    @OptIn(DelicateCoroutinesApi::class)
    fun sendPostToServer(serverSendPost: String, message: JsonObject): JSONObject? {


        GlobalScope.launch(Dispatchers.IO) {


            // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
            socket = Socket("192.168.35.7", 9999)

            // 서버와 통신을 위한 입출력 스트림 생성
            reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            writer = PrintWriter(socket!!.getOutputStream(), true)


            // POST 요청 메시지 구성
            val postMessage = buildPostMessage(serverSendPost, message)
            println(postMessage)

            // 서버로 POST 요청 전송
            writer!!.println(postMessage)

            // 서버 응답 받기
            val response: String
            var line: String? = reader!!.readLine()


            while (true) {

                line = reader!!.readLine()

                if (line.startsWith("{")) {
                    response = line
                    jsonResponse = JSONObject(response)
                    println(jsonResponse!!.getJSONObject("data").getString("accessToken"))
                    break
                }

            }


        }
        return jsonResponse
    }

    private fun buildPostMessage(serverSendPost: String,jsonBody: JsonObject): String {

        val jsonBodyString = jsonBody.toString()


        // HTTP POST 요청 메시지 구성

        val postMessage = """
                          $serverSendPost HTTP/1.1
                          Host: 192.168.35.7:9999
                          Content-Type: application/json
                          Content-Length: ${jsonBodyString.toByteArray(Charsets.UTF_8).size}

                          $jsonBodyString
                          """.trimIndent()





        return postMessage
    }



    fun extractJsonFromBody(log: StringBuilder): String? {
        val startToken = "{"
        val endToken = "}"

        val startIndex = log.indexOf(startToken)
        val endIndex = log.lastIndexOf(endToken)

        return if (startIndex != -1 && endIndex != -1 && endIndex > startIndex) {
            log.substring(startIndex, endIndex + 1)
        } else {
            null
        }
    }



    @OptIn(DelicateCoroutinesApi::class)
    fun getUserInfoFromServer(userId: String): JSONObject? {
        val requestMessage = buildUserInfoRequest(userId)
        writer?.println(requestMessage)

        // 서버 응답 받기
        val response: String? = reader?.readLine()

        // 예를 들어, 서버에서 JSON 형식으로 사용자 정보를 제공하는 경우
        return response?.let { extractJsonFromBody(StringBuilder(it)) }?.let { JSONObject(it) }
    }

    private fun buildUserInfoRequest(name: String): String {
        return """
            GET_USER_INFO HTTP/1.1
            Host: 192.168.35.7:9999
            Content-Type: application/json
            Content-Length: ${name.toByteArray(Charsets.UTF_8).size}

            {"name": "$name"}
        """.trimIndent()
    }

}


