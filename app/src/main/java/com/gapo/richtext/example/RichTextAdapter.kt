package com.gapo.richtext.example

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.doOnPreDraw
import androidx.recyclerview.widget.RecyclerView
import com.gapo.richtext.RichTextOnClickSpanListener
import com.gapo.richtext.ext.createRichText
import com.gapo.richtext.measurement.RichTextMeasurement
import com.gapo.richtext.parser.email.RichTextEmailMetadataParser
import com.gapo.richtext.parser.hashtag.RichTextHashtagMetadataParser
import com.gapo.richtext.parser.phonenumber.RichTextPhoneNumberMetadataParser
import com.gapo.richtext.parser.url.RichTextUrlMetadataParser
import com.gapo.richtext.spanner.markdown.RichTextMarkdownSpanner
import com.gapo.richtext.spanner.metadata.RichTextMetadata
import com.gapo.richtext.spanner.metadata.RichTextMetadataSpanner
import com.gapo.richtext.spanner.seemore.RichTextSeeMoreType
import io.noties.markwon.Markwon

/**
 * @author vietth
 * @since 29/09/2021
 */
class RichTextAdapter(
    private val markwon: Markwon,
    private val dataSet: List<String>,
) : RecyclerView.Adapter<RichTextAdapter.RichTextViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RichTextViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rich_text, parent, false)

        return RichTextViewHolder(view, markwon)
    }

    override fun onBindViewHolder(holder: RichTextViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int = dataSet.size

    class RichTextViewHolder(
        view: View,
        private val markwon: Markwon,
    ) : RecyclerView.ViewHolder(view) {
        private val textView: ReadMoreTextView = view.findViewById(R.id.text_read_more)

        private val seeMore = "...\nXem thêm"

        private val color = Color.parseColor("#30A960")

        private val mentionMetadata = listOf(
            RichTextMetadata("kienht", 0, 14),
            RichTextMetadata("vietth", 15, 30),
            RichTextMetadata("huykn", 31, 44),
            RichTextMetadata("trieunh", 45, 61),
            RichTextMetadata("toandk", 62, 75),
        )

        fun bind(content: String) {
            textView.doOnPreDraw {
                val richText = createRichText {
                    setText(content)
                    addSpanner(RichTextMarkdownSpanner(markwon))
                    addSpanner(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setMetadataParser(RichTextHashtagMetadataParser())
                            .setOnClickListener(
                                object : RichTextOnClickSpanListener {
                                    override fun onClickSpan(
                                        view: View,
                                        metadata: RichTextMetadata
                                    ) {
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
                    addSpanner(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setMetadataParser(RichTextPhoneNumberMetadataParser())
                            .setOnClickListener(
                                object : RichTextOnClickSpanListener {
                                    override fun onClickSpan(
                                        view: View,
                                        metadata: RichTextMetadata
                                    ) {
                                        Log.e("TAG", "onClickSpan: = $metadata")
                                    }
                                }
                            )
                            .create()
                    )
                    addSpanner(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setUnderline(true)
                            .setMetadataParser(RichTextUrlMetadataParser())
                            .setOnClickListener(
                                object : RichTextOnClickSpanListener {
                                    override fun onClickSpan(
                                        view: View,
                                        metadata: RichTextMetadata
                                    ) {
                                        Log.e("TAG", "onClickSpan: = $metadata")
                                    }
                                }
                            )
                            .create()
                    )
                    addSpanner(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setUnderline(true)
                            .setMetadataParser(RichTextEmailMetadataParser())
                            .setOnClickListener(
                                object : RichTextOnClickSpanListener {
                                    override fun onClickSpan(
                                        view: View,
                                        metadata: RichTextMetadata
                                    ) {
                                        Log.e("TAG", "onClickSpan: = $metadata")
                                    }
                                }
                            )
                            .create()
                    )
                    addSpanner(
                        RichTextMetadataSpanner.Params()
                            .setForegroundColor(color)
                            .setMetadata(mentionMetadata)
                            .setOnClickListener(
                                object : RichTextOnClickSpanListener {
                                    override fun onClickSpan(
                                        view: View,
                                        metadata: RichTextMetadata
                                    ) {
                                        Log.e("TAG", "onClickSpan: = $metadata")
                                    }
                                }
                            )
                            .create()
                    )
                    setSeeMoreType(
                        RichTextSeeMoreType.Line(
                            RichTextMetadataSpanner.Params()
                                .setForegroundColor(color)
                                .setMetadata(listOf(RichTextMetadata(seeMore, 4, seeMore.length)))
                                .create()
                                .span(seeMore),
                            10,
                            RichTextMeasurement.Params.Builder().from(textView).build()
                        )
                    )
                }

                textView.apply {
                    setText(
                        richText = richText,
                        isFullTextShown = false,
                    )
                    setOnClickNotSpanListener {
                        toggle()
                    }
                    setOnLongClickNotSpanListener {
                        Log.e("TAG", "onLongClick")
                    }
                }
            }
        }
    }
}