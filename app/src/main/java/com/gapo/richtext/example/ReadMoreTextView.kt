package com.gapo.richtext.example

import android.content.Context
import android.text.SpannableString
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.transition.TransitionManager
import com.gapo.richtext.RichText
import com.gapo.richtext.helper.RichTextHelper

/**
 * @author vietth
 * @since 29/09/2021
 */
class ReadMoreTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    private var isFullTextShown = false

    private var richText: RichText? = null
    private val richTextHelper = RichTextHelper(this)

    init {
        setText(SpannableString(""), BufferType.SPANNABLE)
        richTextHelper.apply {
            setSpannableFactory()
            removeHighLight()
            setRichTextLinkMovementMethod()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        val parentLayout = parent as? ViewGroup ?: return
        TransitionManager.endTransitions(parentLayout)
    }

    fun setText(
        richText: RichText?,
        isFullTextShown: Boolean,
    ) {
        this.isFullTextShown = isFullTextShown
        this.richText = richText
        val text = if (richText != null) {
            if (isFullTextShown) {
                richText.spannable
            } else {
                richText.seeMoreSpannable
            }
        } else {
            SpannableString("")
        }
        setText(text, BufferType.SPANNABLE)
    }

    fun setOnClickNotSpanListener(onClick: () -> Unit) {
        richTextHelper.setOnClickNotSpanListener {
            onClick()
        }
    }

    fun setOnLongClickNotSpanListener(onLongClick: () -> Unit) {
        richTextHelper.setOnLongClickNotSpanListener {
            onLongClick()
        }
    }

    fun toggle(withAnim: Boolean = false): Boolean? {
        val richText = richText ?: return null
        if (withAnim) {
            val parentLayout = parent as? ViewGroup ?: return null
            TransitionManager.beginDelayedTransition(parentLayout)
        }
        val text = if (isFullTextShown) {
            richText.seeMoreSpannable
        } else {
            richText.spannable
        }
        setText(text, BufferType.SPANNABLE)
        isFullTextShown = !isFullTextShown
        return isFullTextShown
    }
}
