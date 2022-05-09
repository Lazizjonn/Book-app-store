package uz.gita.bookapp.presentation.ui.screen

import android.R.attr.data
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import uz.gita.bookapp.R
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.databinding.FragmentMainBinding
import uz.gita.bookapp.presentation.ui.adapter.BookListAdapter
import uz.gita.bookapp.presentation.viewmodel.impl.MainViewModelImpl
import java.io.FileOutputStream
import java.util.jar.Manifest


@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {
    private val binding by viewBinding(FragmentMainBinding::bind)
    private val viewModel by viewModels<MainViewModelImpl>()
    private lateinit var booksAdapter: BookListAdapter


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
        booksAdapter = BookListAdapter()
        binding.booksRecycle.adapter = booksAdapter
        binding.booksRecycle.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun livedata() {
        viewModel.bookListLiveResponseData.observe(viewLifecycleOwner, bookListObserver)
        viewModel.uploadSuccessLiveData.observe(this, uploadSuccessObserver)
        viewModel.loadSuccessLiveData.observe(this, loadSuccessObserver)
        viewModel.readBookLiveData.observe(this, readBookObserver)
    }

    private val bookListObserver = Observer<List<BookResponseData>> {
        booksAdapter.submitList(it)
    }
    private val uploadSuccessObserver = Observer<Boolean> {
        Toast.makeText(requireContext(), "Uploaded: " + it, Toast.LENGTH_SHORT).show()
    }

    private val loadSuccessObserver = Observer<Boolean> {
        when(it){
            true -> Toast.makeText(requireContext(), "Book loaded", Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(requireContext(), "Book load failed", Toast.LENGTH_SHORT).show()
        }

    }
    private val readBookObserver = Observer<BookResponseData> {
        val readBundle = bundleOf("read_book" to it)
        findNavController().navigate(R.id.readFragment, readBundle)

    }

    private fun clicks() {

        booksAdapter.putDownloadListener { book ->
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


        booksAdapter.putReadListener {
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

        booksAdapter.isFavListener {
            viewModel.isBookFavourite(it)
        }


    }



    private fun incrementDownloadCount() {
        TODO("Not yet implemented")
    }

    private val givePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()){ permissionCallback ->
            if (permissionCallback) {
                Timber.tag("TAG").d("mainFragment rechecked permission granted")
            } else {
                Timber.tag("TAG").d("mainFragment rechecked permission denied")
            }
    }

}