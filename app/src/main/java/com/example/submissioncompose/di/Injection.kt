package com.example.submissioncompose.di

import com.example.submissioncompose.data.PlayerRepo

object Injection {
    fun provideRepository(): PlayerRepo {
        return PlayerRepo.getInstance()
    }
}