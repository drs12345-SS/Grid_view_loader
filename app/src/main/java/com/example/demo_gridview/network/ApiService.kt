package com.example.demo_gridview.network

import com.example.demo_gridview.models.Photo
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("photos")
    fun getPhotos(): Call<List<Photo>>
}
