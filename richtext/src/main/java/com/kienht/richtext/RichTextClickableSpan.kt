package com.kienht.richtext

import android.text.style.ClickableSpan
import android.view.View

/**
 * @author vietth
 * @since 26/09/2021
 */
open class RichTextClickableSpan : ClickableSpan() {

    override fun onClick(widget: View) {

    }

    open fun onLongClick(widget: View) {}
}