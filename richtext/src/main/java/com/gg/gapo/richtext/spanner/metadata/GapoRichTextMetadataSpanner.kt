package com.gg.gapo.richtext.spanner.metadata

import android.text.Spannable
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.toSpannable
import com.gg.gapo.richtext.GapoOnClickSpanListener
import com.gg.gapo.richtext.parser.GapoRichTextMetadataParser
import com.gg.gapo.richtext.spanner.GapoRichTextSpanner

/**
 * @author kienht
 * @since 26/09/2021
 */
class GapoRichTextMetadataSpanner private constructor(
    private val params: Params
) : GapoRichTextSpanner {

    override fun span(charSequence: CharSequence): Spannable {
        val metadata = params.metadataParser?.parse(charSequence) ?: params.metadata
        val spannable = charSequence.toSpannable()
        metadata.forEach { meta ->
            spannable.span(
                params,
                meta,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
            )
        }
        return spannable
    }

    private fun Spannable.span(
        params: Params,
        metadata: GapoRichTextMetadata,
        flag: Int,
    ): Spannable {
        val value = metadata.value
        val start = metadata.start
        val end = metadata.end
        if (start < 0 || start >= end || end > length) return this

        val spans = params.createSpans(value, start, end)
        spans.forEach { span ->
            setSpan(span, start, end, flag)
        }
        return this
    }

    class Params {

        internal var metadata = listOf<GapoRichTextMetadata>()

        internal var metadataParser: GapoRichTextMetadataParser? = null

        @ColorInt
        private var foregroundColor = 0

        @ColorInt
        private var backgroundColor = 0

        private var underline = false

        private var strikethrough = false

        private var spanFactories = mutableListOf<GapoRichTextMetadataSpanFactory>()

        private var onClickSpanListener: GapoOnClickSpanListener? = null

        internal fun createSpans(value: Any, start: Int, end: Int): List<CharacterStyle> {
            val spans = mutableListOf<CharacterStyle>()
            if (backgroundColor != 0) {
                spans.add(BackgroundColorSpan(backgroundColor))
            }
            if (foregroundColor != 0) {
                spans.add(ForegroundColorSpan(foregroundColor))
            }
            val listener = onClickSpanListener
            if (listener != null) {
                spans.add(
                    object : ClickableSpan() {
                        override fun updateDrawState(textPaint: TextPaint) {
                            listener.updateDrawState(textPaint)
                        }

                        override fun onClick(widget: View) {
                            listener.onClickSpan(widget, value, start, end)
                        }
                    }
                )
            }
            if (underline) {
                spans.add(UnderlineSpan())
            }
            if (strikethrough) {
                spans.add(StrikethroughSpan())
            }
            spans.addAll(spanFactories.map { it.create() })
            return spans
        }

        fun setMetadata(metadata: List<GapoRichTextMetadata>) = apply { this.metadata = metadata }

        fun setMetadataParser(metadataParser: GapoRichTextMetadataParser) =
            apply { this.metadataParser = metadataParser }

        fun setBackgroundColor(@ColorInt backgroundColor: Int) =
            apply { this.backgroundColor = backgroundColor }

        fun setForegroundColor(@ColorInt foregroundColor: Int) =
            apply { this.foregroundColor = foregroundColor }

        fun setUnderline(underline: Boolean) = apply { this.underline = underline }

        fun setStrikethrough(strikethrough: Boolean) = apply { this.strikethrough = strikethrough }

        fun addSpanFactory(spanFactory: GapoRichTextMetadataSpanFactory) =
            apply { this.spanFactories.add(spanFactory) }

        fun addAllSpanFactory(spanFactories: List<GapoRichTextMetadataSpanFactory>) =
            apply { this.spanFactories.addAll(spanFactories) }

        fun setOnClickListener(onClickSpanListener: GapoOnClickSpanListener?) =
            apply { this.onClickSpanListener = onClickSpanListener }

        fun create(): GapoRichTextMetadataSpanner {
            return GapoRichTextMetadataSpanner(this)
        }
    }
}