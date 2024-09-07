package com.example.android.politicalpreparedness.representative.adapter

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Spinner
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.bumptech.glide.Glide
import com.example.android.politicalpreparedness.R

@BindingAdapter("profileImage")
fun fetchImage(view: ImageView, src: String?) {
    if (src != null) {
        val uri = src.toUri().buildUpon().scheme("https").build()
        Glide.with(view.context)
            .load(uri)
            .circleCrop()
            .placeholder(R.drawable.loading_img)
            .error(R.drawable.ic_profile)
            .into(view)
    } else {
        view.setImageResource(R.drawable.ic_profile)
    }
}

@BindingAdapter("stateValue")
fun Spinner.setNewValue(value: String?) {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    val position = when (adapter.getItem(0)) {
        is String -> adapter.getPosition(value)
        else -> this.selectedItemPosition
    }
    if (position >= 0) {
        setSelection(position)
    }
}

@InverseBindingAdapter(attribute = "stateValue", event = "stateValueAttrChange")
fun Spinner.getValue(): String? {
    val adapter = toTypedAdapter<String>(this.adapter as ArrayAdapter<*>)
    return when (selectedItem) {
        is String -> adapter.getItem(this.selectedItemPosition) as String
        else -> null
    }
}

@BindingAdapter("app:stateValueAttrChange")
fun Spinner.doOnItemSelected(listener: InverseBindingListener?) {
    onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            listener?.onChange()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }
    }
}

inline fun <reified T> toTypedAdapter(adapter: ArrayAdapter<*>): ArrayAdapter<T> {
    return adapter as ArrayAdapter<T>
}
