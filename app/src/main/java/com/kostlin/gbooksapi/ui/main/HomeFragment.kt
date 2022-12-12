package com.kostlin.gbooksapi.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.kostlin.gbooksapi.R
import com.kostlin.gbooksapi.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.fragment_home.*
import org.json.JSONException
import java.util.ArrayList

class HomeFragment : Fragment() {
    private lateinit var binding: ActivityMainBinding
    private var mRequestQueue: RequestQueue? = null
    private var bookInfoArrayList: ArrayList<BookInfo>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)

        imageButtonSearch.setOnClickListener(View.OnClickListener {
            progressBar.visibility = View.VISIBLE

            //проверяем, заполненно ли поле поиска
            if (editTextSearchBooks.text.toString().isEmpty()) {
                editTextSearchBooks.error = "Введите название"
                progressBar.visibility = View.GONE
                return@OnClickListener
            }
            getBooksInfo(editTextSearchBooks.text.toString())
        })
        super.onViewCreated(view, savedInstanceState)
    }

    private fun getBooksInfo(query: String) {
        bookInfoArrayList = ArrayList<BookInfo>()
        mRequestQueue = Volley.newRequestQueue(this.context)
        mRequestQueue!!.cache.clear()

        val apiKey = "AIzaSyDfqz4QevoekC1Rx6kME-DOHyWs5QNC19c"
        val url = "https://www.googleapis.com/books/v1/volumes?q=$query&printType=books&key=$apiKey"
        // создаём запрос
        val queue = Volley.newRequestQueue(this.context)

        // запрашиваем json объект
        val booksRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                progressBar.visibility = View.GONE
                // в респонс передаём все данные, которые запросили
                try {
                    val itemsArray = response.getJSONArray("items")
                    for (i in 0 until itemsArray.length()) {
                        val itemsObj = itemsArray.getJSONObject(i)
                        val volumeObj = itemsObj.getJSONObject("volumeInfo")
                        val title = volumeObj.optString("title")
                        val authorsArray = volumeObj.getJSONArray("authors")
                        val publishedDate = volumeObj.optString("publishedDate")
                        val description = volumeObj.optString("description")
                        val imageLinks = volumeObj.optJSONObject("imageLinks")

                        var thumbnail = ""
                        if (imageLinks == null) thumbnail = ""
                        else {
                            thumbnail = imageLinks.optString("thumbnail")
                            thumbnail = thumbnail.replace("http", "https")
                        }

                        val authorsArrayList = ArrayList<String>()

                        if (authorsArray.length() != 0) {
                            for (i in 0 until authorsArray.length()) {
                                authorsArrayList.add(authorsArray.optString(i))
                            }
                        }
                        // после того, как мы всё получили, сохраняем данные
                        val bookInfo = BookInfo(
                            title,
                            authorsArrayList,
                            publishedDate,
                            description,
                            thumbnail
                        )
                        bookInfoArrayList!!.add(bookInfo)
                    }
                    // передаём в адаптер
                    val adapter = BookAdapter(bookInfoArrayList!!, this.requireContext())
                    // создаём Recycler
                    val linearLayoutManager =
                        LinearLayoutManager(this.context, RecyclerView.VERTICAL, false)
                    val mRecyclerView = RvBooks
                    mRecyclerView.layoutManager = linearLayoutManager
                    mRecyclerView.adapter = adapter
                } catch (e: JSONException) {
                    e.printStackTrace()
                    // выдаём ошибку, если ничего не найдено
                    Toast.makeText(this.context, "Ничего не найдено", Toast.LENGTH_SHORT).show()
                }
            }) { error ->
            //если у нас другая ошибка
            Toast.makeText(this.context, "Ошибка:  $error", Toast.LENGTH_SHORT).show()
        }
        // и теперь добавляем наш json объект
        queue.add(booksRequest)
    }
}
