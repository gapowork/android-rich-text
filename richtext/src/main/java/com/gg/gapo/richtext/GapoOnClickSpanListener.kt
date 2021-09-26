package com.gg.gapo.richtext

import android.text.TextPaint
import android.view.View

/**
 * @author kienht
 * @since 26/09/2021
 */
interface GapoOnClickSpanListener {

    fun updateDrawState(textPaint: TextPaint) {
        textPaint.isUnderlineText = false
    }

    fun onClickSpan(view: View, value: Any, start: Int, end: Int)

    fun onLongClickSpan(view: View, value: Any, start: Int, end: Int) {}
}