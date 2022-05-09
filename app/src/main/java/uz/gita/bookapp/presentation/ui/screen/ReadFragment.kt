package uz.gita.bookapp.presentation.ui.screen

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import timber.log.Timber
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.databinding.FragmentReadBinding
import uz.gita.bookapp.presentation.viewmodel.impl.ReadViewModelImpl
import java.io.File


class ReadFragment : Fragment(R.layout.fragment_read) {
    private val binding by viewBinding(FragmentReadBinding::bind)
    private val viewModel by viewModels<ReadViewModelImpl>()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val data = arguments?.getSerializable("read_book") as BookResponseData
        binding.titleReading.text = data.title

        val file = File(data.path.trim())
        loadFromFile(file)

        binding.backButton.setOnClickListener {
            viewModel.goBack()
        }

        viewModel.goBackLiveData.observe(this, goBackObserver)
    }

    private val goBackObserver = Observer<Unit>{
        findNavController().popBackStack()
    }

    private fun loadFromBytes(byte: ByteArray) {
        binding.pdfViewer.fromBytes(byte)
            .swipeHorizontal(false)
            .onPageChange{ page, pageCount -> // current and totalPage
                val currentPage = page + 1
                binding.pageCounter.text = "page ${currentPage} / ${pageCount}"
            }
            .onError { e->
                Timber.d("error: " + e.message)
            }
            .onPageError{ page, e ->
                Timber.d("errorPage: " + e.message)
            }.load()
    }

    private fun loadFromFile(file: File) {
        binding.pdfViewer.fromFile(file)
            .swipeHorizontal(false)
            .onPageChange{ page, pageCount -> // current and totalPage
                val currentPage = page + 1
                binding.pageCounter.text = "page ${currentPage} / ${pageCount}"
            }
            .onError { e->
                Timber.d("error: " + e.message)
            }
            .onPageError{ page, e ->
                Timber.d("errorPage: " + e.message)
            }.load()
    }

}
