package uz.gita.bookapp.domain.repository.impl

import android.content.Context
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.widget.Toast
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.model.common.LoadBookByteData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.data.model.response.BookResponse
import uz.gita.bookapp.domain.repository.BookRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

class BookRepositoryImpl @Inject constructor(
    private val storage: FirebaseStorage,
    private val booksRef: CollectionReference): BookRepository {

    override fun getBooksList(): Flow<List<BookResponse>> = callbackFlow<List<BookResponse>> {

//        addMore1()
//        addMore2()
//        addMore3()
//        addMore4()

        booksRef.get()
            .addOnSuccessListener {
                val books = it.map { document ->
                    Log.d("TAG", "getBooksList: list get success, " + document.toString())
                    document.toObject(BookResponse::class.java)
                }
                trySendBlocking(books)
        }
            .addOnFailureListener{
                Log.d("TAG", "getBooksList: list get success, " + it.message.toString())
                // failed
            }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)

    private fun addMore1() {
        val temp1 = BookResponse(1,
            "https://hilolnashr.uz/image/cache/catalog/001-Kitoblar/001_hilol_nashr/001_diniy/baxtiyor-oila-lotin-web-500x750.png",
            "Baxtiyor oila",
            "Ushbu kitob Islom dinining oilaviy munosabatlarga oid ahkomlarining keng va batafsil sharhi bo‘lib, musulmon kishi oilaviy hayotga oid bilishi lozim bo‘lgan barcha masalalarni o‘z ichiga oladi. Kitobda insonga ikki dunyo saodati yo‘lini ko‘rsatib bergan Islom dinining baxtli, saodatli oila qurish, er-xotinning huquqlari, burch va majburiyatlari, ota-onaga, qaynota-qaynonaga munosabat, kelin va kuyov tanlash, aqiyqa, farzand tarbiyasi, silai rahm, taloq, idda va shu kabi ko‘plab dolzarb mavzulardagi ta’limotlari orqali bugungi kunda qator muammolar muhokama qilinadi, oyatlar, hadislar hamda salaf solihlarning hayoti misolida musulmonning baxtli oilaviy hayot dasturi ko‘rsatib beriladi.",
            "Muslim", "Shayx Muhammad Sodiq Muhammad Yusuf", "pdf", "15mb", 504, 1, 2, "Baxtiyor_oila",
            "gs://bookapp-51c78.appspot.com/book_storage/Baxtiyor_oila.pdf", "/storage/emulated/0/Documents/Baxtiyor_oila.pdf "
        )

        booksRef.document(temp1.id.toString()).set(temp1)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }

    private fun addMore2() {
        val temp2 = BookResponse(2,
            "https://fb2bookfree.com/uploads/posts/2021-11/thumbs/1637987715_415jhuymlrl._sx350_bo1204203200_.jpg",
            "DESIGNING YOUR LIFE: HOW TO BUILD A WELL-LIVED, JOYFUL LIFE",
            "At last, a book that shows you how to build—design—a life you can thrive in, at any age or stage Designers create worlds and solve problems using design thinking. Look around your office or home—at the tablet or smartphone you may be holding or the chair you are sitting in. Everything in our lives was designed by someone. And every design starts with a problem that a designer or team of designers seeks to solve.",
            "Education"," Dave Evans", "pdf", "8mb",199,0, 2,"Designing_your_life",
            "gs://bookapp-51c78.appspot.com/book_storage/Designing_your_life.pdf", "/storage/emulated/0/Documents/Designing_your_life.pdf ")

        booksRef.document(temp2.id.toString()).set(temp2)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }

    private fun addMore3() {

        val temp3 = BookResponse(3,
            "https://fb2bookfree.com/uploads/posts/2020-10/1602553787_41gpiof19sl._sx320_bo1204203200_.jpg",
            "THE MIRACLE MORNING: THE NOT-SO-OBVIOUS SECRET GUARANTEED TO TRANSFORM YOUR LIFE",
            "“Hal Elrod is a genius and his book The Miracle Morning has been magical in my life. What Hal has done is taken the best practices, developed over centuries of human consciousness development, and condensed the 'best of the best' into a daily morning ritual. A ritual that is now part of my day. - Robert Kiyosaki, bestselling author of Rich Dad Poor Dad",
            "Education"," Hal Elrod", "pdf", "1.2 mb",187,0, 2,"The_miracle_morning",
            "gs://bookapp-51c78.appspot.com/book_storage/The_miracle_morning.pdf", "/storage/emulated/0/Documents/The_miracle_morning.pdf ")

        booksRef.document(temp3.id.toString()).set(temp3)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }

    private fun addMore4() {

        val temp4 = BookResponse(4,
            "https://fb2bookfree.com/uploads/posts/2020-10/1602553787_41gpiof19sl._sx320_bo1204203200_.jpg",
            "About me",
            "About me",
            "Education","About me", "pdf", "0.5 mb",5,1, 2, "About_me",
            "gs://bookapp-51c78.appspot.com/book_storage/About_me.pdf", "/storage/emulated/0/Documents/About_me.pdf ")

        booksRef.document(temp4.id.toString()).set(temp4)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }


    override fun uploadBook(book: BookAddRequest) = callbackFlow<Boolean> {
        val storageRef = storage.getReference("book_storage")
        val bookRef = storageRef.child(book.id.toString())

        val file = Uri.fromFile(File(book.path))

        bookRef.putFile(file)
            .addOnSuccessListener { task ->
                Log.d("TAG", "uploadBook: success, " + task.metadata?.sizeBytes.toString())
                trySendBlocking(true)
            }
            .addOnFailureListener {
                Log.d("TAG", "uploadBook: failure, " + it.message.toString())
                trySendBlocking(false)
            }

        awaitClose {  }
    }.catch {
        Log.d("TAG", "uploadBook: failure, " + it.message.toString())
    }.flowOn(Dispatchers.IO)

    /*override fun loadBook2(book: BookResponse): Flow<String>  = callbackFlow<String> {
        val storageRef = storage.getReference("book_storage")
        val bookRef = storageRef.child("${book.id}.pdf")
        val gsReference = storage.getReferenceFromUrl("gs://bookapp-51c78.appspot.com/book_storage/Gita.pdf")
//        val httpsReference = storage.getReferenceFromUrl(
//            "https://firebasestorage.googleapis.com/b/bucket/o/images%20stars.jpg")

        val localFile = File.createTempFile("book.id.toString()", ".pdf")
        gsReference.getFile(localFile)
            .addOnSuccessListener { task ->
                Log.d("TAG", "loadBook: success, " + task.task.result.toString())
//                trySendBlocking(task.loadedfilePath )
            }
            .addOnFailureListener{
                Log.d("TAG", "loadBook: failure, " + it.message.toString())
            }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)*/




    override fun loadBook(book: BookResponse): Flow<Boolean>  = callbackFlow {
        val gsReference = storage.getReferenceFromUrl(book.url)

        val ONE_MEGABYTE: Long = 20 * 1024 * 1024 // 20 mb limit
        gsReference.getBytes(ONE_MEGABYTE)
            .addOnSuccessListener { bytes ->
                Log.d("TAG", "loadBook: success, fileSize " + bytes.size.toString())
//                trySendBlocking(LoadBookByteData(book.toBookData(), bytes))
                val isSaved = saveBookToFolder(book.path.trim(), bytes)
                trySendBlocking(isSaved)
            }
            .addOnFailureListener{
                Log.d("TAG", "loadBook: failure, " + it.message.toString())
//                trySendBlocking()
            }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)

    private fun saveBookToFolder(filePath: String, bytes: ByteArray): Boolean {

        try {
            val downloadsFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
            downloadsFolder.mkdirs() // creates if not exist

            // downloadsFolder.path + "/" + book.fileName + ".pdf"
            val out = FileOutputStream(filePath)
            out.write(bytes)
            out.close()
            return true
            // incrementDownloadCount() /////////***///

        } catch (e: Throwable){
            return false
//            Toast.makeText(requireContext(), "Saving book to folder failed: " + e.message.toString(), Toast.LENGTH_SHORT).show()
        }

    }



    override fun isBookFavourite(book: BookAddRequest): Flow<Boolean> = callbackFlow<Boolean> {
        book.fav = when (book.fav){
            0 -> 1
            1 -> 0
            else -> {0}
        }
        booksRef.document(book.id.toString()).set(book)
            .addOnSuccessListener {
                Log.d("TAG", "book isFav changed to:" + book.fav)
                trySendBlocking(true)
            }
            .addOnFailureListener {
                Log.d("TAG", "book isFav changing failed: " + it.message.toString())
                trySendBlocking(false)
            }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)

    override fun addBookLoadCounter(book: BookAddRequest): Flow<Boolean> = callbackFlow<Boolean> {
        book.loadCount = book.loadCount + 1
        booksRef.document(book.id.toString()).set(book)
            .addOnSuccessListener {
                Log.d("TAG", "book loadCount incremented:" + book.fav)
                trySendBlocking(true)
            }
            .addOnFailureListener {
                Log.d("TAG", "book loadCount incrementing failed: " + it.message.toString())
                trySendBlocking(false)
            }
        awaitClose {  }
    }.flowOn(Dispatchers.IO)



}