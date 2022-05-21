package uz.gita.bookapp.domain.usecase.impl

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.domain.repository.BookRepository
import uz.gita.bookapp.domain.usecase.FavUseCase
import javax.inject.Inject


class FavUseCaseImpl @Inject constructor(private val repository: BookRepository): FavUseCase {

    override fun getFavouriteBooksList(): Flow<List<BookResponseData>> =  repository.getFavouriteBooksList()



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