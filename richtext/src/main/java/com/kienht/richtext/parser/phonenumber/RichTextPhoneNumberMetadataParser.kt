package com.kienht.richtext.parser.phonenumber

import com.kienht.richtext.parser.RichTextMetadataParser

/**
 * @author kienht
 * @since 26/09/2021
 */
object RichTextPhoneNumberMetadataParser : RichTextMetadataParser() {

    override val regex: Regex
        get() = Regex("((\\+84|0)+[35789]+([0-9]{8})\\b)")
}