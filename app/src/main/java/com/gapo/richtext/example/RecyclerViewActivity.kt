package com.gapo.richtext.example

import android.graphics.Rect
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration
import com.gapo.richtext.example.databinding.ActivityRecyclerViewBinding
import io.noties.markwon.AbstractMarkwonPlugin
import io.noties.markwon.Markwon
import io.noties.markwon.SoftBreakAddsNewLinePlugin
import io.noties.markwon.core.MarkwonTheme
import io.noties.markwon.ext.strikethrough.StrikethroughPlugin
import io.noties.markwon.ext.tables.TablePlugin
import io.noties.markwon.ext.tasklist.TaskListPlugin

/**
 * @author vietth
 * @since 29/09/2021
 */
class RecyclerViewActivity : AppCompatActivity() {

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
        val binding = ActivityRecyclerViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val text =
            "Hán Trung Kiên\nTrần Hoàng Việt\nKhúc Ngọc Huy\nNguyễn Hải Triều\nĐỗ Khánh Toàn\n# GapoWork\n## Nền tảng giao tiếp dành cho doanh nghiệp\nCải thiện sự kết nối giữa nhân viên trong tổ chức thông qua các tính năng phục vụ giao tiếp và tương tác. Từ đó thúc đẩy việc hiện thực hóa mục tiêu chung và lan tỏa giá trị cốt lõi.\n\n## Trò chuyện, nhắn tin với mọi người\n0969696969\nkienht@gapo.vn\nhttps://www.gapowork.vn\n#GapoWork đảm bảo truyền tải thông điệp quan trọng cho đúng người, vào đúng thời điểm, theo đúng cách.Phát triển khả năng tương tác đa chiều thông qua công cụ Chat."

        binding.listText.apply {
            setHasFixedSize(true)
            addItemDecoration(
                DividerItemDecoration(
                    this@RecyclerViewActivity,
                    RecyclerView.VERTICAL
                )
            )
            addItemDecoration(object : ItemDecoration() {
                override fun getItemOffsets(
                    outRect: Rect,
                    view: View,
                    parent: RecyclerView,
                    state: RecyclerView.State
                ) {
                    val position = parent.getChildAdapterPosition(view)
                    if (position != RecyclerView.NO_POSITION) {
                        outRect.set(24, 24, 24, 24)
                    }
                }
            })
            adapter = RichTextAdapter(
                markwon,
                mutableListOf<String>().apply {
                    repeat(100) {
                        add(text)
                    }
                }
            )
        }
    }
}
