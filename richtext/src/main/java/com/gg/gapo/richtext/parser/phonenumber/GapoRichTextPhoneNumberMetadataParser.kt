package com.gg.gapo.richtext.parser.phonenumber

import com.gg.gapo.richtext.parser.GapoRichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object GapoRichTextPhoneNumberMetadataParser : GapoRichTextMetadataParser() {

    override val regex: Regex
        get() = Regex("((\\+84|0)+[35789]+([0-9]{8})\\b)")
}