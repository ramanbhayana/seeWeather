package com.example.seeweather.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.seeweather.db.dao.WeatherDao
import com.example.seeweather.db.entity.WeatherEntity

@Database(entities = [WeatherEntity::class], version = 1, exportSchema = false)
abstract class WeatherDataBase : RoomDatabase() {
    companion object {
        @Volatile
        private var sInstance: WeatherDataBase? = null

        @Synchronized
        fun getInstance(context: Context): WeatherDataBase? {
            if (sInstance == null) {
                synchronized(WeatherDataBase::class.java) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(
                            context,
                            WeatherDataBase::class.java,
                            "WeatherDataBase"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return sInstance
        }
    }

    abstract fun weatherDao(): WeatherDao?
}