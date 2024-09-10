package com.hongstudio.marvelcharacters.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterLocal::class], version = 1)
abstract class FavoriteDatabase : RoomDatabase() {

    abstract fun favoriteDao(): FavoriteDao
}
