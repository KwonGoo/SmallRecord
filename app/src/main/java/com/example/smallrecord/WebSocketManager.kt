package com.example.smallrecord


import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class WebSocketManager {

    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null

    fun connectToServer(message: String) {
        GlobalScope.launch(Dispatchers.IO) {
            try {
                // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
                socket = Socket("192.168.35.7", 1234) // localhost 대신에 10.0.2.2를 사용하면 에뮬레이터에서 호스트로 접근 가능

                // 서버와 통신을 위한 입출력 스트림 생성
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                writer = PrintWriter(socket!!.getOutputStream(), true)

                // 서버로 메시지 전송
                val message = message
                writer!!.println(message)

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
}