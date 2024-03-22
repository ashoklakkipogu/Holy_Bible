package com.ashok.bible.domain

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import com.ashok.bible.data.error.DataError
import com.ashok.bible.data.error.RootError

sealed class RequestState<out T> {
    object Idle : RequestState<Nothing>()
    object Loading : RequestState<Nothing>()
    data class Success<T>(val data: T) : RequestState<T>()
    data class Error(val error: RootError) : RequestState<Nothing>()

    fun isLoading() = this is Loading
    fun isSuccess() = this is Success
    fun isError() = this is Error

    /**
     * Returns data from a [Success].
     * @throws ClassCastException If the current state is not [Success]
     *  */
    fun getSuccessData() = (this as Success).data
    fun getSuccessDataOrNull(): T? {
        return try {
            (this as Success).data
        } catch (e: Exception) {
            null
        }
    }

    /**
     * Returns an error message from an [Error]
     * @throws ClassCastException If the current state is not [Error]
     *  */
    fun getErrorMessage() = (this as Error).error
    fun getErrorMessageOrNull(): RootError? {
        return try {
            (this as Error).error
        } catch (e: Exception) {
            null
        }
    }

    @Composable
    fun displayResult(
        onIdle: (@Composable () -> Unit)? = null,
        onLoading: @Composable () -> Unit,
        onSuccess: @Composable () -> Unit,
        onError: @Composable () -> Unit,
    ) {
        AnimatedContent(
            targetState = this,
            transitionSpec = {
                fadeIn(tween(durationMillis = 300)) togetherWith
                        fadeOut(tween(durationMillis = 300))
            },
            label = "Content Animation"
        ) { state ->
            when (state) {
                is Idle -> {
                    onIdle?.invoke()
                }

                is Loading -> {
                    onLoading()
                }

                is Success -> {
                    onSuccess()
                }

                is Error -> {
                    onError()
                }
            }
        }
    }

    @Composable
    fun displayResultNoAnimation(
        onIdle: (@Composable () -> Unit)? = null,
        onLoading: @Composable () -> Unit,
        onSuccess: @Composable () -> Unit,
        onError: @Composable () -> Unit,
    ) {
        when (this) {
            is Idle -> {
                onIdle?.invoke()
            }

            is Loading -> {
                onLoading()
            }

            is Success -> {
                onSuccess()
            }

            is Error -> {
                onError()
            }
        }
    }

}