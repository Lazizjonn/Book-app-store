package uz.gita.bookapp_slp.presentation.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.gita.bookapp_slp.R
import uz.gita.bookapp_slp.data.model.common.BookAddRequestData
import uz.gita.bookapp_slp.data.model.common.BookResponseData
import uz.gita.bookapp_slp.utils.isFileExists
import java.io.File


class BookListAdapter : ListAdapter<BookResponseData, BookListAdapter.BooksViewHolder>(BookDiff) {

    var downloadListener: ((BookResponseData) -> Unit)? = null
    var readListener: ((BookResponseData) -> Unit)? = null
    var isFavListener: ((BookAddRequestData) -> Unit)? = null

    object BookDiff : DiffUtil.ItemCallback<BookResponseData>() {
        override fun areItemsTheSame(oldItem: BookResponseData, newItem: BookResponseData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: BookResponseData, newItem: BookResponseData): Boolean {
            return oldItem == newItem
        }

    }

    inner class BooksViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bookImage = view.findViewById<ImageView>(R.id.book_image)
        val bookTitle = view.findViewById<TextView>(R.id.book_title)
        val bookAuthor = view.findViewById<TextView>(R.id.book_author)
        val bookFileType = view.findViewById<TextView>(R.id.book_file_type)
        val bookSizeMb = view.findViewById<TextView>(R.id.book_size_mb)
        var isFav = view.findViewById<ImageButton>(R.id.isFav)

        val bookDownload = view.findViewById<TextView>(R.id.book_download)
        val bookRead = view.findViewById<TextView>(R.id.book_read)

        init {
            bookDownload.setOnClickListener {
                downloadListener?.invoke(currentList[absoluteAdapterPosition])

            }

            bookRead.setOnClickListener {
                readListener?.invoke(currentList[absoluteAdapterPosition])
            }

            isFav.setOnClickListener {
                isFavListener?.invoke(currentList[absoluteAdapterPosition].toBookAddRequestData())
            }

        }

        fun bind() {
            val item = getItem(absoluteAdapterPosition)

            val file = File("data/user/0/uz.gita.bookapp_slp/files/"+ item.fileName.replace(",", "") + ".pdf")
            Log.d("TAG", "BookListAdapter onBind, fileExist:" + file.isFileExists())
            bookRead.isVisible = file.isFileExists()
            bookDownload.isVisible = !file.isFileExists()
            bookDownload.text = "Download"

            Log.d("TAG", "bind: "+ item.toString())
            Glide.with(bookImage)
                .load(item.image)
                .placeholder(R.drawable.load_anim)
                .centerCrop()
                .into(bookImage)

            bookTitle.text = item.title
            bookAuthor.text = item.author

            bookFileType.text = item.type
            bookSizeMb.text = item.size
//            isFav.isSelected = item.fav
            when(item.fav){
                true -> { isFav.setBackgroundResource(R.drawable.bookmark2) }
                false -> { isFav.setBackgroundResource(R.drawable.bookmark_border2) }
            }

//            item.pages
//            item.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BooksViewHolder =
        BooksViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.books_item, parent, false))

    override fun onBindViewHolder(holder: BooksViewHolder, position: Int) {
        holder.bind()
    }

    fun putDownloadListener(block: (BookResponseData) -> Unit) {
        this.downloadListener = block
    }

    fun putReadListener(block: (BookResponseData) -> Unit) {
        this.readListener = block
    }

    fun isFavListener(block: (BookAddRequestData) -> Unit) {
        this.isFavListener = block
    }

    override fun submitList(list: List<BookResponseData>?) {
        super.submitList(list)
        notifyDataSetChanged()
    }
}