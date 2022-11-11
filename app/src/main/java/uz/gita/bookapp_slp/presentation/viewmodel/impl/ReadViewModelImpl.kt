package uz.gita.bookapp_slp.presentation.viewmodel.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import uz.gita.bookapp_slp.presentation.viewmodel.ReadViewModel

class ReadViewModelImpl: ViewModel(), ReadViewModel {
    override val goBackLiveData = MutableLiveData<Unit>()

    override fun goBack() {
        goBackLiveData.value = Unit
    }


}