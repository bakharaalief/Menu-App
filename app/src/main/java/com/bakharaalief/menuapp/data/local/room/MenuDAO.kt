package com.bakharaalief.menuapp.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bakharaalief.menuapp.data.local.enitity.MenuEntity

@Dao
interface MenuDAO {
    @Query("Select * from menus")
    fun getMenus(): LiveData<List<MenuEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(menuEntity: MenuEntity)

    @Delete
    suspend fun delete(menuEntity: MenuEntity)

    @Query("SELECT EXISTS(SELECT * FROM menus WHERE id = :id)")
    fun isMenuBookmarked(id: Int): LiveData<Boolean>
}