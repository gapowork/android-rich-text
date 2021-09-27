package com.kienht.richtext.spanner.seemore

import android.text.Spanned
import com.kienht.richtext.measurement.RichTextMeasurement

/**
 * @author kienht
 * @since 26/09/2021
 */
sealed class RichTextSeeMoreType(
    open val seeMore: Spanned,
) {

    data class Length(
        override val seeMore: Spanned,
        val length: Int
    ) : RichTextSeeMoreType(seeMore)

    data class Line(
        override val seeMore: Spanned,
        val line: Int,
        val measurementParams: RichTextMeasurement.Params
    ) : RichTextSeeMoreType(seeMore)
}