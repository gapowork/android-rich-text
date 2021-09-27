package com.kienht.richtext.parser.email

import androidx.core.util.PatternsCompat
import com.kienht.richtext.parser.RichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object RichTextEmailMetadataParser : RichTextMetadataParser() {

    override val regex: Regex
        get() = Regex(PatternsCompat.EMAIL_ADDRESS.pattern())
}