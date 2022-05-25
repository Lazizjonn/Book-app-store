package uz.gita.bookapp.presentation.ui.screen

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
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
import uz.gita.bookapp.databinding.FragmentMainBinding
import uz.gita.bookapp.databinding.LoadingDilaogBinding
import uz.gita.bookapp.presentation.ui.adapter.BookListAdapter
import uz.gita.bookapp.presentation.viewmodel.MainViewModel
import uz.gita.bookapp.presentation.viewmodel.impl.MainViewModelImpl
import uz.gita.bookapp.utils.checkPermissions
import uz.gita.bookapp.utils.loadCompleteLiveData
import uz.gita.bookapp.utils.loadStartedLiveData


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private lateinit var booksAdapter: BookListAdapter
    private var loadingDialog: AlertDialog? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setAdapter()
        livedata()
        clicks()
        uploadBook()



    }

    private fun uploadBook() {
        /*var filePath: String? = null
        val _uri: Uri = data.getData()
        Log.d("", "URI = $_uri")
        if (_uri != null && "content" == _uri.getScheme()) {
            val cursor: Cursor = this.getContentResolver()
                .query(_uri, arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null)
            cursor.moveToFirst()
            filePath = cursor.getString(0)
            cursor.close()
        } else {
            filePath = _uri.getPath()
        }
        Log.d("", "Chosen path = $filePath")*/

//        val temp = BookAddRequest(3,
//            "https://assets.eventgo.uz/event/max/602a029b656ca2.76223319photo_2021-02-15_10-09-03.jpg",
//            "Bootcamp haqida","Gita Academy", "pdf", "2mb", "","10",1, "filePath!!")
//        viewModel.uploadBook(temp)

    }


    private fun setAdapter() {
        binding.booksRecycle.isVisible = false
        booksAdapter = BookListAdapter()
        binding.booksRecycle.adapter = booksAdapter
        binding.booksRecycle.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun livedata() {
        viewModel.bookListLiveResponseData.observe(viewLifecycleOwner, bookListObserver)
        viewModel.uploadSuccessLiveData.observe(this, uploadSuccessObserver)
        viewModel.loadSuccessLiveData.observe(this, loadSuccessObserver)
        viewModel.readBookLiveData.observe(this, readBookObserver)
        viewModel.addBookLoadCounterLiveData.observe(this, addBookLoadCounterObserver)
        loadCompleteLiveData.observe(this, loadHelperObserver)
        loadStartedLiveData.observe(this, loadStartedObserver)
    }

    private val bookListObserver = Observer<List<BookResponseData>> {
        if (it.size > 0){
            binding.booksRecycle.isVisible = true
            binding.animationView.isVisible = false
            booksAdapter.submitList(it)
        } else {
            binding.booksRecycle.isVisible = false
            binding.animationView.isVisible = true
        }
    }
    private val uploadSuccessObserver = Observer<Boolean> {
        Toast.makeText(requireContext(), "Uploaded: " + it, Toast.LENGTH_SHORT).show()
    }

    private val loadSuccessObserver = Observer<Boolean> {
        /*when(it){
            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
        }*/

    }
    private val readBookObserver = Observer<BookResponseData> {
        val readBundle = bundleOf("read_book" to it)
        findNavController().navigate(R.id.readFragment, readBundle)

    }
    private val addBookLoadCounterObserver = Observer<Boolean> {
        when(it){
            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
        }
    }

    private val loadHelperObserver = Observer<BookAddRequestData> {
        viewModel.addBookLoadCounter(it)
        // dialog dismiss
        if (loadingDialog != null && loadingDialog!!.isShowing)
        loadingDialog!!.dismiss()
    }

    private val loadStartedObserver = Observer<BookAddRequestData> {
        // dialog start
        openDialog(it)
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

    private fun clicks() {

        booksAdapter.putDownloadListener { book ->
            requireActivity().checkPermissions(arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                Timber.tag("TAG").d("mainFragment WRITE_EXTERNAL_STORAGE permission granted")
                viewModel.loadBook(book)
//                Toast.makeText(requireContext(), "Downloading on background...", Toast.LENGTH_SHORT).show()
            }
        }


        booksAdapter.putReadListener {
            requireActivity().checkPermissions(arrayOf( android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                Timber.tag("TAG").d("mainFragment READ_EXTERNAL_STORAGE permission granted")
                viewModel.readBook(it)
            }
        }

        booksAdapter.isFavListener {
            viewModel.isBookFavourite(it)
        }


    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "MainFragment onResume: called")
        viewModel.getBooksListDB()
    }

}