package com.example.smallrecord


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import com.example.smallrecord.R

class CommunityWrite : AppCompatActivity() {
    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_write)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        val titleEditText = findViewById<EditText>(R.id.titleEditText)
        val contentEditText = findViewById<EditText>(R.id.contentEditText)
        val completeButton = findViewById<Button>(R.id.completeButton)

        completeButton.setOnClickListener {
            // 입력한 제목과 내용 가져오기
            val title = titleEditText.text.toString()
            val content = contentEditText.text.toString()

            // 데이터를 Intent에 추가하여 현재 액티비티 종료
            val intent = Intent()
            intent.putExtra("title", title)
            intent.putExtra("content", content)
            setResult(RESULT_OK, intent)
            finish()
        }
    }
}
