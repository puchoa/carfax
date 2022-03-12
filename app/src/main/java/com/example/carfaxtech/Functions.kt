package com.example.carfaxtech

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import java.text.NumberFormat

class Functions {
    companion object {

        val notAvailable = "is not available"

        fun convertCarTitle(year: Int? = null, make: String? = null, model: String? = null): String{
            if(year != null && !make.isNullOrBlank() && !model.isNullOrEmpty()){
                return "$year $make $model"
            }

            return "Title $notAvailable"
        }

        fun convertMileage(miles: Int? = null): String{
            val mileage = miles?.toDouble()?.toBigDecimal()

            if(mileage != null ){
                if(mileage >= 1000000.toBigDecimal()){
                    return "${(mileage / 1000000.toBigDecimal()).stripTrailingZeros()}m mi"
                } else if(mileage >= 1000.toBigDecimal()){
                    return "${(mileage / 1000.toBigDecimal()).stripTrailingZeros()}k mi"
                }
                return "$mileage mi"
            }
            return "0 mi"
            }

        fun convertPrice(price: Int? = null): String{
            if(price != null){
                return  "$ ${NumberFormat.getIntegerInstance().format(price)}"
            }
            return "Price $notAvailable"
        }

        fun convertLocation(city: String? = null, state: String? = null): String {
            if(!city.isNullOrEmpty() && !state.isNullOrEmpty()){
                return "$city, $state"
            }
            return "Location $notAvailable"
        }

        fun makeCall(context: Context, phone: String? = null){
            Log.d("Dailing Phone", "Call button pressed - calling dealer")

            if(!phone.isNullOrEmpty()){
                val intent = Intent(Intent.ACTION_CALL)
                intent.setData(Uri.parse("tel:$phone"))
                context.startActivity(intent)
            } else{
                Log.d("Dailing Phone", "unable to make call - phone is either empty or null")
            }
        }
    }
}

