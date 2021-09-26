package com.gg.gapo.richtext.spanner.seemore

import android.text.Spanned
import com.gg.gapo.richtext.measurement.GapoTextMeasurement

/**
 * @author kienht
 * @since 26/09/2021
 */
sealed class GapoRichTextSeeMoreType(
    open val seeMore: Spanned,
) {

    data class Length(
        override val seeMore: Spanned,
        val length: Int
    ) : GapoRichTextSeeMoreType(seeMore)

    data class Line(
        override val seeMore: Spanned,
        val line: Int,
        val measurementParams: GapoTextMeasurement.Params
    ) : GapoRichTextSeeMoreType(seeMore)
}