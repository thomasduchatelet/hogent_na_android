package com.example.tequilapp.ratebar

import android.app.Application
import androidx.lifecycle.*
import com.example.tequilapp.database.Bar
import com.example.tequilapp.database.TequilaDatabaseDao
import kotlinx.coroutines.*
import java.lang.IllegalArgumentException

class RateBarViewModel(private val barKey: Long = 0L, val database: TequilaDatabaseDao, application: Application) : AndroidViewModel(application) {
    private val bar = MediatorLiveData<Bar>()

    fun getBar() = bar
    fun addRating(rating: Double) {
        if(rating in 0.0..5.0){
            bar.value?.let {
                it.numberOfRatings++
                it.tequilaQuality += rating
                viewModelScope.launch {
                    update(it)
                }
            }
        } else{
            throw IllegalArgumentException("Illegal rating provided")
        }


    }

    private suspend fun update(newBar: Bar){
        withContext(Dispatchers.IO){
            database.update(newBar)
        }
    }

    init {
        bar.addSource(database.get(barKey), bar::setValue)
    }
}