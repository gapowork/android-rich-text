package com.gapo.richtext.measurement

import android.os.Build
import android.text.Layout
import android.text.StaticLayout
import android.text.TextDirectionHeuristic
import android.text.TextPaint
import android.widget.TextView

/**
 * @author kienht
 * @since 25/09/2021
 * https://stackoverflow.com/a/58975681
 * https://stackoverflow.com/a/64754080
 */
object RichTextMeasurement {

    fun getTextLines(text: CharSequence, params: Params): List<CharSequence> {
        val layout = getStaticLayout(text, params)
        return (0 until layout.lineCount).map {
            layout.text.subSequence(layout.getLineStart(it), layout.getLineEnd(it))
        }
    }

    fun getTextLineCount(text: CharSequence, params: Params): Int {
        val layout = getStaticLayout(text, params)
        return layout.lineCount
    }

    fun getTextLines(textView: TextView): List<CharSequence> {
        val layout =
            getStaticLayout(textView.text, Params.Builder().from(textView).build())
        return (0 until layout.lineCount).map {
            layout.text.subSequence(layout.getLineStart(it), layout.getLineEnd(it))
        }
    }

    fun getTextLineCount(textView: TextView): Int {
        val layout =
            getStaticLayout(textView.text, Params.Builder().from(textView).build())
        return layout.lineCount
    }

    private fun getStaticLayout(
        text: CharSequence,
        params: Params
    ): StaticLayout = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val builder = StaticLayout.Builder
            .obtain(text, 0, text.length, params.textPaint, params.textWidth)
            .setAlignment(params.alignment)
            .setLineSpacing(params.lineSpacingExtra, params.lineSpacingMultiplier)
            .setIncludePad(params.includeFontPadding)
            .setBreakStrategy(params.breakStrategy)
            .setHyphenationFrequency(params.hyphenationFrequency)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder.setJustificationMode(params.justificationMode)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            builder.setUseLineSpacingFromFallbacks(params.useFallbackLineSpacing)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q && params.textDirectionHeuristic != null) {
            builder.setTextDirection(params.textDirectionHeuristic)
        }
        builder.build()
    } else {
        @Suppress("DEPRECATION")
        StaticLayout(
            text,
            params.textPaint,
            params.textWidth,
            params.alignment,
            params.lineSpacingMultiplier,
            params.lineSpacingExtra,
            params.includeFontPadding
        )
    }

    class Params private constructor(
        val textPaint: TextPaint,
        val alignment: Layout.Alignment,
        val lineSpacingExtra: Float,
        val lineSpacingMultiplier: Float,
        val includeFontPadding: Boolean,
        val breakStrategy: Int,
        val hyphenationFrequency: Int,
        val justificationMode: Int,
        val useFallbackLineSpacing: Boolean,
        val textDirectionHeuristic: TextDirectionHeuristic?,
        val textWidth: Int
    ) {

        constructor(builder: Builder) : this(
            builder.textPaint,
            builder.alignment,
            builder.lineSpacingExtra,
            builder.lineSpacingMultiplier,
            builder.includeFontPadding,
            builder.breakStrategy,
            builder.hyphenationFrequency,
            builder.justificationMode,
            builder.useFallbackLineSpacing,
            builder.textDirectionHeuristic,
            builder.textWidth,
        )

        class Builder {
            var textPaint = TextPaint()
            var alignment = Layout.Alignment.ALIGN_NORMAL
            var lineSpacingExtra = 0f
            var lineSpacingMultiplier = 1.0f
            var includeFontPadding = true
            var breakStrategy = 0
            var hyphenationFrequency = 0
            var justificationMode = 0
            var useFallbackLineSpacing = false
            var textDirectionHeuristic: TextDirectionHeuristic? = null
            var textWidth = 0

            fun from(textView: TextView) = apply {
                val layout = textView.layout

                setTextPaint(layout.paint)
                setAlignment(layout.alignment)
                setTextWidth(textView.width - textView.compoundPaddingLeft - textView.compoundPaddingRight)
                setLineSpacingExtra(textView.lineSpacingExtra)
                setLineSpacingMultiplier(textView.lineSpacingMultiplier)
                setIncludeFontPadding(textView.includeFontPadding)

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    setBreakStrategy(textView.breakStrategy)
                    setHyphenationFrequency(textView.hyphenationFrequency)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    setJustificationMode(textView.justificationMode)
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    setUseFallbackLineSpacing(textView.isFallbackLineSpacing)

                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    setTextDirectionHeuristic(textView.textDirectionHeuristic)
                }
            }

            fun setTextPaint(textPaint: TextPaint) = apply { this.textPaint = textPaint }

            fun setAlignment(alignment: Layout.Alignment) = apply { this.alignment = alignment }

            fun setLineSpacingExtra(lineSpacingExtra: Float) =
                apply { this.lineSpacingExtra = lineSpacingExtra }

            fun setLineSpacingMultiplier(lineSpacingMultiplier: Float) =
                apply { this.lineSpacingMultiplier = lineSpacingMultiplier }

            fun setIncludeFontPadding(includeFontPadding: Boolean) =
                apply { this.includeFontPadding = includeFontPadding }

            fun setBreakStrategy(breakStrategy: Int) = apply { this.breakStrategy = breakStrategy }

            fun setHyphenationFrequency(hyphenationFrequency: Int) =
                apply { this.hyphenationFrequency = hyphenationFrequency }

            fun setJustificationMode(justificationMode: Int) =
                apply { this.justificationMode = justificationMode }

            fun setUseFallbackLineSpacing(useFallbackLineSpacing: Boolean) =
                apply { this.useFallbackLineSpacing = useFallbackLineSpacing }

            fun setTextDirectionHeuristic(textDirectionHeuristic: TextDirectionHeuristic) =
                apply { this.textDirectionHeuristic = textDirectionHeuristic }

            fun setTextWidth(textWidth: Int) = apply { this.textWidth = textWidth }

            fun build(): Params {
                return Params(this)
            }
        }
    }
}