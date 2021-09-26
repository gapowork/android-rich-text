package com.gg.gapo.richtext.spanner

import android.text.style.ClickableSpan
import android.view.View

/**
 * @author vietth
 * @since 26/09/2021
 */
open class GapoRichTextClickableSpan : ClickableSpan() {

    override fun onClick(widget: View) {

    }

    open fun onLongClick(widget: View) {}
}