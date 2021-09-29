package com.gapo.richtext.ext

import android.text.SpannableStringBuilder
import com.gapo.richtext.RichText

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

@DslMarker
internal annotation class RichTextInlineDsl

@JvmSynthetic
@RichTextInlineDsl
inline fun createRichText(crossinline block: RichText.Builder.() -> Unit): RichText =
    RichText.Builder().apply(block).build()
