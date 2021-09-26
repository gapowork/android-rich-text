package com.gg.gapo.richtext.spanner.seemore

import android.text.Spannable
import androidx.core.text.toSpannable
import com.gg.gapo.richtext.ext.toSpannableStringBuilder
import com.gg.gapo.richtext.measurement.GapoTextMeasurement
import com.gg.gapo.richtext.spanner.GapoRichTextSpanner

/**
 * @author kienht
 * @since 26/09/2021
 */
internal class GapoRichTextSeeMoreSpanner(
    private val type: GapoRichTextSeeMoreType
) : GapoRichTextSpanner {

    override fun span(charSequence: CharSequence): Spannable {
        val seeMore = type.seeMore
        when (type) {
            is GapoRichTextSeeMoreType.Length -> {
                val expectedContentLength = type.length
                if (expectedContentLength > charSequence.length) return charSequence.toSpannable()

                val indexToSubStr = charSequence.findIndexToSubStr(expectedContentLength)

                return charSequence.subSequence(0, indexToSubStr)
                    .toSpannableStringBuilder()
                    .append(seeMore)
            }
            is GapoRichTextSeeMoreType.Line -> {
                var limitedCountContent = charSequence
                var contentLineCount = measureLineCount(limitedCountContent, type.measurementParams)
                val expectedContentLineCount = type.line

                if (expectedContentLineCount > contentLineCount) return charSequence.toSpannable()

                while (contentLineCount > expectedContentLineCount) {
                    var indexToSubStr = limitedCountContent.length / 2
                    var newValue =
                        limitedCountContent.subSequence(0, indexToSubStr)
                    var newCount = measureLineCount(newValue, type.measurementParams)

                    while (newCount < expectedContentLineCount) {
                        indexToSubStr = (indexToSubStr * 2 / 1.5).toInt()
                        newValue = limitedCountContent.subSequence(0, indexToSubStr)

                        newCount = measureLineCount(newValue, type.measurementParams)
                    }

                    limitedCountContent = newValue
                    contentLineCount = newCount
                }

                if (contentLineCount == expectedContentLineCount) {
                    val indexToSubStr = charSequence.findIndexToSubStr(limitedCountContent.length)
                    limitedCountContent =
                        charSequence.subSequence(0, indexToSubStr)
                }

                return limitedCountContent.toSpannableStringBuilder().append(seeMore)
            }
        }
    }

    private fun measureLineCount(
        charSequence: CharSequence,
        measurementParams: GapoTextMeasurement.Params
    ): Int {
        return GapoTextMeasurement.getTextLineCount(
            charSequence,
            measurementParams
        )
    }

    private fun CharSequence.findIndexToSubStr(startIndex: Int): Int {
        var index = startIndex
        while (isLetterOrDigit(index)) {
            index += 1
        }
        return index
    }

    private fun CharSequence.isLetterOrDigit(index: Int): Boolean {
        val char = getOrNull(index)?.toString()
        return !char.isNullOrEmpty() && !char.isNullOrBlank() && char != "\n" && char != "\r"
    }
}