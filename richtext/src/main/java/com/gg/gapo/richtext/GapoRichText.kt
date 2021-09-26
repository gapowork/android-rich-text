package com.gg.gapo.richtext

import android.text.Spannable
import androidx.core.text.toSpannable
import com.gg.gapo.richtext.spanner.GapoRichTextSpanner

/**
 * @author kienht
 * @since 26/09/2021
 */
data class GapoRichText constructor(
    val original: String,
    val spannable: Spannable
) {

    class Builder {

        private var original: String = ""

        private var spanners = mutableListOf<GapoRichTextSpanner>()

        fun setOriginal(original: String) = apply { this.original = original }

        fun addSpanner(spanner: GapoRichTextSpanner) = apply { this.spanners.add(spanner) }

        fun addSpanner(index: Int, spanner: GapoRichTextSpanner) =
            apply { this.spanners.add(index, spanner) }

        fun build(): GapoRichText {
            var spannable = original.toSpannable()

            spanners.forEach {
                spannable = it.span(spannable)
            }

            return GapoRichText(this.original, spannable)
        }
    }
}