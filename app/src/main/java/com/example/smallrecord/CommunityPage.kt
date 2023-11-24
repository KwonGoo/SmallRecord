package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings.Global
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.*
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.ListView
import io.ktor.http.cio.*
import kotlinx.coroutines.*
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class CommunityPage : AppCompatActivity() {
    private val WRITE_REQUEST_CODE = 1
    private lateinit var listView: ListView
    private lateinit var itemList: MutableList<String>
    private lateinit var adapter: ArrayAdapter<String>
    private lateinit var useJson:JSONObject

    @OptIn(DelicateCoroutinesApi::class)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.community)

        itemList = mutableListOf()
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, itemList)

        listView = findViewById(R.id.listView)
        listView.adapter = adapter


        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.statusBarColor = android.graphics.Color.parseColor("#FDAE84")
        }

        // 글쓰기 버튼
        val writeButton = findViewById<ImageButton>(R.id.writeButton)
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


        GlobalScope.launch {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url("http://172.16.37.219:9999/api/community/list")
                .build()


            val response = client.newCall(request).execute()

            if (response.isSuccessful) {
                val responseBody = response.body?.string()


                val responseArray = JSONArray(responseBody)

                // 리스트를 초기화하고 서버에서 받은 각 항목의 title을 itemList에 추가
                runOnUiThread {
                    itemList.clear()

                    for (i in 0 until responseArray.length()) {
                        val postObject = responseArray.getJSONObject(i)
                        val title = postObject.getString("title")
                        itemList.add(title)
                    }

                    adapter.notifyDataSetChanged()
                }

            }

        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                // 선택한 게시글의 ID 가져오기
                val selectedPostId = getPostIdFromPosition(position)

                println(selectedPostId)

                // CommunityPost 액티비티로 이동
                val intent = Intent(this, CommunityPost::class.java)
                intent.putExtra("id", selectedPostId)
                startActivity(intent)
                overridePendingTransition(0, 0)
            }
    }

    private fun getPostIdFromPosition(position: Int): Int {
        // 선택한 포지션의 게시글 ID를 가져오는 로직 추가
        // 여기서는 가정으로 position을 그대로 사용
        return position +1
    }






    object body {

    }
}




    // onActivityResult 함수를 CommunityPage 클래스 내부로 이동
    /*   override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
    }*/



