package com.kostlin.gbooksapi.ui.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.kostlin.gbooksapi.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_book_detail.*

class BookDetailActivity : AppCompatActivity() {

    var title: String? = null
    var publishedDate: String? = null
    var description: String? = null
    var thumbnail: String? = null
    var authors: ArrayList<String>? = null

    private lateinit var textViewTitle: TextView
    private lateinit var textViewDetail: TextView
    private lateinit var textViewDate: TextView
    private lateinit var imageViewBook: ImageView
    private lateinit var textViewAuthors: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)

        textViewTitle = findViewById(R.id.textViewTitleDetail)
        textViewAuthors = findViewById(R.id.textViewAuthorsDetail)
        textViewDetail = findViewById(R.id.textViewDescriptionDetail)
        textViewDate = findViewById(R.id.textViewDateDetail)
        imageViewBook = findViewById(R.id.imageViewBookDetail)

        title = intent.getStringExtra("title")
        publishedDate = intent.getStringExtra("publishedDate")
        description = intent.getStringExtra("description")
        thumbnail = intent.getStringExtra("thumbnail")
        authors = intent.getStringArrayListExtra("authors")

        textViewTitle.setText(title)
        textViewDate.setText("Дата публикации : $publishedDate")
        textViewDetail.setText(description)

        textViewAuthors.text = "Автор: "
        for (i in authors!!) {
            if (i != authors!!.last()) textViewAuthors.text =
                textViewAuthors.text.toString() + "$i, "
            else textViewAuthors.text = textViewAuthors.text.toString() + i
        }

        if (thumbnail.isNullOrEmpty()) Picasso.get().load(R.drawable.ic_launcher_foreground).into(imageViewBookDetail)
        else Picasso.get().load(thumbnail).into(imageViewBookDetail)
    }
}