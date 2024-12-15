package com.mkl.dailyhoroscope.pages

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


@Composable
fun HoroscopeDataScreen(horoscopeSign: String) {
    var itemCareer by remember { mutableStateOf<String>("") }
    var itemHealth by remember { mutableStateOf<String>("") }
    var itemMoney by remember { mutableStateOf<String>("") }
    var itemLove by remember { mutableStateOf<String>("") }
    var itemComChart by remember { mutableStateOf<String>("") }
    var itemSignAttributes by remember { mutableStateOf<String>("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(horoscopeSign) {
        try {
            val firestore = FirebaseFirestore.getInstance()
            val snapshot = firestore.collection(horoscopeSign).get().await()
            val firstDocument = snapshot.documents.firstOrNull()
            itemCareer = firstDocument?.getString("Career") ?: "No Data"
            itemHealth = firstDocument?.getString("Health") ?: "No Data"
            itemMoney = firstDocument?.getString("Money") ?: "No Data"
            itemLove = firstDocument?.getString("Love") ?: "No Data"
            itemComChart = firstDocument?.getString("Compatibility Chart") ?: "No Data"
            itemSignAttributes = firstDocument?.getString("Sign Attributes") ?: "No Data"
        } catch (e: Exception) {
            error = e.localizedMessage
        } finally {
            loading = false
        }
    }

    if (loading) {
        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
    } else if (error != null) {
        println(error)
        Text(text = "Error: $error", modifier = Modifier.padding(16.dp))
    } else {
        LazyColumn(modifier = Modifier.padding(16.dp)) {
            item{
                Text(
                    text = "${itemCareer}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "${itemMoney}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "${itemHealth}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "${itemLove}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "${itemComChart}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
                Text(
                    text = "${itemSignAttributes}",
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}