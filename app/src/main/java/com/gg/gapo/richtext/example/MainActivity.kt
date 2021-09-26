package com.gg.gapo.richtext.example

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.View
import androidx.core.view.doOnPreDraw
import com.gg.gapo.richtext.GapoOnClickSpanListener
import com.gg.gapo.richtext.GapoRichText
import com.gg.gapo.richtext.example.databinding.ActivityMainBinding
import com.gg.gapo.richtext.measurement.GapoTextMeasurement
import com.gg.gapo.richtext.parser.email.GapoRichTextEmailMetadataParser
import com.gg.gapo.richtext.parser.hashtag.GapoRichTextHashtagMetadataParser
import com.gg.gapo.richtext.parser.phonenumber.GapoRichTextPhoneNumberMetadataParser
import com.gg.gapo.richtext.parser.url.GapoRichTextUrlMetadataParser
import com.gg.gapo.richtext.spanner.metadata.GapoRichTextMetadata
import com.gg.gapo.richtext.spanner.metadata.GapoRichTextMetadataSpanner
import com.gg.gapo.richtext.spanner.seemore.GapoRichTextSeeMoreType

/**
 * @author kienht
 * @since 26/09/2021
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.movementMethod = LinkMovementMethod.getInstance()

        val text =
            "Email kienht@gapo.vn Hán 0911667993 Trung https://kien.dev Kiên #hashtag nhéeeee #Kien Hán Trung #aothatday 0911667993 xxx"

        val seeMore = " ...Xem thêm"

        binding.text.doOnPreDraw {
            val richText = GapoRichText.Builder()
                .setOriginal(text)
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setBackgroundColor(Color.GREEN)
                        .setForegroundColor(Color.MAGENTA)
                        .setMetadata(GapoRichTextHashtagMetadataParser.parse(text))
                        .setOnClickListener(
                            object : GapoOnClickSpanListener {
                                override fun onClickSpan(
                                    view: View,
                                    value: Any,
                                    start: Int,
                                    end: Int
                                ) {
                                    Log.e("TAG", "onClickSpan: = $value")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setForegroundColor(Color.CYAN)
                        .setStrikethrough(true)
                        .setMetadata(GapoRichTextPhoneNumberMetadataParser.parse(text))
                        .setOnClickListener(
                            object : GapoOnClickSpanListener {
                                override fun onClickSpan(
                                    view: View,
                                    value: Any,
                                    start: Int,
                                    end: Int
                                ) {
                                    Log.e("TAG", "onClickSpan: = $value")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setForegroundColor(Color.RED)
                        .setUnderline(true)
                        .setMetadata(GapoRichTextUrlMetadataParser.parse(text))
                        .setOnClickListener(
                            object : GapoOnClickSpanListener {
                                override fun onClickSpan(
                                    view: View,
                                    value: Any,
                                    start: Int,
                                    end: Int
                                ) {
                                    Log.e("TAG", "onClickSpan: = $value")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setForegroundColor(Color.YELLOW)
                        .setUnderline(true)
                        .setMetadata(GapoRichTextEmailMetadataParser.parse(text))
                        .setOnClickListener(
                            object : GapoOnClickSpanListener {
                                override fun onClickSpan(
                                    view: View,
                                    value: Any,
                                    start: Int,
                                    end: Int
                                ) {
                                    Log.e("TAG", "onClickSpan: = $value")
                                }
                            }
                        )
                        .create()
                )
                .setSeeMoreType(
                    GapoRichTextSeeMoreType.Line(
                        GapoRichTextMetadataSpanner.Params()
                            .setForegroundColor(Color.GREEN)
                            .setMetadata(listOf(GapoRichTextMetadata(seeMore, 4, seeMore.length)))
                            .create()
                            .span(seeMore),
                        3,
                        GapoTextMeasurement.Params.Builder().from(binding.text).build()
                    )
                )
                .build()

            binding.text.text = richText.shortSpannable
        }
    }
}