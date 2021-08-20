package com.example.tequilapp.barlist

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tequilapp.database.TequilaDatabaseDao
import java.lang.IllegalArgumentException

class BarListViewModelFactory (private val dataSource: TequilaDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BarListViewModel::class.java)){
            return BarListViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Illegal ViewModel class")
    }
}