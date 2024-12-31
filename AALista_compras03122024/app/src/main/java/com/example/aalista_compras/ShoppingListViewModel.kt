package com.example.aalista_compras

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.text.SimpleDateFormat
import java.util.Locale

class ShoppingListViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()
    private val _shoppingList = MutableStateFlow<List<ShoppingItem>>(emptyList())
    val shoppingList: StateFlow<List<ShoppingItem>> = _shoppingList

    init {
        loadShoppingLists()
    }

    private fun loadShoppingLists() {
        db.collection("shoppingLists")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val lists = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(ShoppingItem::class.java)?.copy(id = doc.id)
                    }
                    _shoppingList.value = lists
                }
            }
    }

    fun addItem(name: String) {
        val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(System.currentTimeMillis())
        val newItem = ShoppingItem(name = name, date = date)
        db.collection("shoppingLists").add(newItem)
    }

    fun addArticleToItem(itemId: String, article: Article) {
        val itemRef = db.collection("shoppingLists").document(itemId)
        itemRef.get().addOnSuccessListener { document ->
            val currentItem = document.toObject(ShoppingItem::class.java)
            if (currentItem != null) {
                val updatedArticles = currentItem.articles + article
                itemRef.update("articles", updatedArticles)
            }
        }
    }

    fun updateArticleCheckState(itemId: String, articleName: String, isChecked: Boolean) {
        val itemRef = db.collection("shoppingLists").document(itemId)
        itemRef.get().addOnSuccessListener { document ->
            val currentItem = document.toObject(ShoppingItem::class.java)
            if (currentItem != null) {
                val updatedArticles = currentItem.articles.map { article ->
                    if (article.name == articleName) {
                        article.copy(isChecked = isChecked)
                    } else {
                        article
                    }
                }
                itemRef.update("articles", updatedArticles)
            }
        }
    }
}
