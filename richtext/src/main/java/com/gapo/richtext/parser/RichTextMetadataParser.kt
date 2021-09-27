package com.gapo.richtext.parser

import com.gapo.richtext.spanner.metadata.RichTextMetadata

/**
 * @author kienht
 * @since 26/09/2021
 */
abstract class RichTextMetadataParser(
    private val regex: Regex,
    private val cache: RichTextMetadataParserCache
) {

    open fun parse(charSequence: CharSequence): List<RichTextMetadata> {
        return cache.getOrPut(regex to charSequence) {
            regex.findAll(charSequence).map { result ->
                val value = result.value
                val range = result.range
                val start = range.first
                val end = start + value.length
                RichTextMetadata(value, start, end)
            }.toList()
        }
    }
}