package uz.gita.bookapp.domain.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.data.model.response.BookResponse


interface BookUseCase {

    fun getBooksList(): Flow<List<BookResponseData>>

    fun uploadBook(book: BookAddRequest): Flow<Boolean>

    fun loadBook(book: BookResponseData): Flow<Boolean>

    fun isBookFavourite(book: BookAddRequest): Flow<Boolean>

    fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean>
}