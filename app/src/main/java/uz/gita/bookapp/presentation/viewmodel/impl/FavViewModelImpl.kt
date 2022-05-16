package uz.gita.bookapp.presentation.viewmodel.impl

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import uz.gita.bookapp.data.model.common.BookAddRequestData
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.presentation.viewmodel.FavViewModel
import uz.gita.bookapp.domain.usecase.FavUseCase
import javax.inject.Inject

@HiltViewModel
class FavViewModelImpl @Inject constructor(private val bookUseCase: FavUseCase): ViewModel(), FavViewModel {
    override val bookListLiveResponseData = MutableLiveData<List<BookResponseData>>()

    override val loadSuccessLiveData = MutableLiveData<Boolean>()
    override val readBookLiveData = MutableLiveData<BookResponseData>()
    override val isBookFavouriteLiveData = MutableLiveData<Boolean>()
    override val addBookLoadCounterLiveData = MutableLiveData<Boolean>()

    init {
        getFavouriteBooksList()
    }

    override fun getFavouriteBooksList() {
            bookUseCase.getFavouriteBooksList().onEach {
                bookListLiveResponseData.value = it
            }.launchIn(viewModelScope)
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
            getFavouriteBooksList()
        }.launchIn(viewModelScope)
    }

    override fun addBookLoadCounter(book: BookAddRequestData) {
        bookUseCase.addBookLoadCounter(book.toBookAddRequest()).onEach {
            addBookLoadCounterLiveData.value = it
            getFavouriteBooksList()
        }.launchIn(viewModelScope)
    }
}