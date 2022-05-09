package uz.gita.bookapp.presentation.ui.screen

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapp.R
import uz.gita.bookapp.databinding.FragmentFavouriteBinding
import uz.gita.bookapp.presentation.ui.adapter.FavListAdapter

@AndroidEntryPoint
class FavouriteFragment : Fragment(R.layout.fragment_favourite) {
    private val binding by viewBinding(FragmentFavouriteBinding::bind)
//    private val viewModel by viewModels<Fav>()
    val mFavAdapter = FavListAdapter()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {



        setAdapter()
        livedata()

    }

    private fun livedata() {

    }

    private fun setAdapter() {
        binding.favRecycle.adapter = mFavAdapter
        binding.favRecycle.layoutManager = LinearLayoutManager(requireContext())
    }

}