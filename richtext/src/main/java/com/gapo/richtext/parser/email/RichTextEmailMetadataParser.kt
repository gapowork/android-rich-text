package com.gapo.richtext.parser.email

import androidx.core.util.PatternsCompat
import com.gapo.richtext.parser.RichTextMetadataParser
import com.gapo.richtext.parser.RichTextMetadataParserCache
import com.gapo.richtext.parser.RichTextMetadataParserSimpleCache

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextEmailMetadataParser(
    regex: Regex = Regex(PatternsCompat.EMAIL_ADDRESS.pattern()),
    cache: RichTextMetadataParserCache = RichTextMetadataParserSimpleCache
) : RichTextMetadataParser(regex, cache)