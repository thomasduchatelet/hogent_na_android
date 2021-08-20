package com.example.tequilapp.ratebar

import android.app.Application
import com.example.tequilapp.addbar.AddBarViewModel
import com.example.tequilapp.database.TequilaDatabaseDao
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RateBarViewModelTest {

    @Mock
    private lateinit var mockApplication: Application
    @Mock
    private lateinit var mockDao: TequilaDatabaseDao

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun addRating_WithRatingSix_ThrowsIllegalArgumentException() {
        val viewModel = RateBarViewModel(1, mockDao, mockApplication)
        Assert.assertThrows(IllegalArgumentException::class.java, {viewModel.addRating(6.0)})
    }
}