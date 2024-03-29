package com.example.currencyapp

import androidx.annotation.IntDef
import java.lang.Exception

class Resource<out T>(
    @Status val status: Int,
    val data: T?,
    val error: Exception?
) {

    companion object {
        const val SUCCESS = 0
        const val ERROR = 1
        const val LOADING = 2

        @IntDef(SUCCESS, ERROR, LOADING)
        @Retention(AnnotationRetention.SOURCE)
        annotation class Status

        fun <T> success(data: T?) = Resource(SUCCESS, data, null)

        fun <T> error(error: Exception) = Resource<T>(ERROR, null, error)

        fun <T> errorWithPayload(data: T) = Resource(ERROR, data, null)

        fun <T> loading() = Resource<T>(LOADING, null, null)
    }
}