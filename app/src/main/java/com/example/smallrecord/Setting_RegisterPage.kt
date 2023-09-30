package com.example.smallrecord

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.Volley
import org.json.JSONException
import org.json.JSONObject

class Setting_RegisterPage : AppCompatActivity() {

    private lateinit var joinEmail: EditText
    private lateinit var joinPassword: EditText
    private lateinit var joinName: EditText
    private lateinit var joinPwck: EditText
    private lateinit var joinButton: Button
    private lateinit var checkButton: Button
    private lateinit var previousButton: Button
    private var dialog: AlertDialog? = null
    private var validate = false

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setting_join)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.hide()
        }

        joinEmail = findViewById(R.id.join_email)
        joinPassword = findViewById(R.id.join_password)
        joinName = findViewById(R.id.join_name)
        joinPwck = findViewById(R.id.join_pwck)

        checkButton = findViewById(R.id.check_button)
        checkButton.setOnClickListener {
            val userName = joinName.text.toString()
            if (validate) {
                return@setOnClickListener // 검증 완료
            }

            if (userName.isEmpty()) {
                val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                dialog = builder.setMessage("아이디를 입력하세요.").setPositiveButton("확인", null).create()
                dialog?.show()
                return@setOnClickListener
            }

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonResponse = JSONObject(response)
                    val success = jsonResponse.getBoolean("success")
                    val colorGray = Color.parseColor("#808080")

                    if (success) {
                        val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                        dialog = builder.setMessage("사용할 수 있는 아이디입니다.").setPositiveButton("확인", null).create()
                        dialog?.show()
                        joinName.isEnabled = false // 아이디값 고정
                        validate = true // 검증 완료
                        checkButton.setBackgroundColor(colorGray)
                    } else {
                        val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                        dialog = builder.setMessage("이미 존재하는 아이디입니다.").setNegativeButton("확인", null).create()
                        dialog?.show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
            val validateRequest = Setting_VaildateRequest(userName, responseListener)
            val queue = Volley.newRequestQueue(this@Setting_RegisterPage)
            queue.add(validateRequest)
        }

        joinButton = findViewById(R.id.join_button)
        joinButton.setOnClickListener {
            val userEmail = joinEmail.text.toString()
            val userPwd = joinPassword.text.toString()
            val userName = joinName.text.toString()
            val passCk = joinPwck.text.toString()

            // 아이디 중복체크 했는지 확인
            if (!validate) {
                val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                dialog = builder.setMessage("중복된 아이디가 있는지 확인하세요.").setNegativeButton("확인", null).create()
                dialog?.show()
                return@setOnClickListener
            }

            // 한 칸이라도 입력 안했을 경우
            if (userEmail.isEmpty() || userPwd.isEmpty() || userName.isEmpty()) {
                val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                dialog = builder.setMessage("모두 입력해주세요.").setNegativeButton("확인", null).create()
                dialog?.show()
                return@setOnClickListener
            }

            val responseListener = Response.Listener<String> { response ->
                try {
                    val jsonObject = JSONObject(response)
                    val success = jsonObject.getBoolean("success")

                    // 회원가입 성공시
                    if (userPwd == passCk) {
                        if (success) {
                            Toast.makeText(
                                applicationContext,
                                "${userName}님 가입을 환영합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            val intent = Intent(this@Setting_RegisterPage, Setting_LoginPage::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                applicationContext,
                                "회원가입에 실패하였습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                            return@Listener
                        }
                    } else {
                        val builder = AlertDialog.Builder(this@Setting_RegisterPage)
                        dialog = builder.setMessage("비밀번호가 동일하지 않습니다.").setNegativeButton("확인", null).create()
                        dialog?.show()
                        return@Listener
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }

            // 서버로 Volley를 이용해서 요청
            val registerRequest = Setting_RegisterRequest(userEmail, userPwd, userName, responseListener)
            val queue = Volley.newRequestQueue(this@Setting_RegisterPage)
            queue.add(registerRequest)
        }

        previousButton = findViewById(R.id.delete) // 뒤로가기 버튼
        previousButton.setOnClickListener {
            finish()
        }
    }
}