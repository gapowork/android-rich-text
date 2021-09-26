package com.gg.gapo.richtext.spanner.markdown

import android.text.Spannable
import androidx.core.text.toSpannable
import com.gg.gapo.richtext.spanner.GapoRichTextSpanner
import io.noties.markwon.Markwon

/**
 * @author kienht
 * @since 26/09/2021
 */
class GapoRichTextMarkdownSpanner(
    private val markwon: Markwon
) : GapoRichTextSpanner {
    override fun span(charSequence: CharSequence): Spannable {
        return markwon.toMarkdown(charSequence.toString()).toSpannable()
    }
}