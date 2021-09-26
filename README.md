# GapoRichText

**GapoRichText** supports Hashtag(#), Mention(@), URLs, Phone Number, Email, Markdown, Custom Span, SeeMore/SeeLess and ability to handle clicks and long clicks on Spanned content.

## Installation

Gradle
```gradle
implementation 'com.gg.gapo.richtext:GapoRichText:1.0.0'
```

## Usage

[Example](https://github.com/hantrungkien/GapoRichText/blob/feature%2Flong-click-span/app/src/main/java/com/gg/gapo/richtext/example/MainActivity.kt):

```kotlin
val richText = GapoRichText.Builder()
    .setOriginal(text)
    .addSpanner(
        GapoRichTextMetadataSpanner.Params()
            .setBackgroundColor(Color.GREEN)
            .setForegroundColor(Color.MAGENTA)
            .setStrikethrough(true)
            .setUnderline(true)
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

binding.textView.text = richText.spannable
```

## Supported Case
- Hashtag: `#hashtag`
```kotlin
.setMetadata(GapoRichTextHashtagMetadataParser.parse(text))
```
- Mention: `@mention`
```kotlin
.setMetadata(GapoRichTextUrlMetadataParser.parse(text))
```
- URLs
```kotlin
.setMetadata(GapoRichTextUrlMetadataParser.parse(text))
```
- Phone Number
```kotlin
.setMetadata(GapoRichTextPhoneNumberMetadataParser.parse(text))
```
- Email
```kotlin
.setMetadata(GapoRichTextEmailMetadataParser.parse(text))
```
- Markdown
```kotlin
.setMetadata(GapoRichTextUrlMetadataParser.parse(text))
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
                .setForegroundColor(Color.GREEN)
                .setMetadata(listOf(GapoRichTextMetadata(seeMore, 4, seeMore.length)))
                .create()
                .span(seeMore),
            line = 3,
            measurementParams = GapoTextMeasurement.Params.Builder().from(binding.text).build()
        )
    )
binding.textView.text = richText.shortSpannable
```