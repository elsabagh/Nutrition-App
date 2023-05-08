package com.example.nutritionapp.util

import android.view.View

fun istVisible(visible: Boolean): Int {
    return if(visible) View.VISIBLE else View.GONE
}