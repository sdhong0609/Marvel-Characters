package com.hongstudio.marvelcharacters.di

import com.hongstudio.marvelcharacters.data.CharacterRepository
import com.hongstudio.marvelcharacters.data.DefaultCharacterRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindCharacterRepository(repository: DefaultCharacterRepository): CharacterRepository
}
