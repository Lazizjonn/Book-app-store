package uz.gita.bookapp_slp.data.source.room.dao

import androidx.room.*
import uz.gita.bookapp_slp.data.source.room.entity.BookEntity

@Dao
interface BookDao {
    @Query("SELECT * from books")
    fun getAllBooks(): List<BookEntity>

    @Query("SELECT * from books where fav = 1")
    fun getAllFavBooks(): List<BookEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertBooks(books: List<BookEntity>)

    @Query("DELETE from books where id > 0")
    fun deleteBooks()

    @Update
    fun updateBook(book: BookEntity)
}