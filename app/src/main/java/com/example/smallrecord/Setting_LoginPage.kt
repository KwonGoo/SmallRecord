package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.json.JsonObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders


class Setting_LoginPage : AppCompatActivity() {

    private var dialog: AlertDialog? = null
    private lateinit var loginId: EditText
    private lateinit var loginPassword: EditText
    private lateinit var loginButton: Button
    private lateinit var joinButton: Button
    private lateinit var mypageButton: Button
    private lateinit var loginPage: Button
    private var jsonResponse: JSONObject? = null
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    lateinit var token:String



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_login)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        token = ""

        loginId = findViewById(R.id.login_id)
        loginPassword = findViewById(R.id.login_password)

        joinButton = findViewById(R.id.join_page)
        joinButton.setOnClickListener {
            val intent = Intent(this@Setting_LoginPage, Setting_RegisterPage::class.java)
            startActivity(intent)
        }

        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener {
            val userId = loginId.text.toString()
            val userPassword = loginPassword.text.toString()

            if (userId.isEmpty() || userPassword.isEmpty()) {
                // 이메일 또는 비밀번호 필드 중 하나라도 비웠을 때
                val builder = AlertDialog.Builder(this)
                builder.setMessage("모두 입력해주세요")
                    .setNegativeButton("확인", null)
                    .create()
                    .show()
            }





                    val webSocketManager = WebSocketManager()



                    val sendMessage = buildJsonObject {
                        put("loginId",userId)
                        put("password",userPassword)
                    }


                        token = sendLoginMessage(sendMessage)
                        println(token)

                    val appPreferences = AppPreferences(applicationContext)
                    appPreferences.saveUserCredentials(userId)
                    Toast.makeText(applicationContext, "${userId}님 환영합니다.", Toast.LENGTH_SHORT).show()



                        var intent = Intent(this, SettingPage::class.java)
                        startActivity(intent)











        }

    }


    override fun onStop() {
        super.onStop()
        dialog?.dismiss()
        dialog = null
    }

    fun getToken(jsonObject: JSONObject):String{
        return jsonObject.getJSONObject("data").getString("accessToken")
    }

    fun sendLoginMessage(message: JsonObject):String {

        var messageString = message.toString()


        GlobalScope.launch(Dispatchers.IO) {


            // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
            socket = Socket("192.168.0.34", 9999)

            // 서버와 통신을 위한 입출력 스트림 생성
            reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            writer = PrintWriter(socket!!.getOutputStream(), true)


            // POST 요청 메시지 구성
            val postMessage = """
                          POST /api/member/login HTTP/1.1
                          Host: 192.168.0.34:9999
                          Content-Type: application/json
                          Content-Length: ${messageString.toByteArray(Charsets.UTF_8).size}

                          $messageString
                          """.trimIndent()
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
                    token = jsonResponse!!.getJSONObject("data").getString("accessToken")
                    println(jsonResponse!!.getJSONObject("data").getString("accessToken"))
                    break
                }

            }


        }
        return token
    }

    fun getInfo(message: HttpEntity<String>) {

        var messageString = message.toString()


        GlobalScope.launch(Dispatchers.IO) {


            // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
            socket = Socket("192.168.0.34", 9999)

            // 서버와 통신을 위한 입출력 스트림 생성
            reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
            writer = PrintWriter(socket!!.getOutputStream(), true)


            // POST 요청 메시지 구성
            val postMessage = """
                          GET /api/member/info HTTP/1.1
                          Host: 192.168.0.34:9999
                          Content-Type: application/json
                          Content-Length: ${messageString.toByteArray(Charsets.UTF_8).size}

                          $messageString
                          """.trimIndent()
            println(postMessage)

            // 서버로 POST 요청 전송
            writer!!.println(postMessage)

            // 서버 응답 받기
            val response: String
            var line: String? = reader!!.readLine()


            while (true) {

                line = reader!!.readLine()
                println("$line \n")

                }

            }


        }


}



