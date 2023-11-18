package com.example.smallrecord

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class WebSocketManager {

    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    fun sendPostToServer(serverSendPost: String, message: JsonObject) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
                socket = Socket("192.168.35.7", 9999)

                // 서버와 통신을 위한 입출력 스트림 생성
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                writer = PrintWriter(socket!!.getOutputStream(), true)




                // POST 요청의 JSON 데이터 생성
                val jsonBody = buildJsonObject {
                    put("loginId", "asd123")
                    put("password", "password1!")
                    put("name", "권구협")
                    put("birthDate", "2001-01-01")
                    put("gender", "WOMAN")
                    put("email", "asd123@gmail.com")
                }


                // POST 요청 메시지 구성
                val postMessage = buildPostMessage(serverSendPost,message)
                println(postMessage)

                // 서버로 POST 요청 전송
                writer!!.println(postMessage)


                // 서버 응답 받기
                val response = reader!!.readLine()
                println("서버 응답: $response")

            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                // 리소스 정리
                try {
                    reader?.close()
                    writer?.close()
                    socket?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
        }
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

}
