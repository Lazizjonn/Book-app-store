package uz.gita.bookapp_slp.presentation.viewmodel

import androidx.lifecycle.LiveData
import uz.gita.bookapp_slp.data.model.common.BookAddRequestData
import uz.gita.bookapp_slp.data.model.common.BookResponseData
import uz.gita.bookapp_slp.data.model.request.BookAddRequest


interface MainViewModel {
    val bookListLiveResponseData: LiveData<List<BookResponseData>>
    val favBookListLiveResponseData: LiveData<List<BookResponseData>>
    val uploadSuccessLiveData: LiveData<Boolean>
    val loadSuccessLiveData: LiveData<Boolean>
    val readBookLiveData: LiveData<BookResponseData>

    val isBookFavouriteLiveData: LiveData<Boolean>
    val addBookLoadCounterLiveData: LiveData<Boolean>

    fun getBooksList()

    fun getBooksListDB()

    fun uploadBook(book: BookAddRequest)

    fun loadBook(book: BookResponseData)

    fun readBook(book: BookResponseData)

    fun isBookFavourite(book: BookAddRequestData)

    fun addBookLoadCounter(book: BookAddRequestData)

}