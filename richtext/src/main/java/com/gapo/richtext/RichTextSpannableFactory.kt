package com.gapo.richtext

import android.text.Spannable
import android.text.SpannableString

/**
 * @author kienht
 * @since 29/09/2021
 */
class RichTextSpannableFactory : Spannable.Factory() {

    override fun newSpannable(source: CharSequence?): Spannable {
        return source as? Spannable ?: SpannableString(source ?: "")
    }
}