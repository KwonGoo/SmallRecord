package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket

class SettingPage : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    private var jsonResponse: JSONObject? = null
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting)
        var accessToken:String ?= ""


        val loginResponse = intent.getStringExtra("loginResponse")
        if(loginResponse != null) {
            val loginResponseJson = JSONObject(loginResponse)
            val token = loginResponseJson.getString("accessToken")
            accessToken = "Bearer $token"

            println("여긴가 $accessToken")
        }
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#D9E8E3") // #RRGGBB는 16진수 색상 코드입니다.
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val foodB = findViewById<ImageButton>(R.id.foodButton)

        foodB.setOnClickListener {
            val intent = Intent(this, FoodPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val babyB = findViewById<ImageButton>(R.id.babyButton)

        babyB.setOnClickListener {
            val intent = Intent(this, BabyPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val diaryB = findViewById<ImageButton>(R.id.diaryButton)

        diaryB.setOnClickListener{
            val intent = Intent(this, DiaryPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val communityB = findViewById<ImageButton>(R.id.communityButton)

        communityB.setOnClickListener {
            val intent = Intent(this, CommunityPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val MyPage = findViewById<Button>(R.id.mypage)

        MyPage.setOnClickListener {

            GlobalScope.launch {

                // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
                socket = Socket("10.210.8.129", 9999)

                // 서버와 통신을 위한 입출력 스트림 생성
                reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                writer = PrintWriter(socket!!.getOutputStream(), true)


                // POST 요청 메시지 구성
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://10.210.8.129:9999/api/member/info")
                    .addHeader("Authorization", accessToken.toString())
                    .build()

                var nnn = client.newCall(request).execute()
                val qqq = nnn.body?.string()
                println(qqq) // {"resultCode":"SUCCESS","data":{"id":1,"loginId":"aaa111","name":"아아아아","birthDate":"20010101","gender":"남","email":"assd@gmail.com"},"message":"정상 처리 되었습니다."}

                withContext(Dispatchers.Main){

                    val intent = Intent(this@SettingPage, Setting_MyPage::class.java)
                    intent.putExtra("accessToken",qqq.toString())
                    startActivity(intent)
                    overridePendingTransition(0, 0)




                }
            }



        }

        val LoginPage = findViewById<Button>(R.id.loginpage)

        LoginPage.setOnClickListener {
            val intent = Intent(this, Setting_LoginPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val ASwitch = findViewById<Switch>(R.id.ASwitch)

        // SharedPreferences를 초기화
        sharedPreferences = getSharedPreferences("SwitchSettings", Context.MODE_PRIVATE)

        // 스위치의 초기 상태를 설정
        val switchState = sharedPreferences.getBoolean("switch_state", false)
        ASwitch.isChecked = switchState

        // 스위치의 상태가 변경될 때 SharedPreferences에 저장
        ASwitch.setOnCheckedChangeListener { _, isChecked ->
            val editor = sharedPreferences.edit()
            editor.putBoolean("switch_state", isChecked)
            editor.apply()
        }

        val appPreferences = AppPreferences(applicationContext)
        if (appPreferences.isLoggedIn()) {
            // 로그인된 상태
            LoginPage.visibility = View.GONE;
            MyPage.visibility = View.VISIBLE;
        } else {
            // 로그아웃된 상태
            LoginPage.visibility = View.VISIBLE;
            MyPage.visibility = View.GONE;
        }

    }
}

