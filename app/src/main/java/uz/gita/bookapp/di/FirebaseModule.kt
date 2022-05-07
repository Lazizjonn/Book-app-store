package uz.gita.bookapp.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseModule {

    @[Provides Singleton]
    fun provideDatabase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @[Provides Singleton]
    fun provideAuth(): FirebaseAuth = Firebase.auth // FirebaseAuth.getInstance()

    @[Provides Singleton]
    fun bookStoreReference(database: FirebaseFirestore): CollectionReference = database.collection("books")

}