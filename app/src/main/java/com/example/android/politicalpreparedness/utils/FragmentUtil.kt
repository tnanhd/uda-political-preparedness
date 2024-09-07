package com.example.android.politicalpreparedness.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun Fragment.setDisplayUpButton(enabled: Boolean) {
    if (activity is AppCompatActivity) {
        (activity as AppCompatActivity)
            .supportActionBar?.setDisplayHomeAsUpEnabled(enabled)
    }
}