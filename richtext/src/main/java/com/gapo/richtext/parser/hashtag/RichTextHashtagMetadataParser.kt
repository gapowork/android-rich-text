package com.gapo.richtext.parser.hashtag

import com.gapo.richtext.parser.RichTextMetadataParser
import com.gapo.richtext.parser.RichTextMetadataParserCache
import com.gapo.richtext.parser.RichTextMetadataParserSimpleCache

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextHashtagMetadataParser(
    regex: Regex = Regex("(#[a-zA-Z\\d][\\w-]*)"),
    cache: RichTextMetadataParserCache = RichTextMetadataParserSimpleCache
) : RichTextMetadataParser(regex, cache)