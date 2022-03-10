package com.example.carfaxtech

data class HomeFeed(val listings: List<Car>)

data class Car(
    val dealer: Dealer,
    val images: Photo,
    val vin: String,
    val year: Int,
    val make: String,
    val model: String,
    val currentPrice: Int,
    val exteriorColor: String,
    val interiorColor: String,
    val engine: String,
    val drivetype: String,
    val transmission: String,
    val bodytype: String,
    val fuel: String)

data class Dealer(val phone:String, val city: String, val state: String)
data class Photo(val firstPhoto:ImageUrl)
data class ImageUrl(val large: String)
data class Channel (val name: String, val profileImageUrl : String)