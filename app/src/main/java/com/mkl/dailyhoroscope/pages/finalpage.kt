package com.mkl.dailyhoroscope.pages
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.util.Calendar
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
fun finalPage() {
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
         //   HoroscopeDataScreen(horoscopeSign = horoscopeSign)
        }
    }
}
