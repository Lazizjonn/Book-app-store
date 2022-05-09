package uz.gita.bookapp.presentation.viewmodel

import androidx.lifecycle.LiveData


interface ReadViewModel {
    val goBackLiveData: LiveData<Unit>

    fun goBack()



}