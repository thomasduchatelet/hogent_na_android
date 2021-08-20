package com.example.tequilapp.about

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tequilapp.network.CocktailApi
import com.example.tequilapp.network.CocktailResponse
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AboutViewModel() : ViewModel() {

    private val _response = MutableLiveData<String>()

    val response: LiveData<String>
        get() = _response

    private val _error = MutableLiveData<Boolean>()
    val error : LiveData<Boolean>
        get() = _error


    init {
        getTequilaDescription()
    }

    private fun getTequilaDescription() {
        viewModelScope.launch {
            try {
                var result = CocktailApi.retrofitService.getProperties()
                    _response.value = result?.ingredients?.first()?.strDescription
                    _error.value = false
            } catch (e: Exception) {
                Log.println(Log.ERROR,"network",e.message.toString())
                _error.value = true
            }
        }
    }
}


