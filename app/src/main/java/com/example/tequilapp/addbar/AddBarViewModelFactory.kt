package com.example.tequilapp.addbar

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tequilapp.barlist.BarListViewModel
import com.example.tequilapp.database.TequilaDatabaseDao
import java.lang.IllegalArgumentException

class AddBarViewModelFactory (private val dataSource: TequilaDatabaseDao, private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(AddBarViewModel::class.java)){
            return AddBarViewModel(dataSource, application) as T
        }
        throw IllegalArgumentException("Illegal ViewModel class")
    }
}