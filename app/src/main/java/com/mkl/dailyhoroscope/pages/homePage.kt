package com.mkl.dailyhoroscope.pages

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
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
import androidx.navigation.NavHostController
import com.mkl.dailyhoroscope.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(navController: NavHostController){
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(12.dp),
            ) {
                item{
                    Box(
                        modifier = Modifier.fillMaxWidth().height(225.dp).clip(RoundedCornerShape(12.dp))
                    ){
                        Image(
                            painter = painterResource(id = R.drawable.planets2), // Replace with your drawable resource
                            contentDescription = "Background Image",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                        Column(
                            modifier = Modifier.padding(12.dp),
                            verticalArrangement = Arrangement.Center
                        ){
                            Text(text = "Get your Personalize\n" + "Daily Horoscope", color = Color.White, fontSize = 24.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(text = "Your stars, your guide \n" + "personalized daily horoscopes.", color = Color(0xFFE0E0E0), fontSize = 18.sp)
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(
                                onClick = {
                                    navController.navigate("finalPage")
                                },
                                modifier = Modifier.height(50.dp).width(150.dp),
                                shape = RoundedCornerShape(12.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(0xFF764AEF),
                                    contentColor = Color.White,
                                ),
                            ) {
                                Text("CONTINUE", color = Color.White)
                                Spacer(modifier = Modifier.width(12.dp))
                                Image(
                                    painter = painterResource(id = R.drawable.forward), // Replace with your drawable resource
                                    contentDescription = "forward",
                                )
                            }
                        }
                    }
                }
                item{
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("Select Your Sign", color = Color(0xFFAEAEAE), fontSize = 20.sp, fontWeight = FontWeight.SemiBold)
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        SignCard("Aries", res = R.drawable.aries, navController, "March 21 - April 19")
                        SignCard("Capricorn", res = R.drawable.capricorn, navController, "December 22 - January 19")
                        SignCard("Aquarius", res = R.drawable.aquarius, navController, "January 20 - February 18")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        SignCard("Pisces", res = R.drawable.pisces, navController, "February 19 - March 20")
                        SignCard("Taurus", res = R.drawable.taurus, navController, "April 20 - May 20")
                        SignCard("Gemini", res = R.drawable.gemini, navController, "May 21 - June 20")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        SignCard("Cancer", res = R.drawable.cancer, navController, "June 21 - July 22")
                        SignCard("Leo", res = R.drawable.leo, navController, "July 23 - August 22")
                        SignCard("Virgo", res = R.drawable.virgo, navController, "August 23 - September 22")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
                item{
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ){
                        SignCard("Libra", res = R.drawable.libra, navController, "September 23 - October 22")
                        SignCard("Scorpio", res = R.drawable.scorpio, navController, "October 23 - November 21" )
                        SignCard("Sagittarius", res = R.drawable.sagittarius, navController, "November 22 - December 21" )
                    }
                }
            }
        }
    )
}
@Composable
fun SignCard(label: String, @DrawableRes res: Int = R.drawable.pisces, navController: NavHostController, dateRange: String = ""){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable{navController.navigate("HoroscopeDataScreen/${label}/${dateRange}")}
    ){
        Card(
            elevation = CardDefaults.cardElevation(4.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0xFF343138) // Set the card's background color
            ),
            modifier = Modifier
                .size(110.dp)
                .border(width = 1.dp, color = Color(0xFF5B5C60), shape = RoundedCornerShape(12.dp))
        ){
            Image(
                painter = painterResource(id = res), // Replace with your drawable resource
                contentDescription = "Background Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(label, color = Color(0xFFAEAEAE))
    }
}
