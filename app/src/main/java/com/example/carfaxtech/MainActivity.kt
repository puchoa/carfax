package com.example.carfaxtech

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*

import android.Manifest
import android.app.Activity
import android.view.View
import android.widget.Toast
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers

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
                return
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.CALL_PHONE), REQUEST_CODE)
            }
        }
        val path = this.filesDir.absolutePath

        Log.d(Tag,"Attempting to Fetch JSON")
        CarFaxRepo().getCarJson(path).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d(Tag,"Successfully Fetched JSON")
                loading.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE

                recyclerView_main.adapter = MainAdapter(it)
        },{
                Log.d("appStart","Failed to execute request")

                try{
                    Log.d(Tag,"Trying to get database from file")
                    val file = File("$path/database").readText()
                    val gson = GsonBuilder().create()
                    val homefeed = gson.fromJson(file, HomeFeed::class.java)
                    Log.d(Tag,"Pulled database from file")

                    Toast.makeText(applicationContext,getString(R.string.No_Internet),Toast.LENGTH_SHORT).show()

                    runOnUiThread {
                        loadingProgressBar.visibility = View.GONE
                        recyclerView_main.adapter = MainAdapter(homefeed)
                    }
                } catch (exception: Exception){
                    Log.d(Tag,"${exception}")
                    runOnUiThread {
                        loadingProgressBar.visibility = View.GONE
                        loading.text = getString(R.string.No_load)
                    }
                }
        })
    }
}