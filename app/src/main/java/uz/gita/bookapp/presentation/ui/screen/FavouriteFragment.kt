package uz.gita.bookapp.presentation.ui.screen

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.databinding.FragmentFavouriteBinding
import uz.gita.bookapp.presentation.ui.adapter.FavListAdapter
import uz.gita.bookapp.presentation.viewmodel.impl.FavViewModelImpl

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
    private val binding by viewBinding(FragmentFavouriteBinding::bind)
    private val viewModel by viewModels<FavViewModelImpl>()
    private lateinit var mFavAdapter: FavListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapter()
        livedata()
        clicks()

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavouriteBooksList()
    }

    private fun livedata() {
        viewModel.bookListLiveResponseData.observe(viewLifecycleOwner, bookListObserver)
        viewModel.loadSuccessLiveData.observe(this, loadSuccessObserver)
        viewModel.readBookLiveData.observe(this, readBookObserver)
    }

    private val bookListObserver = Observer<List<BookResponseData>> {
        mFavAdapter.submitList(it)
    }
    private val uploadSuccessObserver = Observer<Boolean> {
        Toast.makeText(requireContext(), "Uploaded: " + it, Toast.LENGTH_SHORT).show()
    }

    private val loadSuccessObserver = Observer<Boolean> {
        when (it) {
            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
        }
    }

    private val readBookObserver = Observer<BookResponseData> {
        val readBundle = bundleOf("read_book" to it)
        findNavController().navigate(R.id.readFragment, readBundle)

    }

    private fun setAdapter() {
        mFavAdapter = FavListAdapter()
        binding.favRecycle.adapter = mFavAdapter
        binding.favRecycle.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun clicks() {
        mFavAdapter.putDownloadListener { book ->
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

                Timber.tag("TAG").d("mainFragment WRITE_EXTERNAL_STORAGE permission granted")
                viewModel.loadBook(book)
                viewModel.addBookLoadCounter(book.toBookAddRequestData())

            }
            else {
                Timber.tag("TAG").d("mainFragment WRITE_EXTERNAL_STORAGE permission not granted")
                givePermission.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }


        mFavAdapter.putReadListener {
            if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED) {

                Timber.tag("TAG").d("mainFragment READ_EXTERNAL_STORAGE permission granted")
                viewModel.readBook(it)

            }
            else {
                Timber.tag("TAG").d("mainFragment READ_EXTERNAL_STORAGE permission not granted")
                givePermission.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
            }
        }

        mFavAdapter.isFavListener {
            viewModel.isBookFavourite(it)
        }
    }

    private val givePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ permissionCallback ->
        if (permissionCallback) {
            Timber.tag("TAG").d("mainFragment rechecked permission granted")
        } else {
            Timber.tag("TAG").d("mainFragment rechecked permission denied")
        }
    }

}