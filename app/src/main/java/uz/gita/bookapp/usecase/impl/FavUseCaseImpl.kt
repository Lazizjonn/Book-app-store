package uz.gita.bookapp.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.domain.repository.BookRepository
import uz.gita.bookapp.usecase.BookUseCase
import uz.gita.bookapp.usecase.FavUseCase
import javax.inject.Inject


class FavUseCaseImpl @Inject constructor(private val repository: BookRepository): FavUseCase {

    override fun getFavouriteBooksList(): Flow<List<BookResponseData>> = flow {
        repository.getFavouriteBooksList().collect {
            Log.d("TAG", " usecase getBooksList: list get success, " + it[0].toString())
            val temp = it.map { it.toBookData() }
            Log.d("TAG", " usecase getBooksList: list get success, " + temp[0].toString())
            emit(temp)
        }
    }.flowOn(Dispatchers.IO)

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