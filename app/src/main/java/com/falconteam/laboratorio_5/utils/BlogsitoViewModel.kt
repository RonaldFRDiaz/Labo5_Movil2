package com.falconteam.laboratorio_5.utils

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.falconteam.laboratorio_5.data.local.InitDatabase
import com.falconteam.laboratorio_5.data.local.entities.PostEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch

class BlogsitoViewModel : ViewModel() {
    private val database = InitDatabase.database
    private val _listPosts = MutableStateFlow<List<PostEntity>>(emptyList())
    val listPosts = _listPosts.asStateFlow()
    val showModal = mutableStateOf(false)
    val newPostTitle = mutableStateOf("")
    val newPostDescription = mutableStateOf("")

    fun showModal() {
        showModal.value = !showModal.value
    }

    private fun cleanFields() {
        newPostDescription.value = ""
        newPostTitle.value = ""
    }

init{
    getPosts()
}

private fun getPosts() {
    viewModelScope.launch(Dispatchers.IO){
        val result = database.postDao().observeAll().firstOrNull()

        if (result?.isEmpty() == true) {
            return@launch
        }
        database.postDao().observeAll().collect {
            listPosts -> _listPosts.value = listPosts
        }
    }
}

fun addNewPost() {
    if (newPostDescription.value.isEmpty()) {
        return
    }

    if (newPostTitle.value.isEmpty()) {
        return
    }

    val newPost = PostEntity(
        title = newPostTitle.value,
        description = newPostDescription.value,
        author = "EnseMirro"
    )

    viewModelScope.launch(Dispatchers.IO) {
        database.postDao().insertPost(newPost)
        getPosts()
    }
    cleanFields()
}

    fun updatePost(title: String, description: String, id: String) {
        viewModelScope.launch {
            database.postDao().updateSelected(title, description, id)
        }
    }

    fun deletePost(postId: String) {
        viewModelScope.launch {
            database.postDao().deletePostById(postId)
        }
    }

}