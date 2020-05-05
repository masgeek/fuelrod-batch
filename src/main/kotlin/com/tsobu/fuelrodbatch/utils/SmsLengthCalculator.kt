package com.tsobu.fuelrodbatch.utils

import java.util.*

/**
 * @author MarkoSulamagi
 * @author https://github.com/messente/sms-length-calculator
 *
 */
class SmsLengthCalculator {
    fun getCharset(content: String): Int {
        for (i in 0 until content.length) {
            if (!GSM7BIT.contains(Character.toString(content[i]))) {
                if (!GSM7BITEXT.contains(Character.toString(content[i]))) {
                    return GSM_CHARSET_UNICODE
                }
            }
        }
        return GSM_CHARSET_7BIT
    }

    private fun getPartCount7bit(content: String): Int {
        val content7bit = StringBuilder()

        // Add escape characters for extended charset
        for (i in 0 until content.length) {
            if (!GSM7BITEXT.contains(content[i].toString() + "")) {
                content7bit.append(content[i])
            } else {
                content7bit.append('\u001b')
                content7bit.append(content[i])
            }
        }
        return if (content7bit.length <= 160) {
            1
        } else {

            // Start counting the number of messages
            var parts = Math.ceil(content7bit.length / 153.0).toInt()
            val free_chars = content7bit.length - Math.floor(content7bit.length / 153.0).toInt() * 153

            // We have enough free characters left, don't care about escape character at the end of sms part
            if (free_chars >= parts - 1) {
                return parts
            }

            // Reset counter
            parts = 0
            while (content7bit.length > 0) {

                // Advance sms counter
                parts++

                // Check for trailing escape character
                if (content7bit.length >= 152 && content7bit[152] == GSM_7BIT_ESC) {
                    content7bit.delete(0, 152)
                } else {
                    content7bit.delete(0, 153)
                }
            }
            parts
        }
    }

    fun getPartCount(content: String): Int {
        val charset = getCharset(content)
        if (charset == GSM_CHARSET_7BIT) {
            return getPartCount7bit(content)
        } else if (charset == GSM_CHARSET_UNICODE) {
            return if (content.length <= 70) {
                1
            } else {
                Math.ceil(content.length / 67.0).toInt()
            }
        }
        return -1
    } // getPartCount

    companion object {
        const val GSM_CHARSET_7BIT = 0
        const val GSM_CHARSET_UNICODE = 2
        private const val GSM_7BIT_ESC = '\u001b'
        private val GSM7BIT: Set<String> = HashSet(Arrays.asList(
                *arrayOf(
                        "@", "£", "$", "¥", "è", "é", "ù", "ì", "ò", "Ç", "\n", "Ø", "ø", "\r", "Å", "å",
                        "Δ", "_", "Φ", "Γ", "Λ", "Ω", "Π", "Ψ", "Σ", "Θ", "Ξ", "\u001b", "Æ", "æ", "ß", "É",
                        " ", "!", "'", "#", "¤", "%", "&", "\"", "(", ")", "*", "+", ",", "-", ".", "/",
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", ":", ";", "<", "=", ">", "?",
                        "¡", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
                        "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", "Ä", "Ö", "Ñ", "Ü", "§",
                        "¿", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o",
                        "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z", "ä", "ö", "ñ", "ü", "à"
                )
        ))
        private val GSM7BITEXT: Set<String> = HashSet(Arrays.asList(
                *arrayOf(
                        "^", "{", "}", "\\", "[", "~", "]", "|", "€"
                )
        ))
    }
} // SmsLengthCalculator
