package com.gapo.richtext.spanner.markdown

import android.text.Spannable
import androidx.core.text.toSpannable
import com.gapo.richtext.spanner.RichTextSpanner
import io.noties.markwon.Markwon

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextMarkdownSpanner(
    private val markwon: Markwon
) : RichTextSpanner {
    override fun span(charSequence: CharSequence): Spannable {
        return markwon.toMarkdown(charSequence.toString()).toSpannable()
    }
}