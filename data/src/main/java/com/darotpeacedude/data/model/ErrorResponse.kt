package com.darotpeacedude.data.model

import com.darotpeacedude.data.utils.Parent
import java.io.Serializable

data class ErrorResponse (
    val statusMessage: String,
    val success: Boolean,
    val statusCode: Long
):Parent, Serializable