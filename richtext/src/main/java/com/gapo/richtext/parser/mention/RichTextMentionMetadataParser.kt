package com.gapo.richtext.parser.mention

import com.gapo.richtext.parser.RichTextMetadataParser
import com.gapo.richtext.parser.RichTextMetadataParserCache
import com.gapo.richtext.parser.RichTextMetadataParserSimpleCache

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextMentionMetadataParser(
    regex: Regex = Regex(MENTION_PATTERN),
    cache: RichTextMetadataParserCache = RichTextMetadataParserSimpleCache
) : RichTextMetadataParser(regex, cache) {

    companion object {
        const val MENTION_PATTERN = "(@[a-zA-Z\\d][\\w-]*)"
    }
}