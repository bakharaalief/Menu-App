package com.bakharaalief.menuapp.di

import android.content.Context
import com.bakharaalief.menuapp.data.MenuRepository
import com.bakharaalief.menuapp.data.local.room.MenuDB
import com.bakharaalief.menuapp.data.remote.retorfit.ApiConfig

object Injection {
    fun provideRepository(context: Context): MenuRepository {
        val apiService = ApiConfig.getApiService()
        val menuDao = MenuDB.getInstance(context).menuDao()
        return MenuRepository.getInstance(apiService, menuDao)
    }
}