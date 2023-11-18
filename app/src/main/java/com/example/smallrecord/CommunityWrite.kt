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
import com.example.smallrecord.R
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

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
            window.statusBarColor = android.graphics.Color.parseColor("#FDAE84") // #RRGGBB는 16진수 색상 코드입니다.
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

            // 데이터를 Intent에 추가하여 현재 액티비티 종료
            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("content", content)

            /*val sendMessage = buildJsonObject {
                put("messageType","communityWrite")
                put("title",title)
                put("content",content)
            }*/

            val post = "POST /api/member/signup"

            val sendMessage = buildJsonObject {
                put("loginId","asd123")
                put("password","password1!")
                put("name","이종석")
                put("birthDate","2001-01-01")
                put("gender","WOMAN")
                put("email","asd123@gmail.com")
            }
            //webSocketManager.connectToServer(sendMessage.toString()+"\n")
            webSocketManager.sendPostToServer(post, sendMessage)

            setResult(RESULT_OK, intent)
            finish()
            overridePendingTransition(0, 0)
        }
    }
}
