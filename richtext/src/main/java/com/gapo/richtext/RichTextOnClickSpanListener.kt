package com.gapo.richtext

import android.text.TextPaint
import android.view.View
import com.gapo.richtext.spanner.metadata.RichTextMetadata

/**
 * @author kienht
 * @since 26/09/2021
 */
interface RichTextOnClickSpanListener {

    fun updateDrawState(textPaint: TextPaint) {
        textPaint.isUnderlineText = false
    }

    fun onClickSpan(view: View, metadata: RichTextMetadata)

    fun onLongClickSpan(view: View, metadata: RichTextMetadata) {}
}