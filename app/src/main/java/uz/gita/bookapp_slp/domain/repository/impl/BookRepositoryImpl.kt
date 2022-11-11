package uz.gita.bookapp_slp.domain.repository.impl

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import uz.gita.bookapp_slp.app.App
import uz.gita.bookapp_slp.data.model.request.BookAddRequest
import uz.gita.bookapp_slp.data.model.response.BookResponse
import uz.gita.bookapp_slp.data.source.room.dao.BookDao
import uz.gita.bookapp_slp.domain.repository.BookRepository
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject


class BookRepositoryImpl @Inject constructor(
    private val dao: BookDao,
    private val storage: FirebaseStorage,
    private val booksRef: CollectionReference): BookRepository {

    override fun getBooksList(): Flow<List<BookResponse>> = callbackFlow {

//        addMore1()
//        addMore2()
//        addMore3()
//        addMore4()

        booksRef.get().addOnSuccessListener {
                val books = it.map { document ->
                    document.toObject(BookResponse::class.java)
                }
            Log.d("TAG", "getBooksList: list get success, " + books.size)
//            dao.deleteBooks()
            dao.insertBooks(books.map { it.toBookEntity() })
            trySendBlocking(dao.getAllBooks().map { it.toBookResponse() })
        }
            .addOnFailureListener{
                Log.d("TAG", "getBooksList: list get success, " + it.message.toString())
                // failed
            }
        awaitClose {  }
    }
        .flowOn(Dispatchers.IO)

    override fun getBooksListDB(): Flow<List<BookResponse>> = flow { emit(dao.getAllBooks()
        .map { it.toBookResponse() }) }
        .flowOn(Dispatchers.IO)

    override fun getFavouriteBooksListDB(): Flow<List<BookResponse>> = flow{ emit(dao.getAllFavBooks()
        .map { it.toBookResponse() }) }
        .flowOn(Dispatchers.IO)

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
    }
        .flowOn(Dispatchers.IO)

    override fun loadBook(book: BookResponse): Flow<Boolean>  = callbackFlow {
            val gsReference = storage.getReferenceFromUrl(book.url)

            val ONE_MEGABYTE: Long = 20 * 1024 * 1024 // 20 mb limit
            gsReference.getBytes(ONE_MEGABYTE)
                .addOnSuccessListener { bytes ->
                    Log.d("TAG", "loadBook: success, fileSize " + bytes.size.toString())
//                trySendBlocking(LoadBookByteData(book.toBookData(), bytes))
                    val isSaved = saveBookToFolder(book.fileName, bytes)
                    trySendBlocking(isSaved)
                }
                .addOnFailureListener {
                    Log.d("TAG", "loadBook: failure, " + it.message.toString())
//                trySendBlocking()
                }
        awaitClose {  }
    }
        .flowOn(Dispatchers.IO)

    override fun isBookFavouriteDB(book: BookAddRequest): Flow<Boolean> = flow<Boolean> {
        book.fav = when (book.fav){
            0 -> 1
            1 -> 0
            else -> {0}
        }
        dao.updateBook(book.toBookEntity())
        Log.d("TAG", "book isFav changed to:" + book.fav)
        emit(true)

    }.catch {
        //
    }
        .flowOn(Dispatchers.IO)

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
    }
        .flowOn(Dispatchers.IO)

//    private fun saveBookToFolder(filePath: String, bytes: ByteArray): Boolean {
//
//        try {
//            val downloadsFolder = Environment.getStorageDirectory()
//            downloadsFolder.mkdirs() // creates if not exist
//
//            // downloadsFolder.path + "/" + book.fileName + ".pdf"
//
//            val out = FileOutputStream(filePath)
//            out.write(bytes)
//            out.close()
//            return true
//            // incrementDownloadCount() /////////***///
//
//        } catch (e: Throwable){
//            return false
////            Toast.makeText(requireContext(), "Saving book to folder failed: " + e.message.toString(), Toast.LENGTH_SHORT).show()
//        }


    private fun saveBookToFolder(fileName: String, bytes: ByteArray): Boolean {

        val root = App.instance.filesDir.toString()
        val myDir = File(root + "/" +  fileName.replace(",", "") + ".pdf" )
        Log.d("TTT", "cashed directory "+myDir.path)
        Log.d("TTT", "cashed directory "+ (myDir.parentFile?.path ?:"null"))
        if (!myDir.parentFile?.exists()!!) {
            myDir.parentFile?.mkdirs()
        }
        try {
            val out = FileOutputStream(myDir)
            out.write(bytes)
            out.close()
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun addMore1() {
        val temp1 = BookResponse(17,
            "https://images-na.ssl-images-amazon.com/images/I/513BJ68DD6L._SX329_BO1,204,203,200_.jpg",
            "Read Better, Remember More",
            "Whether you're studying for an exam, making your way through an instructional manual on the job, keeping up with work-related magazines and newspaper articles, or just coping with everyday situations in life, reading often makes the difference between success and failure. But it isn't how fast you read that's important. It's how well you understand what you read and how much of it you remember.",
            "Education", "Elizabeth Chesla", "pdf", "0.8mb",254, 0, 0, "Read_better_remember_more",
            "gs://bookstore-57ebb.appspot.com/book_storage/Read_better_remember_more.pdf", "/storage/emulated/0/Documents/Read_better_remember_more.pdf "
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
        val temp2 = BookResponse(18,
            "http://arm.sies.uz/wp-content/uploads/2021/03/%D0%91%D0%B5%D0%B7%D1%8B%D0%BC%D1%8F%D0%BD%D0%BD%D1%8B%D0%B9-20-185x300.png",
            "Muvoffaqiyatga Erishishning 200 Siri",
            "",
            "Education","Robin Sharma", "pdf", "0.95mb",67,0, 0,"Robin_sharma_200_sir",
            "gs://bookstore-57ebb.appspot.com/book_storage/Robin_sharma_200_sir.pdf", "/storage/emulated/0/Documents/Robin_sharma_200_sir.pdf ")

        booksRef.document(temp2.id.toString()).set(temp2)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }
    private fun addMore3() {

        val temp3 = BookResponse(19,
            "https://kitoblardunyosi.uz/image/cache/catalog/001-Kitoblar/Boshqa-nashriyot-kitobi/tarixi-muhammadiya-3d-web-1000x1000h.jpg",
            "TARIXI MUHAMMADIY",
            "Бу асарда Ислом дини тарихи, Пайғамбаримиз Муҳаммад алайҳиссаломнинг ҳаёт ва фаолиятлари, 23 йиллик пайғамбарлик даврларида бошларидан кечирган оғир ва енгил кунлари, тўрт халифалари, саҳобаи киромлари ва бошқа мусулмонларнинг имон ва Ислом йўлида қилган хизматлари, мушрик ва куфр аҳлининг дастлаб Ислом динига кўрсатган қаршилик ва инкорлари, сўнгра ҳақиқатни идрок этганларидан кейин тўп-тўп бўлиб Ислом динини қабул этганлари, ажойиб ва ғаройиб тарихий воқеалар батафсил баён этилган.",
            "Education","Alixonto’ra Sog’uniy", "pdf", "4.1mb",387,0, 2,"Tarixi_muhammadiy",
            "gs://bookstore-57ebb.appspot.com/book_storage/Tarixi_muhammadiy.pdf", "/storage/emulated/0/Documents/Tarixi_muhammadiy.pdf ")

        booksRef.document(temp3.id.toString()).set(temp3)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }
    private fun addMore4() {

        val temp4 = BookResponse(20,
            "https://assets.asaxiy.uz/product/items/desktop/e0f48988114ada340782b2354c906ebc2020120916245217810L3SDzL9FOP.jpg.webp",
            "O‘lsang, kim yig‘laydi?",
            "Биз аслида ҳамма ҳаётнинг ўзи нима эканлигини унутган даврда яшаяпмиз. Биз инсонни бемалол Ойга йўллай оламиз, лекин шундоқ ёнимизга кўчиб келган янги қўшнилар билан танишишга сира фурсат тополмаймиз. Биз баллистик ракетани ер шарининг бошқа қисмига учириб, мўлжалга аниқ ура оламиз, бироқ негадир болалар билан кутубхонага боришни доимо орқага сурамиз. Бизда бир-биримиз билан алоқани йўқотиб қўймаслик учун электрон почта, факслар, уяли телефонлар бор, аммо инсоният ҳали ҳеч қачон бу қадар тарқоқ бўлмаган.",
            "Education","Robin Sharma", "pdf", "20.2mb",157,0, 2, "Olsang_kim_yiglaydi",
            "gs://bookstore-57ebb.appspot.com/book_storage/Olsang_kim_yiglaydi.pdf", "/storage/emulated/0/Documents/Olsang_kim_yiglaydi.pdf ")

        booksRef.document(temp4.id.toString()).set(temp4)
            .addOnSuccessListener {
                Log.d("TAG", "getBooksList: temp set success")
            }
            .addOnFailureListener {
                Log.d("TAG", "getBooksList: temp set failure; " + it.message.toString())
            }
    }

}