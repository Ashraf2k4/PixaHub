package com.progress.photos.pixahub.mvvm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.progress.photos.pixahub.apipack.Hit

class SharedViewModel : ViewModel()
{
    var selectedCategory by mutableStateOf<Hit?>(null)
        private set

    fun selectCategory(category: Hit) {
        selectedCategory = category
    }

    private var _searchFocus by mutableStateOf(false)
    val searchFocus: Boolean get() = _searchFocus

    fun setSearchFocus(focus: Boolean) {
        _searchFocus = focus
    }

    private var _searchText by mutableStateOf("")
    val searchText: String get() = _searchText

    fun setSearchText(search : String) {
        _searchText = search
    }

    private var _path by mutableStateOf("")
    val pathValue: String get() = _path

    fun setPath(path : String) {
        _path = path
    }

    private var _filter by mutableStateOf(false)
    val fetchFilter : Boolean get() = _filter

    fun setFilter(path : Boolean) {
        _filter = path
    }

    private var _orientation by mutableStateOf("Landscape Images")
    val orientationType: String get() = _orientation

    fun setOrientation(search : String) {
        _orientation = search
    }

    private var _view by mutableStateOf("GridView")
    val viewType: String get() = _view

    fun setView(search : String) {
        _view = search
    }

}