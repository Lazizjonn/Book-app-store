package uz.gita.bookapp_slp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp_slp.domain.repository.BookRepository
import uz.gita.bookapp_slp.domain.repository.impl.BookRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    @Singleton
    fun getRepository(impl: BookRepositoryImpl):BookRepository


}