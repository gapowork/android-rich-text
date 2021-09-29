package com.gapo.richtext.spanner.seemore

import android.text.Spannable
import androidx.core.text.toSpannable
import com.gapo.richtext.ext.toSpannableStringBuilder
import com.gapo.richtext.measurement.RichTextMeasurement
import com.gapo.richtext.spanner.RichTextSpanner

/**
 * @author kienht
 * @since 26/09/2021
 */
internal class RichTextSeeMoreSpanner(
    private val type: RichTextSeeMoreType
) : RichTextSpanner {

    override fun span(charSequence: CharSequence): Spannable {
        val seeMore = type.seeMore
        when (type) {
            is RichTextSeeMoreType.Length -> {
                val expectedContentLength = type.length
                if (expectedContentLength > charSequence.length) return charSequence.toSpannable()

                val indexToSubStr = charSequence.findIndexToSubStr(expectedContentLength)

                return charSequence.subSequence(0, indexToSubStr)
                    .toSpannableStringBuilder()
                    .append(seeMore)
            }
            is RichTextSeeMoreType.Line -> {
                val expectedContentLineCount = type.line

                var limitedLineCountContent = charSequence
                var contentLineCount =
                    measureLineCount(limitedLineCountContent, type.measurementParams)

                if (contentLineCount < expectedContentLineCount) return charSequence.toSpannable()

                var startIndex: Int
                var length = limitedLineCountContent.length
                var endIndex = length / 2

                while (contentLineCount != expectedContentLineCount) {
                    limitedLineCountContent = charSequence.subSequence(0, endIndex)
                    contentLineCount =
                        measureLineCount(limitedLineCountContent, type.measurementParams)

                    if (contentLineCount > expectedContentLineCount) {
                        startIndex = 0
                        length = endIndex
                    } else {
                        startIndex = endIndex
                    }
                    endIndex = (length + startIndex) / 2
                }

                endIndex = charSequence.findIndexToSubStr(limitedLineCountContent.length)
                limitedLineCountContent = charSequence.subSequence(0, endIndex)

                return limitedLineCountContent.toSpannableStringBuilder().append(seeMore)
            }
        }
    }

    private fun measureLineCount(
        charSequence: CharSequence,
        measurementParams: RichTextMeasurement.Params
    ): Int {
        return RichTextMeasurement.getTextLineCount(
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