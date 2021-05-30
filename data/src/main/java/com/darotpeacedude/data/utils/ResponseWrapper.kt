package com.darotpeacedude.data.utils


/**
 * A sealed class to control response
 */
sealed class ResponseWrapper<T>(
    val data: T? = null,
    val message: String? = null,
    val code: Int? = null
):Parent  {
    /**
     * A success class wrapper
     */
    class Success<T>(data: T?) : ResponseWrapper<T>(data)
    /**
     * A loading class wrapper
     */
    class Loading<T>(data: T? = null, message: String) : ResponseWrapper<T>(data, message)
    /**
     * An error class wrapper
     */
    class Error<T>(message: String?, code: Int? = null, data: T? = null) : ResponseWrapper<T>(data, message, code)
    class Network<T>(code: Int? = null, message: String?, data: T? = null) : ResponseWrapper<T>(data, message, code)
    class Empty<T> : ResponseWrapper<T>()
}

class EmptyHolder:Parent
interface Parent