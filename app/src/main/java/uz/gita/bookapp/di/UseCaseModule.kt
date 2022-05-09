package uz.gita.bookapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp.domain.repository.BookRepository
import uz.gita.bookapp.usecase.BookUseCase
import uz.gita.bookapp.usecase.FavUseCase
import uz.gita.bookapp.usecase.impl.BookUseCaseImpl
import uz.gita.bookapp.usecase.impl.FavUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    @Singleton
    fun getBookUseCase(impl: BookUseCaseImpl): BookUseCase


    @Binds
    @Singleton
    fun getFavUseCase(impl: FavUseCaseImpl): FavUseCase
}