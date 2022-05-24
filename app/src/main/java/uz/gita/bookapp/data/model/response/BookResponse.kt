package uz.gita.bookapp.data.model.response

import uz.gita.bookapp.data.model.common.BookResponseData
import uz.gita.bookapp.data.source.room.entity.BookEntity
import java.io.Serializable

data class BookResponse(
    val id: Int = 2,
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val author: String = "",
    val type: String = "",
    val size: String = "",
    val pages: Int = 0,
    val fav: Int = 0,
    val loadCount: Int = 0,
    val fileName: String = "",
    val url: String = "",
    val path: String = ""
): Serializable {

    fun toBookData(): BookResponseData {
        val _fav: Boolean = when (this.fav){
            0 ->{ false }
            else->{ true }
        }
        return BookResponseData(id, image, title, description, category, author, type, size, pages, _fav, loadCount, fileName, url, path)
    }

    fun toBookEntity(): BookEntity {
        val _fav: Boolean = when (this.fav){
            0 ->{ false }
            else->{ true }
        }
        return BookEntity(id, image, title, description, category, author, type, size, pages, _fav, false, loadCount, fileName, url, path)
    }
}
