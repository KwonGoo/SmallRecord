package com.example.smallrecord

import com.example.smallrecord.foodreadypage.Food_ReadyPage
import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.zxing.BarcodeFormat
import com.google.zxing.ResultPoint
import com.google.zxing.client.android.BeepManager
import com.journeyapps.barcodescanner.BarcodeCallback
import com.journeyapps.barcodescanner.BarcodeResult
import com.journeyapps.barcodescanner.DecoratedBarcodeView
import com.journeyapps.barcodescanner.DefaultDecoderFactory
import kotlinx.coroutines.*
import kotlinx.serialization.json.*
import java.util.*
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket


class BarcodeCameraPage : AppCompatActivity() {
    private var beepManager: BeepManager? = null
    var lastText: String? = null
    private lateinit var context: Context
    private lateinit var barcodeScanner: DecoratedBarcodeView
    private val DELAY = 10000 // 1 second
    private var socket: Socket? = null
    private var reader: BufferedReader? = null
    private var writer: PrintWriter? = null
    var foodInfo:JSONObject ?= null
    var foodName:String = ""
    lateinit var makeName:String
    lateinit var calorie:String
    lateinit var protein:String
    lateinit var fat:String
    lateinit var sugar:String
    lateinit var sodium:String
    lateinit var capacity:String
    lateinit var carbohydrate:String
    lateinit var cholesterol:String




    private val callback: BarcodeCallback = object : BarcodeCallback {
        private var lastTimestamp: Long = 0

        override fun barcodeResult(result: BarcodeResult) {
            if (result.text != null) {
                if (System.currentTimeMillis() - lastTimestamp < DELAY) {
                    // Too soon after the last barcode - ignore.
                    return
                }

                val sendMessage = buildJsonObject {
                    put("barcodeNum",result.text)
                }


                lastText = result.text
               // var aaa = sendBarcode(sendMessage)



              //  println("1 $aaa")



                    var messageString = """{"barcodeNum" : "$lastText"}"""
                    var jsonResponse: JSONObject?
                    var foodRealInfo: JSONObject? = null


                    GlobalScope.launch {

                        // 서버의 IP 주소와 포트 번호를 지정하여 소켓 생성
                        socket = Socket("192.168.35.7", 9999)

                        // 서버와 통신을 위한 입출력 스트림 생성
                        reader = BufferedReader(InputStreamReader(socket!!.getInputStream()))
                        writer = PrintWriter(socket!!.getOutputStream(), true)


                        // POST 요청 메시지 구성
                        val postMessage = """
                          GET /api/food/barcode HTTP/1.1
                          Host: 192.168.35.7:9999
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
                                foodRealInfo = jsonResponse?.getJSONObject("data")


                                withContext(Dispatchers.Main) {
                                    // food_readyPage로 데이터 전달
                                    val intent = Intent(this@BarcodeCameraPage, Food_ReadyPage::class.java)
                                    intent.putExtra("foodInfo", foodRealInfo.toString())


                                    startActivity(intent)
                                }

                                break
                            } else {

                            }

                        }
                    }





            }
        }

        override fun possibleResultPoints(resultPoints: List<ResultPoint>) {}
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.barcode_camera)


        context = this

        val actionBar: ActionBar? = supportActionBar

        if (actionBar != null) {
            actionBar.hide()
        }

        checkAndRequestPermissions()



        val formats: Collection<BarcodeFormat> =
            listOf(BarcodeFormat.CODABAR, BarcodeFormat.CODE_128)

        barcodeScanner = findViewById(R.id.barcode_scanner)

        barcodeScanner.barcodeView.decoderFactory = DefaultDecoderFactory(formats)
        barcodeScanner.initializeFromIntent(intent)
        beepManager = BeepManager(this)
        beepManager!!.isVibrateEnabled = true
        barcodeScanner.decodeContinuous(callback)


    }



    /**
     * 권한을 확인하고 권한이 부여되어 있지 않다면 권한을 요청한다.
     * @return 필요한 권한이 모두 부여되었다면 true, 그렇지 않다면 false
     */
    private fun checkAndRequestPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA
        )
        val listPermissionsNeeded: MutableList<String> = ArrayList()


        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(this, permission)
                !== PackageManager.PERMISSION_GRANTED
            ) {
                listPermissionsNeeded.add(permission)
            }
        }



        return true
    }

    override fun onResume() {
        super.onResume()
        barcodeScanner!!.resume()
    }

    override fun onPause() {
        super.onPause()
        barcodeScanner!!.pause()
    }

    fun pause(view: View?) {
        barcodeScanner!!.pause()
    }

    fun resume(view: View?) {
        barcodeScanner!!.resume()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        return barcodeScanner!!.onKeyDown(keyCode, event) || super.onKeyDown(keyCode, event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }



}


