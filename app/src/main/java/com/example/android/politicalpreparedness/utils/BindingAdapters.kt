package com.example.android.politicalpreparedness.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@BindingAdapter("dateStr")
fun TextView.bindDate(date: Date?) {
    val dateFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.getDefault())
    text = date?.let {
        dateFormat.format(it)
    }
}

object BindingAdapters {
    @BindingAdapter("android:fadeVisible")
    @JvmStatic
    fun setFadeVisible(view: View, visible: Boolean? = true) {
        if (view.tag == null) {
            view.tag = true
            view.visibility = if (visible == true) View.VISIBLE else View.GONE
        } else {
            view.animate().cancel()
            if (visible == true) {
                if (view.visibility == View.GONE) {
                    view.fadeIn()
                }
            } else {
                if (view.visibility == View.VISIBLE) {
                    view.fadeOut()
                }
            }
        }
    }
}
