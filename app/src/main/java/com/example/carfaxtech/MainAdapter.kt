package com.example.carfaxtech

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.car_row.view.*

class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    override fun getItemCount(): Int {
        return homeFeed.listings.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.car_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {

        val car = homeFeed.listings.get(position)

        holder.view.title?.text = Functions.convertCarTitle(car.year, car.make, car.model)
        holder.view.price?.text = Functions.convertPrice(car.currentPrice)
        holder.view.mileage?.text = Functions.convertMileage(car.mileage)
        holder.view.location?.text = Functions.convertLocation(car.dealer.city, car.dealer.state)


        Picasso.get().load(car.images.firstPhoto.large).placeholder(R.drawable.carfax).into(holder.view.imageView_car_thumbnail)

        holder.carInfo = car
    }
}

class CustomViewHolder(val view: View, var carInfo: Car? = null): RecyclerView.ViewHolder(view) {

    companion object{
        val CAR_INFO_KEY = "CAR_INFO"
    }

    init{
        view.setOnClickListener {

            val caraDataJson = Gson().toJson(carInfo)

            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra(CAR_INFO_KEY, caraDataJson)
            view.context.startActivity(intent)
        }

        view.btnCallDealer.setOnClickListener {
            Functions.makeCall(view.context, carInfo?.dealer?.phone)
        }
    }
}














