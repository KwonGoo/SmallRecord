package com.example.smallrecord

import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.widget.*
import com.example.smallrecord.R
import kotlinx.coroutines.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

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


        setContentView(R.layout.community_post)

        titleTextView = findViewById(R.id.titleTextView)
        contentTextView = findViewById(R.id.contentTextView)
        backButton = findViewById(R.id.backButton)
        editText = findViewById(R.id.editTextText)
        listView = findViewById(R.id.listView)

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

        val intent = intent
        val postId = intent.getIntExtra("id", 1).toString()


        backButton.setOnClickListener {
            finish()
            overridePendingTransition(0, 0)
        }

        // 댓글 목록 초기화
        commentList = ArrayList()

        // 어댑터 설정
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, commentList)
        listView.adapter = adapter

        // 게시글 세부 정보 가져오기
        fetchPostDetails(postId)

        // 댓글 가져오기
        fetchComments(postId)

        // "댓글 입력" 버튼 클릭 이벤트 처리
        val commentButton = findViewById<Button>(R.id.commentButton)
        commentButton.setOnClickListener {
            val comment = editText.text.toString()
            if (comment.isNotEmpty()) {
                // 새로운 댓글 서버로 전송
                sendComment(postId, comment)
            }
        }
    }

    private fun fetchPostDetails(id: String?) {
        // 게시글 세부 정보를 가져오는 네트워크 작업
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response = getPostDetailsFromServer(id)
                withContext(Dispatchers.Main) {
                    if (response != null) {
                        val resultCode = response.getString("resultCode")

                        if (resultCode == "SUCCESS") {
                            try {
                                val data = response.getJSONObject("data")
                                val modifiedId = data.getInt("id")

                                val modifiedResponse = getPostDetailsFromServer(modifiedId.toString())

                                if (modifiedResponse != null) {
                                    val modifiedResultCode = modifiedResponse.getString("resultCode")

                                    if (modifiedResultCode == "SUCCESS") {
                                        val modifiedData = modifiedResponse.getJSONObject("data")

                                        // Check if 'content' exists in the JSON response
                                        if (modifiedData.has("content")) {
                                            contentTextView.text = modifiedData.getString("content")
                                        } else {
                                            // Handle the case where 'content' is not present in the response
                                            // You can set some default text or handle it according to your requirements
                                            contentTextView.text = "내용이 없습니다"
                                        }
                                    } else {
                                        // Handle the case where the server response is not successful
                                        val modifiedMessage = modifiedResponse.getString("message")
                                        Toast.makeText(applicationContext, modifiedMessage, Toast.LENGTH_SHORT).show()
                                    }
                                } else {
                                    // 처리 실패시 예외 처리 코드 작성
                                    Toast.makeText(applicationContext, "게시글의 정보를 불러오는데 실패했습니다", Toast.LENGTH_SHORT)
                                        .show()
                                }
                            } catch (e: Exception) {
                                e.printStackTrace()
                            }
                        } else {
                            // Handle the case where the server response is not successful
                            val message = response.getString("message")
                            Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // 처리 실패시 예외 처리 코드 작성
                        Toast.makeText(applicationContext, "게시글의 정보를 불러오는데 실패했습니다", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private suspend fun getPostDetailsFromServer(id: String?): JSONObject? {
        val url = "http://172.16.37.219:9999/api/community/post/$id"
        return try {
            val response = withContext(Dispatchers.IO) {
                OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            }

            // 추가: 서버 응답 코드와 응답 본문 로그 출력
            println("Server response code: ${response.code}")
            val responseBodyString = response.body?.string() // 변수에 저장
            println("Server response body: $responseBodyString")

            if (!response.isSuccessful) {
                println("Failed to fetch post details. Response code: ${response.code}")
                println("Response body: $responseBodyString")
                return null
            }

            if (responseBodyString.isNullOrBlank()) {
                println("Failed to parse JSON. Response body is null or blank.")
                return null
            }

            try {
                val jsonResponse = JSONObject(responseBodyString)

                withContext(Dispatchers.Main) {
                    val resultCode = jsonResponse.getString("resultCode")

                    if (resultCode == "SUCCESS") {
                        val data = jsonResponse.getJSONObject("data")
                        val modifiedId = data.getInt("id")

                        titleTextView.text = data.getString("title")

                        // Check if 'content' exists in the JSON response
                        if (data.has("content")) {
                            contentTextView.text = data.getString("content")
                        } else {
                            // Handle the case where 'content' is not present in the response
                            // You can set some default text or handle it according to your requirements
                            contentTextView.text = "내용이 없습니다"
                        }
                    } else {
                        // Handle the case where the server response is not successful
                        val message = jsonResponse.getString("message")
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }
                }

                // 수정된 응답 반환
                return jsonResponse
            } catch (e: Exception) {
                println("Failed to parse JSON. Exception: ${e.message}")
                null
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }


    private fun fetchComments(id: String?) {
        // 게시글에 대한 댓글 목록을 가져오는 네트워크 작업
        val url = "http://172.16.37.219:9999/api/community/comment/$id"
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val comments = getCommentsFromServer(id)
                withContext(Dispatchers.Main) {
                    commentList.addAll(comments.map { it.comment })
                    adapter.notifyDataSetChanged()

                    // 댓글 목록을 contentTextView에 추가
                    val commentsText = comments.joinToString("\n") { "\"${it.comment}\"" }
                    contentTextView.text = "댓글 목록:\n$commentsText"
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private suspend fun getCommentsFromServer(id: String?): List<Comment> {
        val url = "http://172.16.37.219:9999/api/community/comment/$id"
        return try {
            val response = withContext(Dispatchers.IO) {
                OkHttpClient().newCall(Request.Builder().url(url).build()).execute()
            }
            if (response.isSuccessful) {
                val jsonArray = JSONArray(response.body?.string())
                val comments = mutableListOf<Comment>()
                for (i in 0 until jsonArray.length()) {
                    val commentObject = jsonArray.getJSONObject(i)
                    val comment = Comment(commentObject.getInt("id"), commentObject.getString("comment"))
                    comments.add(comment)
                }
                comments
            } else {
                emptyList()
            }
        } catch (e: IOException) {
            emptyList()
        }
    }

    data class Comment(val id: Int, val comment: String)


    private fun sendComment(id: String?, comment: String) {
        // 새로운 댓글을 서버로 전송하는 네트워크 작업
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val success = sendCommentToServer(id, comment)
                withContext(Dispatchers.Main) {
                    if (success) {
                        // 성공적으로 댓글을 서버에 전송한 경우
                        commentList.add(comment)
                        adapter.notifyDataSetChanged()
                        editText.text.clear()
                    } else {
                        // 댓글 전송 실패시 예외 처리 코드 작성
                        Toast.makeText(applicationContext, "댓글 작성에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private suspend fun sendCommentToServer(id: String?, comment: String): Boolean {
        val url = "http://172.16.37.219:9999/api/community/post/$id"
        val json = JSONObject().apply {
            put("comment", comment)
        }
        return try {
            val response = withContext(Dispatchers.IO) {
                OkHttpClient().newCall(Request.Builder().url(url)
                    .post(RequestBody.create("application/json".toMediaTypeOrNull()!!, json.toString()))
                    .build()).execute()
            }

            // 추가: 서버 응답 코드와 응답 본문 로그 출력
            println("Server response code: ${response.code}")
            val responseBodyString = response.body?.string() // 변수에 저장
            println("Server response body: $responseBodyString")

            response.isSuccessful
        } catch (e: IOException) {
            e.printStackTrace()
            false
        }
    }

}

