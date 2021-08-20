package com.example.tequilapp.barlist

import android.app.Application
import androidx.lifecycle.*
import com.example.tequilapp.database.Bar
import com.example.tequilapp.database.TequilaDatabaseDao
import kotlinx.coroutines.*

class BarListViewModel(val database: TequilaDatabaseDao, application: Application) : AndroidViewModel(application) {
    private lateinit var  _bars: LiveData<List<Bar>>
    val bars: LiveData<List<Bar>>
        get() = _bars

    private val _sortOption = MutableLiveData<SortOptions>()
    val sortOption: LiveData<SortOptions>
        get() = _sortOption

    private val _navigateToBar = MutableLiveData<Long>()
    val navigateToBar : LiveData<Long>
        get() = _navigateToBar

    init {
        getAllBars()
    }
    fun onBarClicked(barId: Long) {
        _navigateToBar.value = barId
    }

    fun onBarNavigated(){
        _navigateToBar.value = null
    }

    fun setSortOption(option: SortOptions) {
        _sortOption.value = option
    }

    private fun getAllBars() {
        viewModelScope.launch {
            _bars = database.getAll()
        }
    }
}
