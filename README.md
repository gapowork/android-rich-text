# Gapo Android RichText

**RichText** supports Hashtag, Mention, Url, Phone Number, Email, Markdown, Custom Span,
SeeMore/SeeLess by limited line or length, and ability to handle clicks and long clicks on spanned content.
Ability prepare data on background thread before it was drawn on main thread.

<img src="/rich-text-screenshot.png" width="288" />

## Installation

Gradle

```gradle
implementation 'vn.gapowork.android:rich-text:1.0.0-alpha04'
```

## Usage

[Example](/app/src/main/java/com/gapo/richtext/example/MainActivity.kt):

```kotlin

private val richTextHelper = RichTextHelper(binding.textView)
richTextHelper.setRichTextLinkMovementMethod()
richTextHelper.removeHighLight()
richTextHelper.setOnClickNotSpanListener { }

val color = Color.parseColor("#30A960")

val richText = RichText.Builder()
    .setText(text)
    .addSpanner(
       RichTextMetadataSpanner.Params()
           .setForegroundColor(color)
           .setUnderline(true)
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
    .build()

binding.textView.text = richText.spannable
```

## RecyclerView

```kotlin
richTextHelper.setSpannableFactory()
```

## Supported Cases
- Hashtag: `#hashtag`
```kotlin
.addSpanner(
    RichTextMetadataSpanner.Params()
        .setMetadataParser(RichTextHashtagMetadataParser())
        .create()
)
```
- Mention: `@mention`
```kotlin
val mentionMetadata = listOf(
    RichTextMetadata("kienht", 0, 14),
    RichTextMetadata("vietth", 15, 30),
)
.addSpanner(
    RichTextMetadataSpanner.Params()
        .setMetadata(mentionMetadata) // or
        .setMetadataParser(RichTextMentionMetadataParser())
        .create()
)
```
- URL:
```kotlin
.addSpanner(
    RichTextMetadataSpanner.Params()
        .setMetadataParser(RichTextUrlMetadataParser())
        .create()
)
```
- Phone Number:
```kotlin
.addSpanner(
    RichTextMetadataSpanner.Params()
        .setMetadataParser(RichTextPhoneNumberMetadataParser())
        .create()
)
```
- Email:
```kotlin
.addSpanner(
    RichTextMetadataSpanner.Params()
        .setMetadataParser(RichTextEmailMetadataParser())
        .create()
)
```
- Markdown:

```gradle
implementation "io.noties.markwon:core:latest_version"
```

```kotlin
private val markwon by lazy(LazyThreadSafetyMode.NONE) {
    Markwon.builder(context)
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
.addSpanner(RichTextMarkdownSpanner(markwon))
```

- SeeMore/SeeLess by limited length and line:
  type  [RichTextSeeMoreType](/richtext/src/main/java/com/gapo/richtext/spanner/seemore/RichTextSeeMoreType.kt)
```kotlin
val seeMore = "...\nXem thêm"

val richText = RichText.Builder()
    .setOriginal(text)
    .addSpanner(...)
    .setSeeMoreType(
        RichTextSeeMoreType.Line(
            seeMore = RichTextMetadataSpanner.Params()
                .setForegroundColor(color)
                .setMetadata(listOf(RichTextMetadata(seeMore, 4, seeMore.length)))
                .create()
                .span(seeMore),
            line = 24,
            measurementParams = RichTextMeasurement.Params.Builder().from(binding.text).build()
        )
    )
binding.text.text = if (richText.seeMoreSpannable != null) {
    richText.seeMoreSpannable
} else {
    richText.spannable
}
```

SeeMore/SeeLess is limited by line count, TextView's layout_width must be `match_parent`.

```xml

<androidx.appcompat.widget.AppCompatTextView 
    android:id="@+id/text"
    android:layout_width="match_parent".../>
```

- [Cached](/richtext/src/main/java/com/gapo/richtext/parser/RichTextMetadataParserSimpleCache.kt)

## License
~~~
Copyright (c) 2021, Gapo Technology JSC
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of Gapo Technology JSC nor the names of its contributors
      may be used to endorse or promote products derived from this software
      without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL GAPO TECHNOLOGY JSC BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
~~~