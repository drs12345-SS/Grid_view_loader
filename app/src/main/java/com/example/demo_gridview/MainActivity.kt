package com.example.demo_gridview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.demo_gridview.databinding.ActivityMainBinding
import com.example.demo_gridview.network.ApiService
import com.example.demo_gridview.network.RetrofitClient
import android.util.Log
import com.example.demo_gridview.models.Photo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        initView()
        setContentView(binding.root)


    }

    private fun initView() {

        binding.recyclerView.layoutManager = GridLayoutManager(this, 3)

        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        apiService.getPhotos().enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                if (response.isSuccessful) {
                    val photos = response.body() ?: emptyList()
                    val imageUrls = photos.map { it.url }
                    binding.recyclerView.adapter = ImageAdapter(imageUrls)
                } else {
                    Log.e("MainActivity", "Failed to fetch photos: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                Log.e("MainActivity", "Failed to fetch photos", t)
            }
        })
    }
}