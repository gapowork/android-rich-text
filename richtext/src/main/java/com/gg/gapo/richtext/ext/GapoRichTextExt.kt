package com.gg.gapo.richtext.ext

import android.text.SpannableStringBuilder

/**
 * @author kienht
 * @since 26/09/2021
 */
internal fun CharSequence.toSpannableStringBuilder(): SpannableStringBuilder {
    return if (this is SpannableStringBuilder) {
        this
    } else {
        SpannableStringBuilder(this)
    }
}