package com.gapo.richtext.example

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.gapo.richtext.RichTextOnClickSpanListener
import com.gapo.richtext.RichText
import com.gapo.richtext.RichTextLinkMovementMethod
import com.gapo.richtext.example.databinding.ActivityMainBinding
import com.gapo.richtext.ext.RichTextHelper
import com.gapo.richtext.measurement.RichTextMeasurement
import com.gapo.richtext.parser.email.RichTextEmailMetadataParser
import com.gapo.richtext.parser.hashtag.RichTextHashtagMetadataParser
import com.gapo.richtext.parser.phonenumber.RichTextPhoneNumberMetadataParser
import com.gapo.richtext.parser.url.RichTextUrlMetadataParser
import com.gapo.richtext.spanner.markdown.RichTextMarkdownSpanner
import com.gapo.richtext.spanner.metadata.RichTextMetadata
import com.gapo.richtext.spanner.metadata.RichTextMetadataSpanner
import com.gapo.richtext.spanner.seemore.RichTextSeeMoreType
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

    private var richTextHelper: RichTextHelper? = null

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

        richTextHelper = RichTextHelper(binding.text)

        binding.text.movementMethod = RichTextLinkMovementMethod

        val text = "Hán Trung Kiên\nTrần Hoàng Việt\nKhúc Ngọc Huy\nNguyễn Hải Triều\nĐỗ Khánh Toàn\n# GapoWork\n## Nền tảng giao tiếp dành cho doanh nghiệp\nCải thiện sự kết nối giữa nhân viên trong tổ chức thông qua các tính năng phục vụ giao tiếp và tương tác. Từ đó thúc đẩy việc hiện thực hóa mục tiêu chung và lan tỏa giá trị cốt lõi.\n\n## Trò chuyện, nhắn tin với mọi người\n0969696969\nkienht@gapo.vn\nhttps://www.gapowork.vn\n#GapoWork đảm bảo truyền tải thông điệp quan trọng cho đúng người, vào đúng thời điểm, theo đúng cách.Phát triển khả năng tương tác đa chiều thông qua công cụ Chat."
        val seeMore = "...\nXem thêm"

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
                .setText(text)
                .addSpanner(RichTextMarkdownSpanner(markwon))
                .addSpanner(
                    RichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
                        .setMetadataParser(RichTextHashtagMetadataParser())
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
                        .setMetadataParser(RichTextPhoneNumberMetadataParser())
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
                        .setMetadataParser(RichTextUrlMetadataParser())
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
                        .setMetadataParser(RichTextEmailMetadataParser())
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
            richTextHelper?.setOnClickNotSpanListener {
                Log.e("TAG", "onClickText")
            }
        }
    }
}