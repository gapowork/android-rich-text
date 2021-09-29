package com.gapo.richtext

import android.os.Handler
import android.os.Looper
import android.text.Selection
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.view.MotionEvent
import android.widget.TextView

/**
 * @author vietth
 * @since 26/09/2021
 */
object RichTextLinkMovementMethod : LinkMovementMethod() {

    private const val LONG_CLICK_TIME = 500L

    private var longClickHandler: Handler? = null
    private var isLongPressed = false

    init {
        longClickHandler = Handler(Looper.getMainLooper())
    }

    override fun onTouchEvent(
        widget: TextView, buffer: Spannable,
        event: MotionEvent
    ): Boolean {
        val action = event.action
        if (action == MotionEvent.ACTION_CANCEL) {
            longClickHandler?.removeCallbacksAndMessages(null)
        }
        if (action == MotionEvent.ACTION_UP ||
            action == MotionEvent.ACTION_DOWN
        ) {
            var x = event.x.toInt()
            var y = event.y.toInt()
            x -= widget.totalPaddingLeft
            y -= widget.totalPaddingTop
            x += widget.scrollX
            y += widget.scrollY

            val layout = widget.layout
            val line = layout.getLineForVertical(y)
            val off = layout.getOffsetForHorizontal(line, x.toFloat())
            val link = buffer.getSpans(
                off, off,
                RichTextClickableSpan::class.java
            )

            val xx = event.x.toInt()
            val lineLeft = layout.getLineLeft(line)
            val lineRight = layout.getLineRight(line)

            if (xx > lineRight || xx >= 0 && xx < lineLeft) {
                return true
            }

            if (link.isNotEmpty()) {
                if (action == MotionEvent.ACTION_UP) {
                    longClickHandler?.removeCallbacksAndMessages(null)
                    if (!isLongPressed) {
                        link.first().onClick(widget)
                    }
                    isLongPressed = false
                } else {
                    Selection.setSelection(
                        buffer,
                        buffer.getSpanStart(link.first()),
                        buffer.getSpanEnd(link.first())
                    )
                    longClickHandler?.postDelayed({
                        link.first().onLongClick(widget)
                        isLongPressed = true
                    }, LONG_CLICK_TIME)
                }
                return true
            }
        }
        return super.onTouchEvent(widget, buffer, event)
    }
}