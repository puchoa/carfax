package com.example.carfaxtech

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.video_row.view.*
import java.text.NumberFormat


class MainAdapter(val homeFeed: HomeFeed): RecyclerView.Adapter<CustomViewHolder>() {

    // numberOfItems
    override fun getItemCount(): Int {
        return homeFeed.listings.count()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        // how do we even create a view
        val layoutInflater = LayoutInflater.from(parent?.context)
        val cellForRow = layoutInflater.inflate(R.layout.video_row, parent, false)
        return CustomViewHolder(cellForRow)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val car = homeFeed.listings.get(position)
        holder?.view?.title?.text = "${car.year} ${car.make} ${car.model}"
        holder?.view?.price?.text = "$ ${NumberFormat.getIntegerInstance().format(car.currentPrice)}"
        holder?.view?.location.text = "${car.dealer.city}, ${car.dealer.state}"

        val thumbnaulImageView = holder?.view?.imageView_car_thumbnail
        Picasso.get().load(car.images.firstPhoto.large).into(thumbnaulImageView)

        holder?.car = car
    }
}


class CustomViewHolder(val view: View, var car: Car? = null): RecyclerView.ViewHolder(view) {

    init{
        view.setOnClickListener {
            val intent = Intent(view.context, DetailActivity::class.java)
            intent.putExtra("title", "${car?.year} ${car?.make} ${car?.model}")
            view.context.startActivity(intent)

        }
    }
}














