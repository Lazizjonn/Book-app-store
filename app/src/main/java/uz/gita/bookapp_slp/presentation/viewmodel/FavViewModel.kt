package uz.gita.bookapp_slp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.bookapp_slp.data.model.common.BookAddRequestData
import uz.gita.bookapp_slp.data.model.common.BookResponseData


interface FavViewModel {
    val bookListLiveResponseData: LiveData<List<BookResponseData>>
    val loadSuccessLiveData: LiveData<Boolean>
    val readBookLiveData: LiveData<BookResponseData>

    val isBookFavouriteLiveData: LiveData<Boolean>
    val addBookLoadCounterLiveData: LiveData<Boolean>

    fun getFavouriteBooksListDB()

    fun loadBook(book: BookResponseData)

    fun readBook(book: BookResponseData)

    fun isBookFavourite(book: BookAddRequestData)

    fun addBookLoadCounter(book: BookAddRequestData)

}