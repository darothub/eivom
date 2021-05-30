package com.darotpeacedude.data.local

import com.darotpeacedude.data.utils.Parent
import java.io.Serializable

data class ResultList (
    val page: Long,
    val results: List<Result>
): Serializable, Parent