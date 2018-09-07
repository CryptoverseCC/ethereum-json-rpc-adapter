package io.userfeeds.parityapi

import java.nio.charset.Charset

fun String.hexToLong() = removePrefix("0x").toLong(16)

fun Long.longToHex() = "0x${toString(16)}"

fun String.hexToString() = chunked(2).map { it.toInt(16).toByte() }.toByteArray().toString(Charset.defaultCharset())

fun String.getStringAtIndex(index: Int): String {
    val withoutPrefix = removePrefix("0x")
    val indexOfStringLength = withoutPrefix.substring(64 * index, 64 * (index + 1)).toInt(16)
    val start = 2 * indexOfStringLength + 64
    val length = withoutPrefix.substring(2 * indexOfStringLength, 2 * indexOfStringLength + 64).toInt(16)
    val hex = withoutPrefix.substring(start, start + 2 * length)
    return hex.hexToString()
}
