package com.example.tequilapp.barlist

import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.tequilapp.database.Bar

@BindingAdapter("priceFormatted")
fun TextView.setPriceFormatted(item: Bar?){
    item?.let{
        text = "€ " + item.price
    }
}

@BindingAdapter("barRating")
fun RatingBar.setBarRating(item: Bar?){
    item?.let {
        rating = (item.tequilaQuality / item.numberOfRatings).toFloat()
    }
}

@BindingAdapter("barName")
fun TextView.setBarName(item: Bar?){
    item?.let {
        text = item.name
    }
}

@BindingAdapter("ratedBy")
fun TextView.setRatedBy(item: Bar?){
    item?.let {
        text = "Rated by ${item.numberOfRatings} users"
    }
}

