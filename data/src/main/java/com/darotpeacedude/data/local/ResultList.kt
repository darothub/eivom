package com.darotpeacedude.data.local

import java.io.Serializable

data class ResultList (
    val page: Long,
    val results: List<Result>
): Serializable