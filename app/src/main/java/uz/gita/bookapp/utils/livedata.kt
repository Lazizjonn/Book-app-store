package uz.gita.bookapp.utils

import androidx.lifecycle.MutableLiveData
import uz.gita.bookapp.data.model.common.BookAddRequestData


val loadCompleteLiveData = MutableLiveData<BookAddRequestData>()

val loadStartedLiveData = MutableLiveData<BookAddRequestData>()