package com.example.retrofiturl

import com.example.retrofiturl.model.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface Api {
    @GET("users")
    fun getUsers(
        @QueryMap parameters:HashMap<String,String>
    ):Call<UserResponse>
}

