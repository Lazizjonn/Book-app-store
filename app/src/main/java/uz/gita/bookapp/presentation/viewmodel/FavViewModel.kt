package uz.gita.bookapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import uz.gita.bookapp.data.model.common.BookAddRequestData
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest


interface FavViewModel {
    val bookListLiveResponseData: LiveData<List<BookResponseData>>
    val loadSuccessLiveData: LiveData<Boolean>
    val readBookLiveData: LiveData<BookResponseData>

    val isBookFavouriteLiveData: LiveData<Boolean>
    val addBookLoadCounterLiveData: LiveData<Boolean>

    fun getFavouriteBooksList()

    fun loadBook(book: BookResponseData)

    fun readBook(book: BookResponseData)

    fun isBookFavourite(book: BookAddRequestData)

    fun addBookLoadCounter(book: BookAddRequestData)

}