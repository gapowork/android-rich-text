package com.gapo.richtext.spanner.metadata

import android.text.style.CharacterStyle

/**
 * @author kienht
 * @since 26/09/2021
 */
interface RichTextMetadataSpanFactory {

    fun create(): CharacterStyle
}