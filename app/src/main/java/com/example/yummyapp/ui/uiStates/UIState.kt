package com.example.yummyapp.ui.uiStates

sealed class UIState<out T> {
    object Loading : UIState<Nothing>()
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val exception: Throwable) : UIState<Nothing>()
}