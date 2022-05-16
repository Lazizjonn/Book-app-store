package uz.gita.bookapp.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.domain.repository.BookRepository
import uz.gita.bookapp.domain.usecase.BookUseCase
import javax.inject.Inject


class BookUseCaseImpl @Inject constructor(private val repository: BookRepository): BookUseCase {

    override fun getBooksList(): Flow<List<BookResponseData>> = flow {
        repository.getBooksList().collect {
            Log.d("TAG", " usecase getBooksList: list get success, " + it[0].toString())
            val temp = it.map { it.toBookData() }
            Log.d("TAG", " usecase getBooksList: list get success, " + temp[0].toString())
            emit(temp)
        }
    }.flowOn(Dispatchers.IO)

    override fun uploadBook(book: BookAddRequest): Flow<Boolean> {
       return repository.uploadBook(book)
    }

    override fun loadBook(book: BookResponseData): Flow<Boolean> = flow {
         repository.loadBook(book.toBookResponse()).collect {
             Log.d("TAG", " usecase loadBook: success, " + it)
             emit(it)
         }
    }

    override fun isBookFavourite(book: BookAddRequest): Flow<Boolean> = flow {
        repository.isBookFavourite(book).collect {
            emit(it)
        }
    }.flowOn(Dispatchers.IO)

    override fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean> = flow {
        repository.addBookLoadCounter(book).collect{
            emit(it)
        }
    }.flowOn(Dispatchers.IO)


}