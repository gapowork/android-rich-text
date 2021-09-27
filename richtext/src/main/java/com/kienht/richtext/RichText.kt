package com.kienht.richtext

import android.text.Spannable
import android.util.Log
import androidx.core.text.toSpannable
import com.kienht.richtext.spanner.RichTextSpanner
import com.kienht.richtext.spanner.seemore.RichTextSeeMoreSpanner
import com.kienht.richtext.spanner.seemore.RichTextSeeMoreType

/**
 * @author kienht
 * @since 26/09/2021
 */
data class RichText constructor(
    val original: String,
    val spannable: Spannable,
    val seeMoreSpannable: Spannable? = null
) {

    val isEmpty: Boolean
        get() = original.isEmpty()

    val hasSeeMore: Boolean
        get() = seeMoreSpannable != null

    override fun equals(other: Any?): Boolean {
        if (other !is RichText) return false
        return other.original == original
    }

    override fun hashCode(): Int {
        return original.hashCode()
    }

    class Builder {

        private var original: String = ""

        private var spanners = mutableListOf<RichTextSpanner>()

        private var seeMoreType: RichTextSeeMoreType? = null

        fun setOriginal(original: String) = apply { this.original = original }

        fun addSpanner(spanner: RichTextSpanner) = apply { this.spanners.add(spanner) }

        fun addSpanner(index: Int, spanner: RichTextSpanner) =
            apply { this.spanners.add(index, spanner) }

        fun setSeeMoreType(seeMoreType: RichTextSeeMoreType) =
            apply { this.seeMoreType = seeMoreType }

        fun build(): RichText {
            var spannable = original.toSpannable()

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

            return RichText(this.original, spannable, shortSpannable)
        }
    }
}