package com.example.carfaxtech

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import android.Manifest
import android.app.Activity
import java.io.File

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE: Int = 0
    private val Tag = "appStart"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        val actionBar = supportActionBar
        actionBar!!.title = "CARFAX"

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this as Activity,
                    Manifest.permission.CALL_PHONE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE)
            }
        }
        val path = this.filesDir.absolutePath
        fetchJsonAndSetupDB(path)
    }

    fun fetchJsonAndSetupDB(path: String){
        Log.d(Tag,"Attempting to Fetch JSON")

        val url = "https://carfax-for-consumers.firebaseio.com/assignment.json"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback{

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                if (!body.isNullOrEmpty()){
                    val gson = GsonBuilder().create()
                    val homefeed = gson.fromJson(body, HomeFeed::class.java)
                    Log.d(Tag,"Successfully Fetched JSON")

                    val file = File("$path/database").printWriter()
                    file.use { out -> out.println(body) }
                    Log.d(Tag,"Database has been written to file")

                    runOnUiThread {
                        loading.text = ""
                        recyclerView_main.adapter = MainAdapter(homefeed)
                    }
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                Log.d("appStart","Failed to execute request")

                try{
                    Log.d(Tag,"Trying to get database from file")
                    val file = File("$path/database").readText()
                    val gson = GsonBuilder().create()
                    val homefeed = gson.fromJson(file, HomeFeed::class.java)
                    Log.d(Tag,"Pulled database from file")

                    runOnUiThread {
                        loading.text = ""
                        recyclerView_main.adapter = MainAdapter(homefeed)
                    }
                } catch (exception: Exception){
                    Log.d(Tag,"${exception}")
                    runOnUiThread {
                        loading.text = getString(R.string.No_load)
                    }
                }
            }
        })
    }
}