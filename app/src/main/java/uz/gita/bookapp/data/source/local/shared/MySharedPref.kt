package uz.gita.chatapp.data.source.local.shared

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import dagger.hilt.android.qualifiers.ApplicationContext
import java.lang.reflect.Type
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MySharedPref @Inject constructor(@ApplicationContext context: Context) {

    val pref = context.getSharedPreferences("delivery", Context.MODE_PRIVATE)

    var isNotFirstTime: Boolean
        get() = pref!!.getBoolean("INTRO", false)
        set(value) = pref!!.edit().putBoolean("INTRO", value).apply()


    /*fun putUser(obj: MyUserData, key: String) {
        val json: String = Gson().toJson(obj)
//        pref!!.edit().remove(key).apply()
        pref.edit().putString(key, json).apply()
    }

    fun getUser(key: String): MyUserData? {
        val value = pref?.getString(key, null)
        *//*GsonBuilder().create().fromJson(value, T::class.java)*//*
        return when (value) {
            null -> null
            else -> {
                val type: Type = object : TypeToken<MyUserData>() {}.type
                Gson().fromJson(value, type)
            }
        }
    }*/



    fun <T> putListObj(objs: ArrayList<T>?, key: String) {
        val json: String = Gson().toJson(objs)
        pref!!.edit().remove(key).apply()
        pref.edit().putString(key, json).apply()
    }

    inline fun <reified T> getListObj(key: String): ArrayList<T> {
        val value = pref?.getString(key, null)
        /*GsonBuilder().create().fromJson(value, T::class.java)*/
        return if (value == null) {
            ArrayList()
        } else {
            val type: Type = object : TypeToken<ArrayList<T>>() {}.type
            Gson().fromJson(value, type)
        }
    }

}
