package com.gapo.richtext.parser.phonenumber

import com.gapo.richtext.parser.RichTextMetadataParser
import com.gapo.richtext.parser.RichTextMetadataParserCache
import com.gapo.richtext.parser.RichTextMetadataParserSimpleCache

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextPhoneNumberMetadataParser(
    regex: Regex = Regex(PHONE_NUMBER_PATTERN),
    cache: RichTextMetadataParserCache = RichTextMetadataParserSimpleCache
) : RichTextMetadataParser(regex, cache) {

    companion object {
        const val PHONE_NUMBER_PATTERN = "((\\+84|0)+[35789]+([0-9]{8})\\b)"
    }
}