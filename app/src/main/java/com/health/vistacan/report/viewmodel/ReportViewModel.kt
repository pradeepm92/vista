package com.health.vistacan.report.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.health.vistacan.report.ReportEvent
import com.health.vistacan.report.ReportEvent.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReportViewModel
@Inject
constructor(
) : ViewModel() {
    val recipes: MutableState<List<Int>> = mutableStateOf(ArrayList())

    val query = mutableStateOf("")

    val loading = mutableStateOf(false)


    init {
            onTriggerEvent(ReportEvent.NewSearchEvent)
    }

    fun onTriggerEvent(event: ReportEvent){
        viewModelScope.launch {
            try {
                when(event){
                    is NewSearchEvent -> {
                        newSearch()
                    }
                    else -> {

                    }
                }
            }catch (e: Exception){
                Log.e("Viewmodel", "launchJob: Exception: ${e}, ${e.cause}")
                e.printStackTrace()
            }
            finally {
                Log.d("Viewmodel", "launchJob: finally called.")
            }
        }
    }

    private suspend fun newSearch() {
        loading.value = true


        delay(2000)
        val data:ArrayList<Int> = ArrayList()
      data.add(40)
      data.add(20)
      data.add(30)
      data.add(10)

        data.add(20)
        data.add(40)
        data.add(60)
        data.add(100)
        recipes.value = data
        loading.value = false
    }

}
















