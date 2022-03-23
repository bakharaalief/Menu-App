package com.bakharaalief.menuapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bakharaalief.menuapp.BuildConfig
import com.bakharaalief.menuapp.data.remote.response.ResultsItem
import com.bakharaalief.menuapp.data.remote.retorfit.ApiService

class MenuRepository private constructor(
    private val apiService: ApiService
) {

    fun searchMenu(keyword: String): LiveData<Result<List<ResultsItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.searchMenus(BuildConfig.api_key, false, keyword)
            val result = response.results

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null

        fun getInstance(
            apiService: ApiService,
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(apiService)
            }.also { instance = it }
    }
}