package com.mkl.dailyhoroscope.pages
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
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
fun FinalPage(navController: NavController) {
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
            val dateRange = getDateRangeForSign(horoscopeSign)
            navController.navigate("HoroscopeDataScreen/$horoscopeSign/$dateRange")
        }, year, month, day
    )
    Scaffold(
        topBar = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ){
                Text(
                    text = "MKL Studio",
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    modifier = Modifier.padding(12.dp)
                )
            }
        },
        containerColor = Color(0xFF0D050F),
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (selectedDate.isEmpty()) "Select Your Birthday" else "Selected Date: $selectedDate",
                    fontSize = 24.sp,
                    color = Color(0xFFF5F5F5),
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Button(
                    onClick = { datePickerDialog.show() },
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF764AEF),
                        contentColor = Color.White,
                    ),
                    modifier = Modifier.height(50.dp).width(150.dp),
                ) {
                    Text(text = "Pick Date")
                }
            }
        }
    )
}
fun getDateRangeForSign(sign: String): String {
    return when (sign) {
        "Aries" -> "March 21 - April 19"
        "Taurus" -> "April 20 - May 20"
        "Gemini" -> "May 21 - June 20"
        "Cancer" -> "June 21 - July 22"
        "Leo" -> "July 23 - August 22"
        "Virgo" -> "August 23 - September 22"
        "Libra" -> "September 23 - October 22"
        "Scorpio" -> "October 23 - November 21"
        "Sagittarius" -> "November 22 - December 21"
        "Capricorn" -> "December 22 - January 19"
        "Aquarius" -> "January 20 - February 18"
        "Pisces" -> "February 19 - March 20"
        else -> "Unknown"
    }
}
