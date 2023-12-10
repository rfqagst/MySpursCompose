package com.example.submissioncompose

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.submissioncompose.data.PlayerRepo
import com.example.submissioncompose.ui.screen.detail.DetailViewModel
import com.example.submissioncompose.ui.screen.favoriteplayer.FavoriteViewModel
import com.example.submissioncompose.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: PlayerRepo) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(DetailViewModel::class.java)) {
            return DetailViewModel(repository) as T

        } else if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
            return FavoriteViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}