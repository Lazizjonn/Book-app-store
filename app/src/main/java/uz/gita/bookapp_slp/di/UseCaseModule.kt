package uz.gita.bookapp_slp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp_slp.domain.usecase.BookUseCase
import uz.gita.bookapp_slp.domain.usecase.FavUseCase
import uz.gita.bookapp_slp.domain.usecase.impl.BookUseCaseImpl
import uz.gita.bookapp_slp.domain.usecase.impl.FavUseCaseImpl
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