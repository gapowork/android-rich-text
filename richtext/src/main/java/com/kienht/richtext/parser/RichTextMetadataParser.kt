package com.kienht.richtext.parser

import com.kienht.richtext.spanner.metadata.RichTextMetadata

/**
 * @author kienht
 * @since 26/09/2021
 */
abstract class RichTextMetadataParser {

    abstract val regex: Regex

    open fun parse(charSequence: CharSequence): List<RichTextMetadata> {
        return regex.findAll(charSequence).map { result ->
            val value = result.value
            val range = result.range
            val start = range.first
            val end = start + value.length
            RichTextMetadata(value, start, end)
        }.toList()
    }
}