package com.gg.gapo.richtext.spanner

import android.text.Spannable

/**
 * @author kienht
 * @since 26/09/2021
 */
interface GapoRichTextSpanner {

    fun span(charSequence: CharSequence): Spannable
}