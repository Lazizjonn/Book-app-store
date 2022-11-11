package uz.gita.bookapp_slp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp_slp.data.model.common.BookResponseData
import uz.gita.bookapp_slp.data.model.request.BookAddRequest


interface BookUseCase {

    fun getBooksList(): Flow<List<BookResponseData>>

    fun getBooksListDB(): Flow<List<BookResponseData>>

    fun getFavouriteBooksListDB(): Flow<List<BookResponseData>>

    fun uploadBook(book: BookAddRequest): Flow<Boolean>

    fun loadBook(book: BookResponseData): Flow<Boolean>

    fun isBookFavourite(book: BookAddRequest): Flow<Boolean>

    fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean>
}