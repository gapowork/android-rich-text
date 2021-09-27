package com.kienht.richtext.parser.hashtag

import com.kienht.richtext.parser.RichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object RichTextHashtagMetadataParser : RichTextMetadataParser() {

    override val regex: Regex
        get() = Regex("(#[a-zA-Z\\d][\\w-]*)")
}