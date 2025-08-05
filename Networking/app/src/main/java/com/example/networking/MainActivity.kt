package com.example.networking

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.networking.api.ApiService
import com.example.networking.api.RetrofitClient
import com.example.networking.models.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class MainActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        apiService = RetrofitClient.instance.create(ApiService::class.java)

        fetchUsers()
    }

    private fun fetchUsers() {
        val call = apiService.getUsers()

        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.let {
                        for (user in it) {
                            Log.d("MainActivity", "User: ${user.name}, Email: ${user.email}")
                        }
                    }
                } else {
                    when (response.code()) {
                        400 -> Log.e("MainActivity", "Error: Bad Request")
                        401 -> Log.e("MainActivity", "Error: Unauthorized")
                        404 -> Log.e("MainActivity", "Error: Not Found")
                        500 -> Log.e("MainActivity", "Error: Internal Server Error")
                        else -> Log.e("MainActivity", "Unknown Error: ${response.code()}")
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                if (t is IOException) {
                    Log.e("MainActivity", "Network Failure: ${t.message}")
                } else {
                    Log.e("MainActivity", "Conversion Error: ${t.message}")
                }
            }
        })
    }
}