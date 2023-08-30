package com.health.vistacan.api

import com.health.vistacan.splash.model.ToDoModel
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {

    @POST("api/login")
    @FormUrlEncoded
    suspend fun getToDo(@Field("email") email:String, @Field("password") password:String): Response<ToDoModel>

}