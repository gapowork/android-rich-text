# GapoRichText

**GapoRichText** supports Hashtag(#), Mention(@), URLs, Phone, Email, Markdown, Custom Span, SeeMore/SeeLess and ability to handle clicks and long clicks on Spanned content.

## Installation

Gradle
```gradle
implementation 'com.gg.gapo.richtext:GapoRichText:1.0.0'
```

## Usage

Example:

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
.setMetadata(GapoRichTextHashtagMetadataParser.parse(text))
```
- URLs
- Phone
- Email
- Markdown
- SeeMore/SeeLess