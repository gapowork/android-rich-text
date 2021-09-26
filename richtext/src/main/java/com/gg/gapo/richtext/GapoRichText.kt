package com.gg.gapo.richtext

import android.text.Spannable
import android.util.Log
import androidx.core.text.toSpannable
import com.gg.gapo.richtext.spanner.GapoRichTextSpanner
import com.gg.gapo.richtext.spanner.seemore.GapoRichTextSeeMoreSpanner
import com.gg.gapo.richtext.spanner.seemore.GapoRichTextSeeMoreType

/**
 * @author kienht
 * @since 26/09/2021
 */
data class GapoRichText constructor(
    val original: String,
    val spannable: Spannable,
    val shortSpannable: Spannable?
) {

    class Builder {

        private var original: String = ""

        private var spanners = mutableListOf<GapoRichTextSpanner>()

        private var seeMoreType: GapoRichTextSeeMoreType? = null

        fun setOriginal(original: String) = apply { this.original = original }

        fun addSpanner(spanner: GapoRichTextSpanner) = apply { this.spanners.add(spanner) }

        fun addSpanner(index: Int, spanner: GapoRichTextSpanner) =
            apply { this.spanners.add(index, spanner) }

        fun setSeeMoreType(seeMoreType: GapoRichTextSeeMoreType) =
            apply { this.seeMoreType = seeMoreType }

        fun build(): GapoRichText {
            var spannable = original.toSpannable()

            spanners.forEach {
                spannable = it.span(spannable)
            }

            val seeMoreType = this.seeMoreType
            var shortSpannable: Spannable? = null
            if (seeMoreType != null) {
                shortSpannable = try {
                    GapoRichTextSeeMoreSpanner(seeMoreType).span(spannable)
                } catch (e: Exception) {
                    Log.e("GapoRichText", "build: ", e)
                    null
                }
            }

            return GapoRichText(this.original, spannable, shortSpannable)
        }
    }
}