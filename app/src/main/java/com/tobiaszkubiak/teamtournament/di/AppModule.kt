package com.tobiaszkubiak.teamtournament.di

import com.tobiaszkubiak.teamtournament.data.datasources.GroupDataSource
import com.tobiaszkubiak.teamtournament.data.datasources.UserDataSource
import com.tobiaszkubiak.teamtournament.data.repository.GroupRepository
import com.tobiaszkubiak.teamtournament.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideUserDataSource(): UserDataSource {
        return UserDataSource()
    }

    @Provides
    @Singleton
    fun provideUserRepository(dataSource: UserDataSource): UserRepository {
        return UserRepository(dataSource)
    }

    @Provides
    @Singleton
    fun provideGroupDataSource(): GroupDataSource {
        return GroupDataSource()
    }

    @Provides
    @Singleton
    fun provideGroupRepository(dataSource: GroupDataSource): GroupRepository {
        return GroupRepository(dataSource)
    }
}