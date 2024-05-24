package com.falconteam.laboratorio_5.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.falconteam.laboratorio_5.data.local.entities.PostEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM post_table")
    fun observeAll(): Flow<List<PostEntity>>

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(postEntity: PostEntity)

    @Query("UPDATE post_table SET title = :title, description= :description WHERE id = :postId" )
    suspend fun updateSelected(title: String, description: String, postId: String)

    @Query("DELETE FROM post_table Where id = :postId")
    suspend fun deletePostById(postId: String)
}