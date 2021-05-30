package com.darotpeacedude.core.utils

/**
 * Get any name
 * @return
 */
fun Any.getName(): String = this::class.qualifiedName!!
