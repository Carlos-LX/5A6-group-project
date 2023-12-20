import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bookcraft.data.Book
import com.example.bookcraftapplication.data.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class DetailsViewModel : ViewModel() {

    // LiveData or State for reviews
    // You might want to use State if you're on Compose version 1.0.0-beta02 or higher
    // If you're using an older version, you can use LiveData
    // val reviews = MutableLiveData<List<Review>>()

    var reviews = mutableStateOf<List<Review>>(emptyList())

    fun fetchReviewsForBook(book: Book, db: FirebaseFirestore) {
        viewModelScope.launch {
            try {
                val reviewsSnapshot = db.collection("reviews")
                    .whereEqualTo("Book", book.name)
                    .get()
                    .await()

                val newReviews = mutableListOf<Review>()
                for (document in reviewsSnapshot.documents) {
                    val rating = document.getDouble("Rating")?.toFloat() ?: 0f
                    val title = document.getString("Title") ?: ""
                    val description = document.getString("Description") ?: ""
                    val review = Review(rating, title, description)
                    newReviews.add(review)
                }

                // Update the reviews state
                reviews.value = newReviews
            } catch (e: Exception) {
                // Handle the exception
                Log.e("DetailsViewModel", "Error fetching reviews: ${e.message}")
            }
        }
    }
}