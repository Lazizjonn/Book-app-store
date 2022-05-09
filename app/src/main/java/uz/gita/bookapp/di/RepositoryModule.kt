package uz.gita.bookapp.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp.domain.repository.BookRepository
import uz.gita.bookapp.domain.repository.impl.BookRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getRepository(impl: BookRepositoryImpl):BookRepository


}