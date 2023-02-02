package com.udacity.shoestore.presentation.binding.adapters

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("shoeSize")
fun bindShoeSize(textView: TextView, size: Double) {
    textView.text = "$size"
}