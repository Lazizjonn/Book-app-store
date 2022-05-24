package uz.gita.bookapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import uz.gita.bookapp.data.source.room.BooksDatabase
import uz.gita.bookapp.data.source.room.dao.BookDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule{

    @[Provides Singleton]
    fun database( @ApplicationContext context: Context): BooksDatabase =
        Room.databaseBuilder(context, BooksDatabase::class.java, "bppks.db")
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries().build()

    @[Provides Singleton]
    fun dao(booksDatabase: BooksDatabase): BookDao = booksDatabase.dao()

}
