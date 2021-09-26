package com.gg.gapo.richtext.parser.hashtag

import com.gg.gapo.richtext.parser.GapoRichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object GapoRichTextHashtagMetadataParser : GapoRichTextMetadataParser() {

    override val regex: Regex
        get() = Regex("(#[a-zA-Z\\d][\\w-]*)")
}