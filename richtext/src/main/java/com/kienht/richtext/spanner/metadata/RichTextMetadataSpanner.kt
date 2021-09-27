package com.kienht.richtext.spanner.metadata

import android.text.Spannable
import android.text.TextPaint
import android.text.style.*
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.text.toSpannable
import com.kienht.richtext.RichTextOnClickSpanListener
import com.kienht.richtext.RichTextClickableSpan
import com.kienht.richtext.parser.RichTextMetadataParser
import com.kienht.richtext.spanner.RichTextSpanner

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextMetadataSpanner private constructor(
    private val params: Params
) : RichTextSpanner {

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
        metadata: RichTextMetadata,
        flag: Int,
    ): Spannable {
        val value = metadata.value
        val start = metadata.start
        val end = metadata.end
        if (start < 0 || start >= end || end > length) return this

        val spans = params.createSpans(metadata)
        spans.forEach { span ->
            setSpan(span, start, end, flag)
        }
        return this
    }

    class Params {

        internal var metadata = listOf<RichTextMetadata>()

        internal var metadataParser: RichTextMetadataParser? = null

        @ColorInt
        private var foregroundColor = 0

        @ColorInt
        private var backgroundColor = 0

        private var underline = false

        private var strikethrough = false

        private var spanFactories = mutableListOf<RichTextMetadataSpanFactory>()

        private var onClickSpanListener: RichTextOnClickSpanListener? = null

        internal fun createSpans(metadata: RichTextMetadata): List<CharacterStyle> {
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
                    object : RichTextClickableSpan() {
                        override fun updateDrawState(textPaint: TextPaint) {
                            listener.updateDrawState(textPaint)
                        }

                        override fun onClick(widget: View) {
                            listener.onClickSpan(widget, metadata)
                        }

                        override fun onLongClick(widget: View) {
                            listener.onLongClickSpan(widget, metadata)
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

        fun setMetadata(metadata: List<RichTextMetadata>) = apply { this.metadata = metadata }

        fun setMetadataParser(metadataParser: RichTextMetadataParser) =
            apply { this.metadataParser = metadataParser }

        fun setBackgroundColor(@ColorInt backgroundColor: Int) =
            apply { this.backgroundColor = backgroundColor }

        fun setForegroundColor(@ColorInt foregroundColor: Int) =
            apply { this.foregroundColor = foregroundColor }

        fun setUnderline(underline: Boolean) = apply { this.underline = underline }

        fun setStrikethrough(strikethrough: Boolean) = apply { this.strikethrough = strikethrough }

        fun addSpanFactory(spanFactory: RichTextMetadataSpanFactory) =
            apply { this.spanFactories.add(spanFactory) }

        fun addAllSpanFactory(spanFactories: List<RichTextMetadataSpanFactory>) =
            apply { this.spanFactories.addAll(spanFactories) }

        fun setOnClickListener(onClickSpanListener: RichTextOnClickSpanListener?) =
            apply { this.onClickSpanListener = onClickSpanListener }

        fun create(): RichTextMetadataSpanner {
            return RichTextMetadataSpanner(this)
        }
    }
}