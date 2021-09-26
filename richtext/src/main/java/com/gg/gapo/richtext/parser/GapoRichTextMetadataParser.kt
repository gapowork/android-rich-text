package com.gg.gapo.richtext.parser

import com.gg.gapo.richtext.spanner.metadata.GapoRichTextMetadata

/**
 * @author kienht
 * @since 26/09/2021
 */
abstract class GapoRichTextMetadataParser {

    abstract val regex: Regex

    open fun parse(charSequence: CharSequence): List<GapoRichTextMetadata> {
        return regex.findAll(charSequence).map { result ->
            val value = result.value
            val range = result.range
            val start = range.first
            val end = start + value.length
            GapoRichTextMetadata(start, end, value)
        }.toList()
    }
}