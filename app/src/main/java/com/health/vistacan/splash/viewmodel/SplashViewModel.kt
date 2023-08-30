package com.health.vistacan.splash.viewmodel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.vistacan.api.ErrorParser
import com.health.vistacan.splash.model.ToDoModel
import com.health.vistacan.utils.NetworkUtil.Companion.hasInternetConnection
import com.health.vistacan.utils.Resource
import com.kadirkuruca.newsapp.repository.SplashRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val newsRepository: SplashRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {
    val todo: MutableLiveData<Resource<ToDoModel>> = MutableLiveData()

    init {
        getTodo()
    }

    fun getTodo() = viewModelScope.launch {
        safeTodoCall()
    }

    private suspend fun safeTodoCall(){
        todo.postValue(Resource.Loading())
        try{
            if(hasInternetConnection(context)){
                val response = newsRepository.getToDo("eve.holt@reqres.in","cityslicka")
                todo.postValue(handleBreakingNewsResponse(response))
            }
            else{
                todo.postValue(Resource.Error("No Internet Connection"))
            }
        }
        catch (ex : Exception){
            when(ex){
                is IOException -> todo.postValue(Resource.Error("Network Failure"))
                else -> todo.postValue(Resource.Error("Conversion Error"))
            }
        }
    }

    private fun handleBreakingNewsResponse(response: Response<ToDoModel>): Resource<ToDoModel> {
        if (response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }else if(response.errorBody()!=null){
            return Resource.Error(response.errorBody()?.let { ErrorParser(it) }!!.message)
        }
        return Resource.Error("Something went wrong")
        }
    }