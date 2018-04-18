package io.userfeeds.parityapi

fun String.hexToLong() = removePrefix("0x").toLong(16)

fun Long.longToHex() = "0x${toString(16)}"
