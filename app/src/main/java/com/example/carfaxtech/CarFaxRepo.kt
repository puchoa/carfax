package com.example.carfaxtech

import android.util.Log
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Single
import java.io.File
import java.net.URL

class CarFaxRepo {

    private val Tag = "appStart"

    fun getCarJson(path: String): Single<HomeFeed> {
       return Single.create<HomeFeed> {
            val json = URL("https://carfax-for-consumers.firebaseio.com/assignment.json").readText()
           saveJsonFile(json, path)
           val carData = Gson().fromJson(json, HomeFeed::class.java)
           it.onSuccess(carData)
        }
    }

    fun saveJsonFile (json: String, path: String){
        val file = File("$path/database").printWriter()
        file.use { out -> out.println(json) }
        Log.d(Tag,"Database has been written to file")
    }
}