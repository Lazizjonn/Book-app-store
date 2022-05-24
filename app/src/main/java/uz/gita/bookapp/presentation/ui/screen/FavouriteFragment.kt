package uz.gita.bookapp.presentation.ui.screen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.common.BookAddRequestData
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.databinding.FragmentFavouriteBinding
import uz.gita.bookapp.databinding.LoadingDilaogBinding
import uz.gita.bookapp.presentation.ui.adapter.FavListAdapter
import uz.gita.bookapp.presentation.viewmodel.impl.FavViewModelImpl
import uz.gita.bookapp.utils.checkPermissions
import uz.gita.bookapp.utils.loadCompleteLiveData
import uz.gita.bookapp.utils.loadStartedLiveData

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
    private val binding by viewBinding(FragmentFavouriteBinding::bind)
    private val viewModel by viewModels<FavViewModelImpl>()
    private lateinit var mFavAdapter: FavListAdapter
    private var loadingDialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setAdapter()
        livedata()
        clicks()

    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "FavouriteFragment onResume: called")
        viewModel.getFavouriteBooksListDB()
    }

    private fun livedata() {
        viewModel.bookListLiveResponseData.observe(viewLifecycleOwner, bookListObserver)
        viewModel.readBookLiveData.observe(this, readBookObserver)
        viewModel.addBookLoadCounterLiveData.observe(this, addBookLoadCounterObserver)

        viewModel.loadSuccessLiveData.observe(this, loadSuccessObserver)

        loadCompleteLiveData.observe(this, loadHelperObserver)
        loadStartedLiveData.observe(this, loadStartedObserver)
    }

    private val bookListObserver = Observer<List<BookResponseData>> {
        Log.d("TAG", "addBookLoadCounter: loaded 5")
        mFavAdapter.submitList(it)
    }
    private val uploadSuccessObserver = Observer<Boolean> {
        Toast.makeText(requireContext(), "Uploaded: " + it, Toast.LENGTH_SHORT).show()
    }

    private val loadSuccessObserver = Observer<Boolean> {
//        when (it) {
//            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
//            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
//        }
    }

    private val readBookObserver = Observer<BookResponseData> {
        val readBundle = bundleOf("read_book" to it)
        findNavController().navigate(R.id.readFragment, readBundle)
    }

    private val addBookLoadCounterObserver = Observer<Boolean> {
//        when(it){
//            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
//            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
//        }
    }

    private val loadHelperObserver = Observer<BookAddRequestData> {
        viewModel.addBookLoadCounter(it)
        // dialog dismiss
        if (loadingDialog != null && loadingDialog!!.isShowing)
            loadingDialog!!.dismiss()
    }
    private val loadStartedObserver = Observer<BookAddRequestData> {
        // dialog start
    }

    private fun openDialog(book: BookAddRequestData){
        val binding = LoadingDilaogBinding.inflate(LayoutInflater.from(requireContext()), null, false)
        binding.title.text = book.title
        Glide.with(binding.image).load(R.drawable.loading).into(binding.image)

        loadingDialog = AlertDialog.Builder(requireContext())
            .setCancelable(false)
            .setView(binding.root)
            .create()

        loadingDialog!!.show()
    }


    private fun setAdapter() {
        mFavAdapter = FavListAdapter()
        binding.favRecycle.adapter = mFavAdapter
        binding.favRecycle.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun clicks() {
        mFavAdapter.putDownloadListener { book ->
            requireActivity().checkPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Timber.tag("TAG").d("mainFragment WRITE_EXTERNAL_STORAGE permission granted")
                viewModel.loadBook(book)
//                Toast.makeText(requireContext(), "Downloading on background...", Toast.LENGTH_SHORT).show()
            }
        }


        mFavAdapter.putReadListener {
            requireActivity().checkPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Timber.tag("TAG").d("mainFragment READ_EXTERNAL_STORAGE permission granted")
                viewModel.readBook(it)
            }
        }

        mFavAdapter.isFavListener {
            // it`s visibility has been hid
            viewModel.isBookFavourite(it)
        }
    }



}