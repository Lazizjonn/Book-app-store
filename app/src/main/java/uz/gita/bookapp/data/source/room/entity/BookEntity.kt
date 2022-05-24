package uz.gita.bookapp.data.source.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.gita.bookapp.data.model.common.BookAddRequestData
import uz.gita.bookapp.data.model.request.BookAddRequest
import uz.gita.bookapp.data.model.response.BookResponse
import java.io.Serializable
@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey
    val id: Int = 2,
    val image: String = "",
    val title: String = "",
    val description: String = "",
    val category: String = "",
    val author: String = "",
    val type: String = "",
    val size: String = "",
    val pages: Int = 0,
    val fav: Boolean = false,
    val loading: Boolean = false,
    val loadCount: Int = 0,
    val fileName: String = "",
    val url: String = "",
    val path: String = ""
): Serializable{

    fun toBookResponse(): BookResponse {
        val _fav: Int = when (this.fav){
            true ->{ 1 }
            else->{ 0 }
        }
        return BookResponse(id, image, title, description, category, author, type, size, pages, _fav, loadCount, fileName, url, path)
    }
    fun toBookAddRequest(): BookAddRequest {
        val _fav: Int = when (this.fav){
            true ->{ 1 }
            else->{ 0 }
        }
        return BookAddRequest(id, image, title, description, category, author, type, size, pages, _fav, loadCount, fileName, url, path)
    }

    fun toBookAddRequestData(): BookAddRequestData {
        return BookAddRequestData(id, image, title, description, category, author, type, size, pages, fav, loadCount, fileName, url, path)
    }
}