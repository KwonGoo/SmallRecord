package com.example.smallrecord

import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.widget.*

class CommunityPost : AppCompatActivity() {
    private lateinit var titleTextView: TextView
    private lateinit var contentTextView: TextView
    private lateinit var backButton: Button
    private lateinit var editText: EditText
    private lateinit var listView: ListView
    private lateinit var commentList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community_post) // CommunityPost 액티비티의 레이아웃 파일 설정

        val titleTextView = findViewById<TextView>(R.id.titleTextView)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)
        val backButton = findViewById<Button>(R.id.backButton)
        editText = findViewById(R.id.editTextText) //댓글입력
        listView = findViewById(R.id.listView) //댓글용 리스트뷰

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }
        val intent = intent
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        titleTextView.text = title
        contentTextView.text = content

        backButton.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }
        // 댓글 목록 초기화
        commentList = ArrayList()

        // 어댑터 설정
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, commentList)
        listView.adapter = adapter

        // "댓글 입력" 버튼 클릭 이벤트 처리
        val commentButton = findViewById<Button>(R.id.commentButton)
        commentButton.setOnClickListener {
            val comment = editText.text.toString()
            overridePendingTransition(0, 0)
            if (comment.isNotEmpty()) {
                // 댓글 목록에 추가
                commentList.add(comment)
                adapter.notifyDataSetChanged()
                editText.text.clear() // 입력한 내용 지우기
            }
        }
    }
}
