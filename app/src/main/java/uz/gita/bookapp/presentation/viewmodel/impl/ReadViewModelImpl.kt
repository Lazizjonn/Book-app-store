package uz.gita.bookapp.presentation.viewmodel.impl

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.bookapp.presentation.viewmodel.ReadViewModel

class ReadViewModelImpl: ViewModel(), ReadViewModel {
    override val goBackLiveData = MutableLiveData<Unit>()

    override fun goBack() {
        goBackLiveData.value = Unit
    }


}