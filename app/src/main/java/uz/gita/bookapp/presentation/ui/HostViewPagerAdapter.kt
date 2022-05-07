package uz.gita.bookapp.presentation.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class HostViewPagerAdapter (list: List<Fragment>, fragmentMen: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentMen,lifecycle) {
    private val mList = list

    override fun getItemCount(): Int = mList.size

    override fun createFragment(position: Int): Fragment = mList[position]
}