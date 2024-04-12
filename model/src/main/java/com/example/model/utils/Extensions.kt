package com.example.model.utils


fun String?.orEmptyString() = this ?: ""

fun String.visualFormat(delimiter: String) = split(delimiter).joinToString(" ") { string ->
    string.replaceFirstChar { firstChar -> firstChar.uppercase() }
}

fun String.abbreviate(n: Int) =
    split(" ").joinToString(". ") { it.take(n) }.plus(".")