package uz.gita.bookapp.usecase

import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.data.model.response.BookResponse


interface FavUseCase {

    fun getFavouriteBooksList(): Flow<List<BookResponseData>>

    fun loadBook(book: BookResponseData): Flow<Boolean>

    fun isBookFavourite(book: BookAddRequest): Flow<Boolean>

    fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean>
}