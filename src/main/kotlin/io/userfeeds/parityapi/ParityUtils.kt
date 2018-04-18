package io.userfeeds.parityapi

fun String.hexToLong() = removePrefix("0x").toLong(16)

val Block.numberHex: String get() = first.longToHex()

fun Long.longToHex() = "0x${toString(16)}"
