package com.progress.photos.pixahub.mvvm

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.progress.photos.pixahub.apipack.Hit
import com.progress.photos.pixahub.apipack.pixaBayService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel()
{

    private val apiKey = "Add your api key here"

    data class  ImageState(
        val loading : Boolean = true,
        val list :List<Hit> = emptyList(),
        val error : String? = null
    )

    private val _imageState = mutableStateOf(ImageState())

    val imageState : State<ImageState> = _imageState

    private var isFetching by mutableStateOf(true)

    fun fetchImagesIfNeeded(items: List<String>) {
        if (isFetching) {
            fetchMultipleImages(items)
            isFetching = false
        }
    }


    private fun fetchMultipleImages(queries: List<String>) {
        viewModelScope.launch {
            _imageState.value = ImageState(loading = true)

            for (query in queries) {
                try {
                    val response = pixaBayService.getImages(apikey = apiKey, query = query)

                    val updatedList = _imageState.value.list.shuffled().toMutableList()
                    updatedList.addAll(response.hits)

                    if (response.hits.isNotEmpty()) {
                        _imageState.value = _imageState.value.copy(
                            list = updatedList,
                            loading = false,
                            error = null
                        )
                    }
                    else{
                        _imageState.value = _imageState.value.copy(
                            loading = false,
                            error ="No images found"
                        )
                    }
                } catch (e: Exception) {
                    _imageState.value = _imageState.value.copy(
                        loading = false,
                        error = "Error fetching images :\n ${e.message}"
                    )
                }
            }
        }
    }

}
