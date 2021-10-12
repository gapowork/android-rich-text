package com.gapo.richtext.parser.url

import com.gapo.richtext.parser.RichTextMetadataParser
import com.gapo.richtext.parser.RichTextMetadataParserCache
import com.gapo.richtext.parser.RichTextMetadataParserSimpleCache

/**
 * @author kienht
 * @since 26/09/2021
 */
class RichTextUrlMetadataParser(
    regex: Regex = Regex(
        "(work.gapo://)(\\S+)|(http://www.|https://www.|http://|https://|www.|[a-zA-Z0-9].)?[a-zA-Z0-9-]{1,}\\.?[a-z0-9-]{1,}\\.(?:ac|ad|aero|ae|af|ag|ai|al|am|an|ao|aq|arpa|ar|asia|as|at|au|aw|ax|az|ba|bb|bd|be|bf|bg|bh|biz|bi|blog|bj|bm|bn|bo|br|bs|bt|buzz|bv|bw|by|bz|cat|ca|cc|cd|cf|cg|ch|ci|ck|cl|cm|cn|coop|com|co|cr|cu|cv|cx|cy|cz|dev|de|dj|dk|dm|do|dz|ec|edu|ee|eg|engineering|er|es|et|eu|fi|fj|fk|fm|fo|fr|ga|gb|gd|ge|gf|gg|gh|gi|gle|gl|gm|gn|gov|gp|gq|gr|gs|gt|gu|gw|gy|hk|hm|hn|hr|ht|hu|id|ie|il|im|info|int|in|io|iq|ir|is|it|je|jm|jobs|jo|jp|ke|kg|kh|ki|km|kn|kp|kr|kw|ky|kz|la|lb|lc|link|li|lk|lr|ls|lt|lu|lv|ly|ma|mc|md|me|mg|mh|mil|mk|ml|mm|mn|mobi|mo|mp|mq|mr|ms|mt|museum|mu|mv|mw|mx|my|mz|name|na|nc|net|ne|nf|ng|ni|nl|no|np|nr|nu|nz|om|org|pa|pe|pf|pg|ph|pk|pl|pm|pn|pro|pr|ps|pt|pw|py|qa|re|ro|rs|ru|rw|sa|sb|sc|sd|se|sg|sh|si|sj|sk|sl|sm|sn|so|sr|st|su|sv|sy|sz|tc|td|tel|tf|tg|th|tj|tk|tl|tm|tn|top|to|tp|travel|tr|tt|tv|tw|tz|ua|ug|uk|um|us|uy|uz|viva|va|vc|ve|vg|vi|vn|vu|wf|ws|wiki|weibo|work|yahoo|yandex|ye|yoga|youtube|you|yt|yu|za|zip|zm|zw)(:[0-9]{1,5})?(/[\\S&&[^\\s(),]]+)?"
    ),
    cache: RichTextMetadataParserCache = RichTextMetadataParserSimpleCache
) : RichTextMetadataParser(regex, cache)