package com.example.tequilapp.addbar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.tequilapp.database.Bar
import com.example.tequilapp.database.TequilaDatabaseDao
import kotlinx.coroutines.*

class AddBarViewModel (val database: TequilaDatabaseDao, application: Application) : AndroidViewModel(application) {

    @Throws(IllegalArgumentException::class)
    fun addBar(name: String, price: String, rating: Double) {
        if(name == "") throw IllegalArgumentException("Please add name")
        if (price.toDoubleOrNull() == null) throw IllegalArgumentException("Please add price")
        var bar = Bar(name = name, price = price.toDouble(), numberOfRatings = 1, tequilaQuality = rating)
        uiScope.launch {
            insert(bar)
        }
    }

    private suspend fun insert(newBar: Bar){
        withContext(Dispatchers.IO){
            database.insert(newBar)
        }
    }

    private var viewModelJob = Job()

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
}