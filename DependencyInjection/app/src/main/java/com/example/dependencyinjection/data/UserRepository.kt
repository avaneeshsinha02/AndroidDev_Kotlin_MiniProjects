package com.example.dependencyinjection.data

import javax.inject.Inject

class UserRepository @Inject constructor() {
    fun getUserData(): String {
        return "User data from repository"
    }
}