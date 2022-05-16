package uz.gita.bookapp.presentation.ui.screen

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import uz.gita.bookapp.R
import uz.gita.bookapp.databinding.FragmentHostBinding
import uz.gita.bookapp.presentation.ui.HostViewPagerAdapter

@AndroidEntryPoint
class HostFragment : Fragment(R.layout.fragment_host) {
    private val main_screen = MainFragment()
    private val favouriteFragment = FavouriteFragment()
    private val binding by viewBinding(FragmentHostBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?): Unit = with(binding) {

        val pagerAdapter = HostViewPagerAdapter(
            listOf(main_screen, favouriteFragment),
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.mainViewPager.adapter = pagerAdapter
        val title = arrayOf("Home", "Favourite")
        TabLayoutMediator(binding.tablayout, binding.mainViewPager) { tab, position ->
            tab.text = title[position]
        }.attach()

        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.START)
            when (it.itemId) {
                R.id.menu_home -> binding.mainViewPager.currentItem = 0
                R.id.favourites -> binding.mainViewPager.currentItem = 1
                R.id.share -> { shareApp() }
                R.id.rate_us -> { rateApp() }
                R.id.contact -> { contactUs() }
            }
            true
        }

        mainMenuButton.setOnClickListener(View.OnClickListener {
            // If the navigation drawer is not open then open it, if its already open then close it.
            if (!drawerLayout.isDrawerOpen(GravityCompat.START)) drawerLayout.openDrawer(
                GravityCompat.START
            )
            else drawerLayout.closeDrawer(GravityCompat.END)
        })

    }


    private fun rateApp() {
        val packageName =
            "developer?id=uz.gita.italiandictionary"    // GITA+Dasturchilar+Akademiyasi" // "details?id=uz.gita.wooden15puzzleapp"
        val uri: Uri = Uri.parse("market://$packageName")
        val goToMarket = Intent(Intent.ACTION_VIEW, uri)
        // To count with Play market backstack, After pressing back button,
        // to taken back to our application, we need to add following flags to intent.
        goToMarket.addFlags(
            Intent.FLAG_ACTIVITY_NO_HISTORY or
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT or
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK
        )
        try {
            startActivity(goToMarket)
        } catch (e: ActivityNotFoundException) {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/$packageName")
                )
            )
        }
    }

    private fun shareApp(): Boolean {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val packageName = "developer?id=uz.gita.italiandictionary"    // GITA+Dasturchilar+Akademiyasi"  "details?id=uz.gita.wooden15puzzleapp"

        val shareBody = "http://play.google.com/store/apps/$packageName"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Our Apps")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)

//        sharingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
        return true
    }

    private fun shareText(word: String): Boolean {
        val sharingIntent = Intent(android.content.Intent.ACTION_SEND)
        sharingIntent.type = "text/plain"
        val packageName = "developer?id=uz.gita.italiandictionary"    // GITA+Dasturchilar+Akademiyasi"  "details?id=uz.gita.wooden15puzzleapp"
        val url = "http://play.google.com/store/apps/$packageName"

        val shareBody = "Word: \"$word\"  \n \n Italian Dictionary: $url"
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Our Apps")
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody)

//        sharingIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK;
        startActivity(Intent.createChooser(sharingIntent, "Share via"))
        return true
    }

    private fun contactUs(): Boolean {
        val email = "ttymi.kamolov@gmail.com" //  "kamolov2540@gmail.com"
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.putExtra(android.content.Intent.EXTRA_EMAIL, arrayOf(email))
//        intent.type = "message/rfc822" // for multiple app
        intent.data = Uri.parse("mailto:")
        startActivity(intent)
        /*if (intent.resolveActivity(requireActivity().packageManager) == null) {
            startActivity(intent)
        } else {
            Snackbar.make(
                requireView(),
                "You have no application to contact us via email",
                Snackbar.LENGTH_SHORT
            ).show()
        }*/
        return true
    }
}