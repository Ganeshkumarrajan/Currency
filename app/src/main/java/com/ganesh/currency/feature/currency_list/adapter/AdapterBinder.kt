package com.ganesh.currency.feature.currency_list.adapter

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ganesh.currency.BuildConfig
import com.squareup.picasso.Picasso
import java.util.*

/**
 * Created by ganeshkumar raja on 4/10/20.
 */

const val imageType = ".png"
const val deImageNameAndType = "de.png"
const val deName = "EUR"

@BindingAdapter("currency")
fun setCurrencyValue(edTxt: EditText, currency: Float) {
    val newValue = "%.2f".format(currency)

    if (edTxt.text.toString() != newValue) {
        edTxt.setText(newValue)
    }
}

@BindingAdapter("image_url")
fun assignImage(imageView: ImageView, imageName: String) {

    var currencyURL = BuildConfig.IMG_URL + imageName.substring(
        0,
        2
    ).toLowerCase(Locale.ENGLISH) + imageType

    if (imageName.contentEquals(deName)) {
        currencyURL = BuildConfig.IMG_URL + deImageNameAndType
    }

    currencyURL.let {
        Picasso.with(imageView.context).load(currencyURL).into(imageView)
    }
}

@BindingAdapter("error_message")
fun assignErrorMessage(textView: TextView, message: String?) {
    if (!message.isNullOrEmpty()) textView.text = message
    else textView.text = ""
}

@BindingAdapter("visible")
fun viewVisibility(view: View, visibleStatus: Boolean?) {
    visibleStatus?.let {
        if (visibleStatus) view.visibility = View.VISIBLE
        else view.visibility = View.GONE
    }
}
