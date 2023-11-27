package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.Date
import com.example.smallrecord.R
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put
import okhttp3.*
import java.io.IOException
import okhttp3.MediaType.Companion.toMediaType


class CommunityWrite : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_write)

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

        diaryB.setOnClickListener {
            val intent = Intent(this, DiaryPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val settingB = findViewById<ImageButton>(R.id.settingButton)

        settingB.setOnClickListener {
            val intent = Intent(this, SettingPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#FDAE84")
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        val backButton = findViewById<Button>(R.id.backButton)
        backButton.setOnClickListener {
            val intent = Intent(this, CommunityPage::class.java)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val contentEditText = findViewById<EditText>(R.id.contentEditText)
        val completeButton = findViewById<Button>(R.id.completeButton)
        val webSocketManager = WebSocketManager()

        completeButton.setOnClickListener {
            // 입력한 제목과 내용 가져오기
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // 현재 날짜와 시간 가져오기 (원하는 형식으로 포맷팅은 필요에 따라 수정)
            val currentDate = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            val currentTime = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())

            val jsonBody = buildJsonObject {
                put("title", title)
                put("content", content)
                put("date", currentDate)
                put("time", currentTime)
            }

            // OkHttp를 사용하여 서버에 데이터 전송
            val client = OkHttpClient.Builder()
                .build()

            val request = Request.Builder()
                .url("http://10.210.8.129:9999/api/community/postup")
                .post(
                    RequestBody.create(
                        "application/json".toMediaType(),
                        jsonBody.toString()
                    )
                )
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // 전송 실패 처리
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    // 전송 성공 처리
                    if (response.isSuccessful) {
                        // 서버 응답이 성공인 경우
                        runOnUiThread {
                            // 현재 액티비티 종료
                            setResult(RESULT_OK, intent)
                            val intent = Intent(this@CommunityWrite, CommunityPage::class.java)
                            startActivity(intent)
                            overridePendingTransition(0, 0)
                        }
                    } else {
                        println(response)
                    }
                }
            })
        }
    }
}