package com.gg.gapo.richtext

import android.text.SpannableStringBuilder
import android.widget.TextView

/**
 * @author kienht
 * @since 26/09/2021
 */
fun CharSequence.toSpannableStringBuilder(): SpannableStringBuilder {
    return if (this is SpannableStringBuilder) {
        this
    } else {
        SpannableStringBuilder(this)
    }
}

fun TextView.setOnClickNotSpanListener(onClick: () -> Unit) {
    setOnClickListener {
        if (selectionStart == -1 && selectionEnd == -1) {
            onClick()
        }
    }
}