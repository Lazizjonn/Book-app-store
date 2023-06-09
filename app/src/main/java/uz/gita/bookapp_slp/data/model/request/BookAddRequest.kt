package uz.gita.bookapp_slp.data.model.request

import uz.gita.bookapp_slp.data.model.common.BookAddRequestData
import uz.gita.bookapp_slp.data.source.room.entity.BookEntity
import java.io.Serializable

class BookAddRequest(
    val id: Int = 2,
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val author: String = "",
    val type: String = "",
    val size: String = "",
    val pages: Int = 0,
    var fav: Int = 0,
    var loadCount: Int = 0,
    val fileName: String = "",
    val url: String = "",
    val path: String = ""
) : Serializable {

    fun toBookData(): BookAddRequestData {
        val _fav: Boolean = when (this.fav) {
            0 -> { false }
            else -> { true }
        }
        return BookAddRequestData(id, image, title, description, category, author, type, size, pages, _fav, loadCount, fileName, url, path)
    }

    fun toBookEntity(): BookEntity {
        val _fav: Boolean = when (this.fav) {
            0 -> { false }
            else -> { true }
        }
        return BookEntity(id, image, title, description, category, author, type, size, pages, _fav, false, loadCount, fileName, url, path)
    }
}