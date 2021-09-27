package com.kienht.richtext.spanner

import android.text.Spannable

/**
 * @author kienht
 * @since 26/09/2021
 */
interface RichTextSpanner {

    fun span(charSequence: CharSequence): Spannable
}