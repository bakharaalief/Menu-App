package com.bakharaalief.menuapp.data

sealed class Result<out T>() {
    class Success<T>(val data : T) : Result<T>()
    class Error(val data : String) : Result<Nothing>()
    object Loading : Result<Nothing>()
}
