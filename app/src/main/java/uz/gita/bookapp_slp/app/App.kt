package uz.gita.bookapp_slp.app

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import uz.gita.bookapp_slp.BuildConfig


@HiltAndroidApp
class App: Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }

    companion object{
        lateinit var instance: App
            private set
    }

}