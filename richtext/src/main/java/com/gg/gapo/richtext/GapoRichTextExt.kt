package com.gg.gapo.richtext

import android.text.SpannableStringBuilder

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