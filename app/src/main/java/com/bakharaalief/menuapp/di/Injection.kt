package com.bakharaalief.menuapp.di

import android.content.Context
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.data.remote.retorfit.ApiConfig

object Injection {
    fun provideRepository(context: Context): MenuRepository {
        val apiService = ApiConfig.getApiService()
        return MenuRepository.getInstance(apiService)
    }
}