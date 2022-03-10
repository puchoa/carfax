package com.example.carfaxtech

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.video_row.view.*

class DetailActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        val title = intent.getStringExtra("title")
        recyclerView_main.adapter = title?.let { DetailAdapter(it) }

    }

    private class DetailAdapter(val title: String): RecyclerView.Adapter<DetailViewHolder>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {


            val layoutInflater = LayoutInflater.from(parent?.context)
            val customView = layoutInflater.inflate(R.layout.display_car, parent, false)
            return DetailViewHolder(customView)
        }

        override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
            holder?.customView?.title?.text = title

        }

        override fun getItemCount(): Int {
            return 1
        }
    }

    private class DetailViewHolder(val customView: View): RecyclerView.ViewHolder(customView){

    }


}


