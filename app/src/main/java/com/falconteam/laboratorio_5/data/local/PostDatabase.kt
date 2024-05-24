package com.falconteam.laboratorio_5.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.falconteam.laboratorio_5.data.local.dao.PostDao
import com.falconteam.laboratorio_5.data.local.entities.PostEntity

@Database(
    entities = [PostEntity::class],
    version = 2)
    abstract class PostDatabase : RoomDatabase() {

        abstract fun postDao(): PostDao
    }

