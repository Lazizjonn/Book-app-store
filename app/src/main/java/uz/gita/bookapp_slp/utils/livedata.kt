package uz.gita.bookapp_slp.utils

import androidx.lifecycle.MutableLiveData
import uz.gita.bookapp_slp.data.model.common.BookAddRequestData


val loadCompleteLiveData = MutableLiveData<BookAddRequestData>()

val loadStartedLiveData = MutableLiveData<BookAddRequestData>()