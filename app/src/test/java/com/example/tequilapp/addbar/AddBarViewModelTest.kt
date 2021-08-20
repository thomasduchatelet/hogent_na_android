package com.example.tequilapp.addbar

import android.app.Application
import com.example.tequilapp.database.TequilaDatabaseDao
import junit.framework.TestCase
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations


class AddBarViewModelTest {
        @Mock
        private lateinit var mockApplication: Application
        @Mock
        private lateinit var mockDao: TequilaDatabaseDao

        @Before
        fun setUp() {
            MockitoAnnotations.initMocks(this)
        }

        @Test
        fun addBar_WithoutName_ThrowsIllegalArgumentException() {
            val viewModel = AddBarViewModel(mockDao, mockApplication)
            Assert.assertThrows(IllegalArgumentException::class.java, {viewModel.addBar("","0.0",0.0)})
        }

        @Test
        fun addBar_WithBadPrice_ThrowsIllegalArgumentException() {
            val viewModel = AddBarViewModel(mockDao, mockApplication)
            Assert.assertThrows(IllegalArgumentException::class.java, {viewModel.addBar("test","a",0.0)})
        }

    }