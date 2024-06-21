package com.android.airticketssearchapp.ui.utils

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PriceVisualTransformation : VisualTransformation {
    private val priceOffsetTranslator = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            if (offset <= 2) return offset
            return offset + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            if (offset <= 3) return offset
            return offset - 1
        }
    }

    override fun filter(text: AnnotatedString): TransformedText {
        val out = StringBuilder()
        text.text.forEachIndexed { index, char ->
            out.append(char)
            if (index == text.lastIndex - 3) out.append(SEPARATOR)
        }
        return TransformedText(AnnotatedString(out.toString()), priceOffsetTranslator)
    }

    companion object {
        private const val SEPARATOR = " "
    }
}