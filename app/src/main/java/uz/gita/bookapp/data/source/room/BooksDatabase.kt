package uz.gita.bookapp.data.source.room

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.gita.bookapp.data.source.room.dao.BookDao
import uz.gita.bookapp.data.source.room.entity.BookEntity

@Database(entities = [BookEntity::class], version = 1)
abstract class BooksDatabase: RoomDatabase() {
    abstract fun dao(): BookDao
}
