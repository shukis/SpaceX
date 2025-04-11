package com.spaceix.data.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    entities = [FavouriteLaunchDbEntity::class, FavouriteRocketDbEntity::class],
    version = 1
)
@TypeConverters(
    StringListTypeConverter::class
)
@ConstructedBy(FavouriteDatabaseConstructor::class)
abstract class FavoriteDatabase : RoomDatabase() {
    abstract val favoriteLaunchesDao: FavouriteLaunchesDao
    abstract val favoriteRocketsDao: FavouriteRocketsDao

    companion object {
        const val DB_NAME = "favourites.db"
    }
}