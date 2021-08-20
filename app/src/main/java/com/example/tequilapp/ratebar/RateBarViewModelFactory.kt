package com.example.tequilapp.ratebar

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tequilapp.addbar.AddBarViewModel
import com.example.tequilapp.database.TequilaDatabaseDao
import java.lang.IllegalArgumentException

class RateBarViewModelFactory (private val barKey: Long, private val dataSource: TequilaDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(RateBarViewModel::class.java)){
            return RateBarViewModel(barKey, dataSource, application) as T
        }
        throw IllegalArgumentException("Illegal ViewModel class")
    }
}
