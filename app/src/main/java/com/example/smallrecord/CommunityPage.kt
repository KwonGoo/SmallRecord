package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView

class CommunityPage : AppCompatActivity() {
    private val WRITE_REQUEST_CODE = 1
    private lateinit var listView: ListView
    private lateinit var itemList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        // 글쓰기버튼
        val writeButton = findViewById<Button>(R.id.writeButton)
        writeButton.setOnClickListener {
            // CommunityWrite 액티비티로 이동
            val intent = Intent(this, CommunityWrite::class.java)
            startActivityForResult(intent, WRITE_REQUEST_CODE)
            overridePendingTransition(0, 0)
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

        listView = findViewById(R.id.listView)
        itemList = mutableListOf("게시글 1", "게시글 2", "게시글 3")

        // 어댑터 설정
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)
        listView.adapter = adapter

        // 리스트뷰 항목 클릭 이벤트 처리
        listView.setOnItemClickListener { _, _, position, _ ->
            val selectedTitle = itemList[position] // 선택된 게시글 제목 가져오기
            val selectedContent = "게시글 내용" // 게시글 내용을 가져와야함
            val intent = Intent(this, CommunityPost::class.java)
            intent.putExtra("title", selectedTitle)
            intent.putExtra("content", selectedContent)
            startActivity(intent)
            overridePendingTransition(0, 0)
        }
    }

    // onActivityResult 함수를 CommunityPage 클래스 내부에 추가
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == WRITE_REQUEST_CODE && resultCode == RESULT_OK) {
            val title = data?.getStringExtra("title")
            val content = data?.getStringExtra("content")

            if (title != null) {
                itemList.add(title)
                adapter.notifyDataSetChanged()
            }
            overridePendingTransition(0, 0)
        }
    }
}
