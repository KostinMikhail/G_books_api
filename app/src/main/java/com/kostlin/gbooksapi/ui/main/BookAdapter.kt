package com.kostlin.gbooksapi.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kostlin.gbooksapi.R
import com.squareup.picasso.Picasso
import java.util.*

class BookAdapter(
    private val bookInfoArrayList: ArrayList<BookInfo>,
    private val mcontext: Context
) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.book_rv_item, parent, false)
        return BookViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {

        // вкладываем данные во вьюхи
        val bookInfo = bookInfoArrayList[position]
        holder.textViewTitle.text = bookInfo.title

        // устанавливаем обложку с ссылки через пикассо
        if (bookInfo.thumbnail.isEmpty()) Picasso.get().load(R.drawable.ic_launcher_foreground)
            .into(holder.imageViewThumbnail)
        else Picasso.get().load(bookInfo.thumbnail).into(holder.imageViewThumbnail)

        holder.itemView.setOnClickListener {

            // по клику вызываем новую активити и передаём туда данные
            val i = Intent(mcontext, BookDetailActivity::class.java)
            i.putExtra("title", bookInfo.title)
            i.putExtra("authors", bookInfo.authors)
            i.putExtra("publishedDate", bookInfo.publishedDate)
            i.putExtra("description", bookInfo.description)
            i.putExtra("thumbnail", bookInfo.thumbnail)
            i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            mcontext.startActivity(i)
        }
    }

    override fun getItemCount(): Int {
        // возвращаем размер нашего списка
        return bookInfoArrayList.size
    }

    inner class BookViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // инициализируем вьюхи
        var textViewTitle: TextView
        var imageViewThumbnail: ImageView

        init {
            textViewTitle = itemView.findViewById(R.id.idTVBookTitle)
            imageViewThumbnail = itemView.findViewById(R.id.imageViewBookDetail)
        }
    }
}