package com.gapo.richtext

import android.text.Spannable
import android.util.Log
import androidx.core.text.toSpannable
import com.gapo.richtext.spanner.RichTextSpanner
import com.gapo.richtext.spanner.seemore.RichTextSeeMoreSpanner
import com.gapo.richtext.spanner.seemore.RichTextSeeMoreType

/**
 * @author kienht
 * @since 26/09/2021
 */
data class RichText constructor(
    val original: CharSequence,
    val spannable: Spannable,
    val seeMoreSpannable: Spannable? = null
) {

    val isEmpty: Boolean
        get() = original.isEmpty()

    val hasSeeMore: Boolean
        get() = seeMoreSpannable != null

    class Builder {

        private var text: CharSequence = ""

        private var spanners = mutableListOf<RichTextSpanner>()

        private var seeMoreType: RichTextSeeMoreType? = null

        fun setText(text: CharSequence) = apply { this.text = text }

        fun addSpanner(spanner: RichTextSpanner) = apply { this.spanners.add(spanner) }

        fun addSpanner(index: Int, spanner: RichTextSpanner) =
            apply { this.spanners.add(index, spanner) }

        fun setSeeMoreType(seeMoreType: RichTextSeeMoreType) =
            apply { this.seeMoreType = seeMoreType }

        fun build(): RichText {
            if (text.isEmpty()) return RichText(text, text.toSpannable(), null)
            var spannable = text.toSpannable()

            spanners.forEach {
                try {
                    spannable = it.span(spannable)
                } catch (e: Exception) {
                    Log.e("RichText", "build: ", e)
                }
            }

            val seeMoreType = this.seeMoreType
            var shortSpannable: Spannable? = null
            if (seeMoreType != null) {
                shortSpannable = try {
                    RichTextSeeMoreSpanner(seeMoreType).span(spannable)
                } catch (e: Exception) {
                    Log.e("RichText", "build: ", e)
                    null
                }
            }

            return RichText(text, spannable, shortSpannable)
        }
    }
}