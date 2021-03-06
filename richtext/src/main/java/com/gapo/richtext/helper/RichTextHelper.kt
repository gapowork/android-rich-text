package com.gapo.richtext.helper

import android.graphics.Color
import android.widget.TextView
import com.gapo.richtext.RichTextLinkMovementMethod
import com.gapo.richtext.RichTextSpannableFactory

/**
 * @author vietth
 * @since 29/09/2021
 */
class RichTextHelper(
    private val textView: TextView
) {

    fun setSpannableFactory() {
        textView.setSpannableFactory(RichTextSpannableFactory())
    }

    fun setRichTextLinkMovementMethod() {
        textView.movementMethod = RichTextLinkMovementMethod
    }

    fun removeHighLight() {
        textView.highlightColor = Color.TRANSPARENT
    }

    fun setOnClickNotSpanListener(onClick: () -> Unit) {
        textView.setOnClickListener {
            if (textView.selectionStart == -1 && textView.selectionEnd == -1) {
                onClick()
            }
        }
    }

    fun setOnLongClickNotSpanListener(onLongClick: () -> Unit) {
        textView.setOnLongClickListener {
            if (textView.selectionStart == -1 && textView.selectionEnd == -1) {
                onLongClick()
                return@setOnLongClickListener true
            }
            return@setOnLongClickListener false
        }
    }
}