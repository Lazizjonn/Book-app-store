package uz.gita.bookapp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.gita.bookapp.data.model.common.BookAddRequestData
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.presentation.viewmodel.MainViewModel
import uz.gita.bookapp.usecase.BookUseCase
import javax.inject.Inject

@HiltViewModel
class MainViewModelImpl @Inject constructor(private val bookUseCase: BookUseCase): ViewModel(), MainViewModel {
    override val bookListLiveResponseData = MutableLiveData<List<BookResponseData>>()
    override val uploadSuccessLiveData = MutableLiveData<Boolean>()
    override val loadSuccessLiveData = MutableLiveData<Boolean>()
    override val readBookLiveData = MutableLiveData<BookResponseData>()
    override val isBookFavouriteLiveData = MutableLiveData<Boolean>()
    override val addBookLoadCounterLiveData = MutableLiveData<Boolean>()

    init {
        getBooksList()
    }

    override fun getBooksList() {
            bookUseCase.getBooksList().onEach {
                bookListLiveResponseData.value = it
            }.launchIn(viewModelScope)
        }

    override fun uploadBook(book: BookAddRequest) {
        /*bookUseCase.uploadBook(book).onEach { response->
            uploadSuccessLiveData.value = response
        }.launchIn(viewModelScope)*/

        viewModelScope.launch {
            bookUseCase.uploadBook(book).collect { response->
                uploadSuccessLiveData.postValue(response)
            }
        }
    }

    override fun loadBook(book: BookResponseData) {
        bookUseCase.loadBook(book).onEach {
            Log.d("TAG", "viewModel loadBook: ")
            loadSuccessLiveData.value = it
        }.launchIn(viewModelScope)
    }


    override fun readBook(book: BookResponseData) {
        readBookLiveData.value = book
    }

    override fun isBookFavourite(book: BookAddRequestData) {
        bookUseCase.isBookFavourite(book.toBookAddRequest()).onEach {
            isBookFavouriteLiveData.value = it
            getBooksList()
        }.launchIn(viewModelScope)
    }

    override fun addBookLoadCounter(book: BookAddRequestData) {
        bookUseCase.addBookLoadCounter(book.toBookAddRequest()).onEach {
            addBookLoadCounterLiveData.value = it
            getBooksList()
        }.launchIn(viewModelScope)
    }
}