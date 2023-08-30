package com.kadirkuruca.newsapp.repository

import com.health.vistacan.api.ApiService
import com.health.vistacan.splash.model.ToDoModel
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepository @Inject constructor(
    private val apiService: ApiService,
) {
    suspend fun getToDo(email:String, password:String): Response<ToDoModel> {
        return apiService.getToDo(email,password)
    }

}