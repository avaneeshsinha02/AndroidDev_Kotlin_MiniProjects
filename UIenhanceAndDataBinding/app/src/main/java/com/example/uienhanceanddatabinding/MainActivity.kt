package com.example.uienhanceanddatabinding

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uienhanceanddatabinding.adapter.UserAdapter
import com.example.uienhanceanddatabinding.databinding.ActivityMainBinding
import com.example.uienhanceanddatabinding.model.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val users = listOf(
            User("Avaneesh", 23),
            User("Dwight", 30),
            User("Michael", 31),
            User("Jim", 29)
        )

        val adapter = UserAdapter(users)
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
    }
}
