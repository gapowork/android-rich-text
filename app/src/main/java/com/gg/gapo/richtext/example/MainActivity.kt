package com.gg.gapo.richtext.example

import android.graphics.Color
import android.graphics.Typeface
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
import com.gg.gapo.richtext.spanner.markdown.GapoRichTextMarkdownSpanner
import com.gg.gapo.richtext.spanner.metadata.GapoRichTextMetadata
import com.gg.gapo.richtext.spanner.metadata.GapoRichTextMetadataSpanner
import com.gg.gapo.richtext.spanner.seemore.GapoRichTextSeeMoreType
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin

/**
 * @author kienht
 * @since 26/09/2021
 */
class MainActivity : AppCompatActivity() {

    private val markwon by lazy(LazyThreadSafetyMode.NONE) {
        Markwon.builder(this)
            .usePlugin(object : AbstractMarkwonPlugin() {
                override fun configureTheme(builder: MarkwonTheme.Builder) {
                    builder
                        .bulletWidth(8)
                        .headingBreakHeight(0)
                        .headingTextSizeMultipliers(floatArrayOf(1.43f, 1.21f, 1f, 1f, 1f, 1f))

                    builder.headingTypeface(Typeface.DEFAULT_BOLD)
                }
            })
            .usePlugin(SoftBreakAddsNewLinePlugin.create())
            .usePlugin(StrikethroughPlugin.create())
            .usePlugin(TablePlugin.create(this))
            .usePlugin(TaskListPlugin.create(this))
            .build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.text.movementMethod = LinkMovementMethod.getInstance()

        val text =
            "kienht vietth huykn trieunh toandk\n " +
                    "# Heading 1\n" +
                    "## Heading 2\n" +
                    "### Heading 3\n" + "Email kienht@gapo.vn Hán 0911667993 Trung https://kien.dev Kiên #hashtag nhéeeee #Kien Hán Trung #aothatday 0911667993 xxx"

        val seeMore = "\n...Xem thêm"

        val mentionMetadata = listOf(
            GapoRichTextMetadata("kienht", 0, 6),
            GapoRichTextMetadata("vietth", 7, 13),
            GapoRichTextMetadata("huykn", 14, 19),
            GapoRichTextMetadata("trieunh", 20, 27),
            GapoRichTextMetadata("toandk", 28, 34),
        )

        binding.text.doOnPreDraw {
            val richText = GapoRichText.Builder()
                .setOriginal(text)
                .addSpanner(GapoRichTextMarkdownSpanner(markwon))
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setBackgroundColor(Color.GREEN)
                        .setForegroundColor(Color.MAGENTA)
                        .setMetadataParser(GapoRichTextHashtagMetadataParser)
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
                        .setMetadataParser(GapoRichTextPhoneNumberMetadataParser)
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
                        .setMetadataParser(GapoRichTextUrlMetadataParser)
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
                        .setMetadataParser(GapoRichTextEmailMetadataParser)
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
                        .setMetadata(mentionMetadata)
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
                        10,
                        GapoTextMeasurement.Params.Builder().from(binding.text).build()
                    )
                )
                .build()

            binding.text.text = if (richText.seeMoreSpannable != null) {
                richText.seeMoreSpannable
            } else {
                richText.spannable
            }
        }
    }
}