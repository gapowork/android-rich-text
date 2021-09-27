package com.kienht.richtext.example

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.kienht.richtext.RichTextOnClickSpanListener
import com.kienht.richtext.RichText
import com.kienht.richtext.RichTextLinkMovementMethod
import com.kienht.richtext.example.databinding.ActivityMainBinding
import com.kienht.richtext.measurement.RichTextMeasurement
import com.kienht.richtext.parser.email.RichTextEmailMetadataParser
import com.kienht.richtext.parser.hashtag.RichTextHashtagMetadataParser
import com.kienht.richtext.parser.phonenumber.RichTextPhoneNumberMetadataParser
import com.kienht.richtext.parser.url.RichTextUrlMetadataParser
import com.kienht.richtext.spanner.markdown.RichTextMarkdownSpanner
import com.kienht.richtext.spanner.metadata.RichTextMetadata
import com.kienht.richtext.spanner.metadata.RichTextMetadataSpanner
import com.kienht.richtext.spanner.seemore.RichTextSeeMoreType
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

        binding.text.movementMethod = RichTextLinkMovementMethod.instance

        val text = "Hán Trung Kiên\nTrần Hoàng Việt\nKhúc Ngọc Huy\nNguyễn Hải Triều\nĐỗ Khánh Toàn\n# Markdown H1\n## Markdown H2\nDealing with Android Text by simple way to get high performance. Dealing with Android Text by simple way to get high performance. Dealing with Android Text by simple way to get high performance.\n\n## Trò chuyện, nhắn tin với mọi người\n0969696969\nkienhantrung@gmail.com\nhttps://www.google.vn\n#RichText Dealing with Android Text by simple way to get high performance. Dealing with Android Text by simple way to get high performance."
        val seeMore = "\n...Xem thêm"

        val mentionMetadata = listOf(
            RichTextMetadata("kienht", 0, 14),
            RichTextMetadata("vietth", 15, 30),
            RichTextMetadata("huykn", 31, 44),
            RichTextMetadata("trieunh", 45, 61),
            RichTextMetadata("toandk", 62, 75),
        )

        val color = Color.parseColor("#30A960")

        binding.text.doOnPreDraw {
            val richText = RichText.Builder()
                .setOriginal(text)
                .addSpanner(RichTextMarkdownSpanner(markwon))
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setMetadataParser(RichTextHashtagMetadataParser)
                        .setOnClickListener(
                            object : RichTextOnClickSpanListener {
                                override fun onClickSpan(view: View, metadata: RichTextMetadata) {
                                    Log.e("TAG", "onClickSpan: = $metadata")
                                }

                                override fun onLongClickSpan(
                                    view: View,
                                    metadata: RichTextMetadata
                                ) {
                                    Log.e("TAG", "onLongClickSpan: = $metadata")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setMetadataParser(RichTextPhoneNumberMetadataParser)
                        .setOnClickListener(
                            object : RichTextOnClickSpanListener {
                                override fun onClickSpan(view: View, metadata: RichTextMetadata) {
                                    Log.e("TAG", "onClickSpan: = $metadata")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setUnderline(true)
                        .setMetadataParser(RichTextUrlMetadataParser)
                        .setOnClickListener(
                            object : RichTextOnClickSpanListener {
                                override fun onClickSpan(view: View, metadata: RichTextMetadata) {
                                    Log.e("TAG", "onClickSpan: = $metadata")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setUnderline(true)
                        .setMetadataParser(RichTextEmailMetadataParser)
                        .setOnClickListener(
                            object : RichTextOnClickSpanListener {
                                override fun onClickSpan(view: View, metadata: RichTextMetadata) {
                                    Log.e("TAG", "onClickSpan: = $metadata")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setMetadata(mentionMetadata)
                        .setOnClickListener(
                            object : RichTextOnClickSpanListener {
                                override fun onClickSpan(view: View, metadata: RichTextMetadata) {
                                    Log.e("TAG", "onClickSpan: = $metadata")
                                }
                            }
                        )
                        .create()
                )
                .setSeeMoreType(
                    RichTextSeeMoreType.Line(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setMetadata(listOf(RichTextMetadata(seeMore, 4, seeMore.length)))
                            .create()
                            .span(seeMore),
                        24,
                        RichTextMeasurement.Params.Builder().from(binding.text).build()
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