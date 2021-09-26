package com.gg.gapo.richtext.parser.email

import androidx.core.util.PatternsCompat
import com.gg.gapo.richtext.parser.GapoRichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object GapoRichTextEmailMetadataParser : GapoRichTextMetadataParser() {

    override val regex: Regex
        get() = Regex(PatternsCompat.EMAIL_ADDRESS.pattern())
}