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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.FirebaseApp
import com.mkl.dailyhoroscope.ui.theme.DailyhoroscopeTheme
import android.text.format.DateFormat
import com.mkl.dailyhoroscope.pages.MyApp

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
//                    Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
////                        val currentDateTime = DateFormat.getMediumDateFormat(this@MainActivity).format(System.currentTimeMillis())
//                        Text(text = "Today's Date and Time: $currentDateTime")
//                        Spacer(modifier = Modifier.height(16.dp))
                        MyApp()
                   // }
                }
            }
        }
    }
}
// 1. Splash Screen.
// 2. Get Started.
// 3. Home Page.



// Love, Career, money, health, Sign Attributes, Compatibility Chart.