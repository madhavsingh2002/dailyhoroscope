package com.mkl.dailyhoroscope.pages

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.firestore.FirebaseFirestore
import com.mkl.dailyhoroscope.R
import kotlinx.coroutines.tasks.await
import android.text.format.DateFormat
import androidx.compose.ui.platform.LocalContext

@Composable
fun HoroscopeDataScreen(horoscopeSign: String, dateRange: String?) {
    var itemCareer by remember { mutableStateOf<String>("") }
    var itemHealth by remember { mutableStateOf<String>("") }
    var itemMoney by remember { mutableStateOf<String>("") }
    var itemLove by remember { mutableStateOf<String>("") }
    var itemComChart by remember { mutableStateOf<String>("") }
    var itemSignAttributes by remember { mutableStateOf<String>("") }
    var loading by remember { mutableStateOf(true) }
    var error by remember { mutableStateOf<String?>(null) }
    var context = LocalContext.current
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
            if (loading) {
                CircularProgressIndicator(modifier = Modifier.padding(16.dp))
            } else if (error != null) {
                println(error)
            } else {
                LazyColumn(modifier = Modifier.padding(16.dp)) {
                    item{
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ){
                            Box(
                                modifier = Modifier
                                    //.fillMaxWidth()
                                    .size(125.dp)
                                    .border(
                                        width = 1.dp, color = Color(0xFF5B5C60),
                                        shape = RoundedCornerShape(12.dp))
                                    .clip(RoundedCornerShape(12.dp))
                            ){
                                Image(
                                    painter = painterResource(id = R.drawable.aries), // Replace with your drawable resource
                                    contentDescription = "Background Image",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )

                            }
                            Column(
                                modifier = Modifier.padding(12.dp).fillMaxSize(),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ){
                                Text(text = "$horoscopeSign", color = Color.White, fontSize = 28.sp, fontWeight = FontWeight.Bold)
                                Spacer(modifier = Modifier.height(16.dp))
                                Text(text = dateRange!!, color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                    item{
                        Spacer(modifier = Modifier.height(12.dp))
                        val currentDateTime = DateFormat.getMediumDateFormat(context).format(System.currentTimeMillis())
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        )
                        {
                            Text(text = "Today: $currentDateTime", textAlign = TextAlign.Center, color = Color.White)
                        }
                    }
                    item {
                        Spacer(modifier = Modifier.height(12.dp))
                        SignCard(label = "Career", content = itemCareer)
                        SignCard(label = "Money", content = itemMoney)
                        SignCard(label = "Health", content = itemHealth)
                        SignCard(label = "Love", content = itemLove)
                        SignCard(label = "Compatibility Chart", content = itemComChart)
                        SignCard(label = "Sign Attributes", content = itemSignAttributes)
                    }
                }
            }
        }
    )
}
@Composable
fun SignCard(label: String, content: String){
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF343138) // Set the card's background color
        ),
        modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp)
            .border(width = 1.dp, color = Color(0xFF5B5C60), shape = RoundedCornerShape(12.dp))
    ){
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp)
        ){
            Text(text = label, color =  Color.White, fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                color = Color(0xFFD6D6D6),
                thickness = 1.dp
            )
            Text(text = content, color = Color(0xFFEDEDED))
        }

    }
    Spacer(modifier = Modifier.height(12.dp))
}