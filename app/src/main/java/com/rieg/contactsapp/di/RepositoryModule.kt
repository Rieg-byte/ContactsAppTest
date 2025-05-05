package com.rieg.contactsapp.di

import com.rieg.contactsapp.data.repository.ContactRepositoryImpl
import com.rieg.contactsapp.domain.repository.ContactRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideContactRepository(
        contactRepository: ContactRepositoryImpl
    ): ContactRepository



}