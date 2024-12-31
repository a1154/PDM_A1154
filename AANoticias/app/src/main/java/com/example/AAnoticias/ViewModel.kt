package com.example.AAnoticias

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.io.IOException
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import java.util.Date


data class ArticleState( //Representa o estado da interface do usuário relacionado aos artigos
    val articles: ArrayList<Publicacao> = arrayListOf(),
    val isLoading: Boolean = false,
    val errorMessage: String = "",
)

class InicialViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ArticleState())
    val uiState: StateFlow<ArticleState> = _uiState.asStateFlow()

    fun fetchArticles() {

        var dateNow = Date()

        _uiState.value = ArticleState(
            isLoading = true
        )
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://newsapi.org/v2/everything?q=tesla&from=${dateNow.toStringDateQuery()}&sortBy=publishedAt&apiKey=0826ae09811e48ce9865d7e1aff157ba")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                _uiState.value = ArticleState(
                    errorMessage = e.message ?: "",
                    isLoading = false
                )
            }


            override fun onResponse(call: Call, response: Response) {
                response.use {
                    if (!response.isSuccessful) throw IOException("Unexpected code $response")

                    val articlesResult = arrayListOf<Publicacao>()
                    val result = response.body?.string() ?: ""
                    val jsonObject = JSONObject(result)

                    if (jsonObject.getString("status") == "ok") {
                        val articlesArray = jsonObject.getJSONArray("articles")
                        for (index in 0 until articlesArray.length()) {
                            val articleObject = articlesArray.getJSONObject(index)
                            val article = Publicacao.fromJson(articleObject)
                            articlesResult.add(article)
                        }

                        _uiState.value = ArticleState(
                            articles = articlesResult,
                            isLoading = false
                        )
                    } else {
                        _uiState.value = ArticleState(
                            errorMessage = "Erro a procurar artigos",
                            isLoading = false
                        )
                    }
                }
            }
        })
    }
}