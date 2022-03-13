package com.example.carfaxtech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.display_car.view.*

class DetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        recyclerView_main.layoutManager = LinearLayoutManager(this)
        loading.text = ""

        supportActionBar?.hide()

        val carInfo = intent.getStringExtra(CustomViewHolder.CAR_INFO_KEY)
        val carData: Car? = Gson().fromJson(carInfo, Car::class.java)

        recyclerView_main.adapter = DetailAdapter(carData)
        loadingProgressBar.visibility = View.GONE
    }

    private class DetailAdapter(val carData: Car? = null): RecyclerView.Adapter<DetailViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val customView = layoutInflater.inflate(R.layout.display_car, parent, false)
            return DetailViewHolder(customView)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            holder.customView.carTitle?.text = Functions.convertCarTitle(carData?.year, carData?.make, carData?.model)
            holder.customView.carPrice?.text = Functions.convertPrice(carData?.currentPrice)
            holder.customView.carMileage?.text = Functions.convertMileage(carData?.mileage)
            holder.customView.carLocation?.text = Functions.convertLocation(carData?.dealer?.city, carData?.dealer?.state)
            holder.customView.carExtColor?.text = carData?.exteriorColor
            holder.customView.carIntColor?.text = carData?.interiorColor
            holder.customView.carDrivetype?.text = carData?.interiorColor
            holder.customView.carTransmission?.text = carData?.transmission
            holder.customView.carBodyStyle?.text = carData?.bodytype
            holder.customView.carEngine?.text = carData?.engine
            holder.customView.carFuel?.text = carData?.fuel

            Picasso.get().load(carData?.images?.firstPhoto?.large).placeholder(R.drawable.carfax).into(holder.customView.car_thumbnail)

            holder.telNumber = carData?.dealer?.phone
        }

        override fun getItemCount(): Int {
            return 1
        }
    }

    private class DetailViewHolder(val customView: View, var telNumber: String? = null): RecyclerView.ViewHolder(customView){
        init {
            customView.btnCall.setOnClickListener {
                Functions.makeCall(customView.context, telNumber)
            }
        }
    }
}


