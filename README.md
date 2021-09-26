# GapoRichText

**GapoRichText** supports Hashtag(#), Mention(@), URLs, Phone Number, Email, Markdown, Custom Span, SeeMore/SeeLess and ability to handle clicks and long clicks on Spanned content.

## Installation

Gradle
```gradle
implementation 'com.github.hantrungkien:gapo-richtext:1.0.0-alpha01'
```

## Usage

[Example](/app/src/main/java/com/gg/gapo/richtext/example/MainActivity.kt):

```kotlin
val color = Color.parseColor("#30A960")

val richText = GapoRichText.Builder()
    .setOriginal(text)
    .addSpanner(
       GapoRichTextMetadataSpanner.Params()
           .setForegroundColor(color)
           .setUnderline(true)
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
    .build()

binding.textView.movementMethod = GapoRichTextLinkMovementMethod.instance
binding.textView.text = richText.spannable
```

## Supported Case
- Hashtag: `#hashtag`
```kotlin
.addSpanner(
    GapoRichTextMetadataSpanner.Params()
        .setMetadataParser(GapoRichTextHashtagMetadataParser)
        .create()
)
```
- Mention: `@mention`
```kotlin
val mentionMetadata = listOf(
    GapoRichTextMetadata("kienht", 0, 14),
    GapoRichTextMetadata("vietth", 15, 30),
)
.addSpanner(
    GapoRichTextMetadataSpanner.Params()
        .setMetadata(mentionMetadata)
        .create()
)
```
- URLs
```kotlin
.addSpanner(
    GapoRichTextMetadataSpanner.Params()
        .setMetadataParser(GapoRichTextUrlMetadataParser)
        .create()
)
```
- Phone Number
```kotlin
.addSpanner(
    GapoRichTextMetadataSpanner.Params()
        .setMetadataParser(GapoRichTextPhoneNumberMetadataParser)
        .create()
)
```
- Email
```kotlin
.addSpanner(
    GapoRichTextMetadataSpanner.Params()
        .setMetadataParser(GapoRichTextEmailMetadataParser)
        .create()
)
```
- Markdown
```kotlin
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
.addSpanner(GapoRichTextMarkdownSpanner(markwon))
```
- SeeMore/SeeLess
```kotlin
val seeMore = " ...Xem thêm"

val richText = GapoRichText.Builder()
    .setOriginal(text)
    .addSpanner(...)
    .setSeeMoreType(
        GapoRichTextSeeMoreType.Line(
            seeMore = GapoRichTextMetadataSpanner.Params()
                .setForegroundColor(color)
                .setMetadata(listOf(GapoRichTextMetadata(seeMore, 4, seeMore.length)))
                .create()
                .span(seeMore),
            line = 24,
            measurementParams = GapoTextMeasurement.Params.Builder().from(binding.text).build()
        )
    )
binding.textView.text = richText.seeMoreSpannable
```