package uz.gita.bookapp_slp.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.bookapp_slp.data.model.common.BookResponseData
import uz.gita.bookapp_slp.data.model.request.BookAddRequest
import uz.gita.bookapp_slp.domain.repository.BookRepository
import uz.gita.bookapp_slp.domain.usecase.BookUseCase
import javax.inject.Inject


class BookUseCaseImpl @Inject constructor(private val repository: BookRepository): BookUseCase {

    override fun getBooksList(): Flow<List<BookResponseData>> = flow {
        repository.getBooksList().collect {
            Log.d("TAG", " usecase getBooksList: list get success, ")
            val temp = it.map { it.toBookData() }
            emit(temp)
        }
    }.flowOn(Dispatchers.IO)

    override fun getBooksListDB(): Flow<List<BookResponseData>> = flow {
        repository.getBooksListDB().collect {
            Log.d("TAG", " usecase getBooksList: list DB, ")
            val temp = it.map { it.toBookData() }
            emit(temp)
        }
    }.flowOn(Dispatchers.IO)

    override fun getFavouriteBooksListDB(): Flow<List<BookResponseData>> = flow {
        repository.getFavouriteBooksListDB().collect {
            Log.d("TAG", " usecase getBooksList: list DB, ")
            val temp = it.map { it.toBookData() }
            emit(temp)
        }
    }.flowOn(Dispatchers.IO)

    override fun uploadBook(book: BookAddRequest): Flow<Boolean> = repository.uploadBook(book)

    override fun loadBook(book: BookResponseData): Flow<Boolean> = flow {
         repository.loadBook(book.toBookResponse()).collect {
             Log.d("TAG", " usecase loadBook: success, " + it)
             emit(it)
         }
    }.flowOn(Dispatchers.IO)

    override fun isBookFavourite(book: BookAddRequest): Flow<Boolean> = flow {
        repository.isBookFavouriteDB(book).collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    override fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean> = flow {
        repository.addBookLoadCounter(book).collect{
            emit(it)
        }
    }.flowOn(Dispatchers.IO)


}