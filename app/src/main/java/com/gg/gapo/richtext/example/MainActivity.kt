package com.gg.gapo.richtext.example

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.doOnPreDraw
import com.gg.gapo.richtext.GapoOnClickSpanListener
import com.gg.gapo.richtext.GapoRichText
import com.gg.gapo.richtext.GapoRichTextLinkMovementMethod
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

        binding.text.movementMethod = GapoRichTextLinkMovementMethod.instance

        val text = "Hán Trung Kiên\nTrần Hoàng Việt\nKhúc Ngọc Huy\nNguyễn Hải Triều\nĐỗ Khánh Toàn\n# GapoWork\n## Nền tảng giao tiếp dành cho doanh nghiệp\nCải thiện sự kết nối giữa nhân viên trong tổ chức thông qua các tính năng phục vụ giao tiếp và tương tác. Từ đó thúc đẩy việc hiện thực hóa mục tiêu chung và lan tỏa giá trị cốt lõi.\n\n## Trò chuyện, nhắn tin với mọi người\n0969696969\nkienht@gapo.vn\nhttps://www.gapowork.vn\n#GapoWork đảm bảo truyền tải thông điệp quan trọng cho đúng người, vào đúng thời điểm, theo đúng cách.Phát triển khả năng tương tác đa chiều thông qua công cụ Chat."
        val seeMore = "\n...Xem thêm"

        val mentionMetadata = listOf(
            GapoRichTextMetadata("kienht", 0, 14),
            GapoRichTextMetadata("vietth", 15, 30),
            GapoRichTextMetadata("huykn", 31, 44),
            GapoRichTextMetadata("trieunh", 45, 61),
            GapoRichTextMetadata("toandk", 62, 75),
        )

        val color = Color.parseColor("#30A960")

        binding.text.doOnPreDraw {
            val richText = GapoRichText.Builder()
                .setOriginal(text)
                .addSpanner(GapoRichTextMarkdownSpanner(markwon))
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
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

                                override fun onLongClickSpan(
                                    view: View,
                                    value: Any,
                                    start: Int,
                                    end: Int
                                ) {
                                    Log.e("TAG", "onLongClickSpan: = $value")
                                }
                            }
                        )
                        .create()
                )
                .addSpanner(
                    GapoRichTextMetadataSpanner.Params()
                        .setForegroundColor(color)
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
                        .setForegroundColor(color)
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
                        .setForegroundColor(color)
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
                        .setForegroundColor(color)
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
                            .setForegroundColor(color)
                            .setMetadata(listOf(GapoRichTextMetadata(seeMore, 4, seeMore.length)))
                            .create()
                            .span(seeMore),
                        24,
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