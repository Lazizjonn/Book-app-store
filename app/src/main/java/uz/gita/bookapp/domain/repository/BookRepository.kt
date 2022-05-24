package uz.gita.bookapp.domain.repository

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.data.model.response.BookResponse


interface BookRepository {

    fun getBooksList(): Flow<List<BookResponse>>

    fun getBooksListDB(): Flow<List<BookResponse>>

//    fun getFavouriteBooksList(): Flow<List<BookResponse>>

    fun getFavouriteBooksListDB(): Flow<List<BookResponse>>

    fun uploadBook(book: BookAddRequest): Flow<Boolean>

    fun loadBook(book: BookResponse): Flow<Boolean>

//    fun isBookFavourite(book: BookAddRequest): Flow<Boolean>

    fun isBookFavouriteDB(book: BookAddRequest): Flow<Boolean>

    fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean>

}