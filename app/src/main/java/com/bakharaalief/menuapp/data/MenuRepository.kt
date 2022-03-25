package com.bakharaalief.menuapp.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.bakharaalief.menuapp.BuildConfig
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity
import com.bakharaalief.menuapp.data.local.room.MenuDAO
import com.bakharaalief.menuapp.data.remote.response.StepsItem
import com.bakharaalief.menuapp.data.remote.retorfit.ApiService

class MenuRepository private constructor(
    private val apiService: ApiService,
    private val menuDAO: MenuDAO
) {

    fun searchMenu(keyword: String): LiveData<Result<List<MenuEntity>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.searchMenus(BuildConfig.api_key, false, keyword)
            val result = response.results

            val listMenuEntity = ArrayList<MenuEntity>()

            result.forEach {
                listMenuEntity.add(
                    MenuEntity(
                        it.id,
                        it.image,
                        it.title
                    )
                )
            }

            emit(Result.Success(listMenuEntity))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun menuInstructions(id: Int): LiveData<Result<List<StepsItem>>> = liveData {
        emit(Result.Loading)

        try {
            val response = apiService.menuInstructions(id, BuildConfig.api_key)
            val result = response[0].steps

            emit(Result.Success(result))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getMenuBookmarked(): LiveData<List<MenuEntity>> = menuDAO.getMenus()

    fun isMenuBookmarked(id: Int): LiveData<Boolean> = menuDAO.isMenuBookmarked(id)

    suspend fun insertMenu(menuEntity: MenuEntity) {
        menuDAO.insert(menuEntity)
    }

    suspend fun deleteMenu(menuEntity: MenuEntity) {
        menuDAO.delete(menuEntity)
    }

    companion object {
        @Volatile
        private var instance: MenuRepository? = null

        fun getInstance(
            apiService: ApiService,
            menuDAO: MenuDAO
        ): MenuRepository =
            instance ?: synchronized(this) {
                instance ?: MenuRepository(apiService, menuDAO)
            }.also { instance = it }
    }
}