package com.falconteam.laboratorio_5.data.local

import android.app.Application
import androidx.room.Room

class InitDatabase: Application() {
    companion object{
        lateinit var database: PostDatabase
    }

    override fun onCreate() {
        super.onCreate()

        database = Room.databaseBuilder(
            this,
            PostDatabase::class.java,
            "AppDatabase"
        ).fallbackToDestructiveMigration().build()

    }

}