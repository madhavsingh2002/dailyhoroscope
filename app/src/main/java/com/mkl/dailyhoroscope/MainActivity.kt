package com.mkl.dailyhoroscope

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.mkl.dailyhoroscope.ui.theme.DailyhoroscopeTheme
import kotlinx.coroutines.tasks.await
import android.text.format.DateFormat
import androidx.compose.material3.Button
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        try {
            FirebaseApp.initializeApp(this)
            Log.d("FirebaseInit", "Firebase initialized successfully")
        } catch (e: Exception) {
            Log.e("FirebaseInit", "Firebase failed to initialize", e)
        }
        enableEdgeToEdge()
        setContent {
            DailyhoroscopeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                        val currentDateTime = DateFormat.getMediumDateFormat(this@MainActivity).format(System.currentTimeMillis())
                        Text(text = "Today's Date and Time: $currentDateTime")
                        Spacer(modifier = Modifier.height(16.dp))
                        DatePickerExample()
                    }
                }
            }
        }
    }
}
fun getHoroscopeSign(day: Int, month: Int): String {
    return when (month) {
        1 -> if (day <= 19) "Capricorn" else "Aquarius"
        2 -> if (day <= 18) "Aquarius" else "Pisces"
        3 -> if (day <= 20) "Pisces" else "Aries"
        4 -> if (day <= 19) "Aries" else "Taurus"
        5 -> if (day <= 20) "Taurus" else "Gemini"
        6 -> if (day <= 20) "Gemini" else "Cancer"
        7 -> if (day <= 22) "Cancer" else "Leo"
        8 -> if (day <= 22) "Leo" else "Virgo"
        9 -> if (day <= 22) "Virgo" else "Libra"
        10 -> if (day <= 22) "Libra" else "Scorpio"
        11 -> if (day <= 21) "Scorpio" else "Sagittarius"
        12 -> if (day <= 21) "Sagittarius" else "Capricorn"
        else -> "Unknown"
    }
}
@Composable
fun DatePickerExample() {
    var selectedDate by remember { mutableStateOf("") }
    var horoscopeSign by remember { mutableStateOf("") }

    val calendar = Calendar.getInstance()
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, selectedYear, selectedMonth, selectedDay ->
            selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
            horoscopeSign = getHoroscopeSign(selectedDay, selectedMonth + 1)
        }, year, month, day
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Text(
            text = if (selectedDate.isEmpty()) "Select Your Birthday" else "Selected Date: $selectedDate",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Button(onClick = { datePickerDialog.show() }) {
            Text(text = "Pick Date")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (horoscopeSign.isNotEmpty()) {
            Text(
                text = "Your Horoscope Sign: $horoscopeSign",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            FirestoreDataScreen(horoscopeSign = horoscopeSign)
        }
    }
}

@Composable
fun FirestoreDataScreen(horoscopeSign: String) {
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
// Love, Career, money, health, Sign Attributes, Compatibility Chart.