package com.gapo.richtext.parser

import com.gapo.richtext.spanner.metadata.RichTextMetadata

/**
 * @author kienht
 * @since 27/09/2021
 */
interface RichTextMetadataParserCache {

    fun get(key: Any): List<RichTextMetadata>?

    fun getOrPut(key: Any, defaultValue: () -> List<RichTextMetadata>): List<RichTextMetadata>

    fun put(key: Any, value: List<RichTextMetadata>): List<RichTextMetadata>?

    fun remove(key: Any): List<RichTextMetadata>?

    fun clear()
}