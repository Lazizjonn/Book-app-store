package uz.gita.bookapp_slp.presentation.viewmodel

import androidx.lifecycle.LiveData


interface ReadViewModel {
    val goBackLiveData: LiveData<Unit>

    fun goBack()



}