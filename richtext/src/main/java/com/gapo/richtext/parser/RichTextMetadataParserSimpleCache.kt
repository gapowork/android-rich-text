package com.gapo.richtext.parser

import com.gapo.richtext.spanner.metadata.RichTextMetadata

/**
 * @author kienht
 * @since 27/09/2021
 */
object RichTextMetadataParserSimpleCache : RichTextMetadataParserCache {

    private val cached = mutableMapOf<Any, List<RichTextMetadata>>()

    override fun get(key: Any): List<RichTextMetadata>? {
        return cached[key]
    }

    override fun getOrPut(
        key: Any,
        defaultValue: () -> List<RichTextMetadata>
    ): List<RichTextMetadata> {
        return cached.getOrPut(key, defaultValue)
    }

    override fun put(key: Any, value: List<RichTextMetadata>): List<RichTextMetadata>? {
        return cached.put(key, value)
    }

    override fun remove(key: Any): List<RichTextMetadata>? {
        return cached.remove(key)
    }

    override fun clear() {
        cached.clear()
    }
}