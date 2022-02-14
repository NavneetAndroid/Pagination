package com.example.retrofiturl.model

import com.google.gson.annotations.SerializedName

class User (
    val email:String,
    @SerializedName("first_name")
    val first_name:String


        )