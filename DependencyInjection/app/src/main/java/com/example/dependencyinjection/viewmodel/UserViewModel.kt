package com.example.dependencyinjection.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dependencyinjection.data.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {
    fun getUserData(): String = userRepository.getUserData()
}