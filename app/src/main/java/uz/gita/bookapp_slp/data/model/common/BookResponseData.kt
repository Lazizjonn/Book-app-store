package uz.gita.bookapp_slp.data.model.common

import uz.gita.bookapp_slp.data.model.response.BookResponse
import uz.gita.bookapp_slp.data.source.room.entity.BookEntity
import java.io.Serializable
data class BookResponseData(
    val id: Int = 2,
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val author: String = "",
    val type: String = "",
    val size: String = "",
    val pages:Int = 0,
    val fav: Boolean = false,
    val loadCount: Int = 0,
    val fileName: String = "",
    val url: String = "",
    val path: String = ""
): Serializable {

    fun toBookResponse(): BookResponse {
        val _fav: Int = when (this.fav){
            true -> { 1 }
            else -> { 0 }
        }
        return BookResponse(id, image, title, description, category, author, type, size, pages, _fav, loadCount, fileName, url, path)
    }

    fun toBookAddRequestData(): BookAddRequestData {
        return BookAddRequestData(id, image, title, description, category, author, type, size, pages, fav, loadCount, fileName, url, path)
    }

    fun toBookEntity(): BookEntity = BookEntity(id, image, title, description, category, author, type, size, pages, fav, false, loadCount, fileName, url)
}
