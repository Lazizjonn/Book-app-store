package uz.gita.chatapp.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Build
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import uz.gita.bookapp.app.App
import java.util.*
import java.util.concurrent.TimeUnit

/*
   Author: Zukhriddin Kamolov
   Created: 13.05.2022 at 17:59
   Project: ChatApp
*/

object AppManager {

    val addChatroomLiveData = MutableLiveData<Unit>()

}

    fun Long.toDate(): String {

    val offset: Int = TimeZone.getDefault().rawOffset + TimeZone.getDefault().dstSavings
    var now: Long = this + offset

    var day: Long = TimeUnit.MILLISECONDS.toDays(now)
    var hr: Long = TimeUnit.MILLISECONDS.toHours(now)
    val min: Long = TimeUnit.MILLISECONDS.toMinutes(now - TimeUnit.HOURS.toMillis(hr))
//        val sec: Long = TimeUnit.MILLISECONDS.toSeconds(now - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min))
    //val ms: Long = TimeUnit.MILLISECONDS.toMillis(now - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec))
    hr = hr - (hr/24) * 24

//        val dateFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("d/M/u")
//        val startDate = "1/4/2022"
//        val endDate = "14/5/2022"
//        val startDateValue: LocalDate = LocalDate.parse(startDate, dateFormatter)
//        val endDateValue: LocalDate = LocalDate.parse(endDate, dateFormatter)
//        val days: Long = ChronoUnit.DAYS.between(startDateValue, endDateValue) + 1
//        println("Days: $days")
//        return days.toString()
    return String.format("%02d:%02d", hr, min)

}

fun setClipboard(text: String) {
    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
        val clipboard = App.instance.getSystemService(Context.CLIPBOARD_SERVICE) as android.text.ClipboardManager
        clipboard.text = text
    } else {
        val clipboard = App.instance.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", text)
        clipboard.setPrimaryClip(clip)
    }
    Toast.makeText(App.instance, "Copied \"$text\"", Toast.LENGTH_SHORT).show()
}