package com.oldgrangehill.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.oldgrangehill.app.ui.theme.OldGrangehillTheme
import androidx.compose.material3.OutlinedTextField

private val Black = Color(0xFF000000)
private val SoftBlack = Color(0xFF080808)
private val Cream = Color(0xFFF6F1E8)
private val Gold = Color(0xFFD3B36F)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            OldGrangehillTheme {
                var screen by remember { mutableStateOf("home") }
                var selectedEventType by remember { mutableStateOf("") }
                var selectedGuestRange by remember { mutableStateOf("") }
                var selectedTiming by remember { mutableStateOf("") }
                var contactName by remember { mutableStateOf("") }
                var contactEmail by remember { mutableStateOf("") }
                var contactPhone by remember { mutableStateOf("") }

                when (screen) {
                    "home" -> HomeScreen(
                        onRegisterClick = { screen = "eventType" }
                    )

                    "eventType" -> EventTypeScreen(
                        onBackClick = { screen = "home" },
                        onEventTypeSelected = { eventType ->
                            selectedEventType = eventType
                            screen = "guestNumbers"
                        }
                    )

                    "guestNumbers" -> GuestNumbersScreen(
                        selectedEventType = selectedEventType,
                        onBackClick = { screen = "eventType" },
                        onGuestRangeSelected = { guestRange ->
                            selectedGuestRange = guestRange
                            screen = "preferredTiming"
                        }
                    )

                    "preferredTiming" -> PreferredTimingScreen(
                        selectedEventType = selectedEventType,
                        selectedGuestRange = selectedGuestRange,
                        onBackClick = { screen = "guestNumbers" },
                        onTimingSelected = { timing ->
                            selectedTiming = timing
                            screen = "contactDetails"
                        }
                    )

                    "contactDetails" -> ContactDetailsScreen(
                        selectedEventType = selectedEventType,
                        selectedGuestRange = selectedGuestRange,
                        selectedTiming = selectedTiming,
                        onBackClick = { screen = "preferredTiming" },
                        onContinueClick = { name, email, phone ->
                            contactName = name
                            contactEmail = email
                            contactPhone = phone
                            screen = "additionalInfo"
                        }
                    )

                    "additionalInfo" -> AdditionalInfoScreen(
                        selectedEventType = selectedEventType,
                        selectedGuestRange = selectedGuestRange,
                        selectedTiming = selectedTiming,
                        contactName = contactName,
                        contactEmail = contactEmail,
                        contactPhone = contactPhone,
                        onBackClick = { screen = "contactDetails" },
                        onSubmitClick = {
                            screen = "thankYou"
                        }
                    )

                    "thankYou" -> ThankYouScreen(
                        onStartAgainClick = {
                            screen = "home"
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onRegisterClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(SoftBlack, Black, SoftBlack)))
            .padding(horizontal = 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = R.drawable.grangehill_logo),
                contentDescription = "Grangehill logo",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .height(220.dp)
            )

            Spacer(Modifier.height(42.dp))

            Text(
                text = "PRIVATE DINING · WEDDINGS · EVENTS",
                color = Gold,
                fontSize = 15.sp,
                letterSpacing = 5.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            Spacer(Modifier.height(26.dp))

            Box(
                modifier = Modifier
                    .width(150.dp)
                    .height(1.dp)
                    .background(Gold.copy(alpha = 0.65f))
            )

            Spacer(Modifier.height(28.dp))

            Text(
                text = "SPRING HILL, QUEENSLAND",
                color = Cream,
                fontSize = 14.sp,
                letterSpacing = 5.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(58.dp))

            Button(
                onClick = onRegisterClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(62.dp)
            ) {
                Text(
                    text = "REGISTER INTEREST",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 4.sp
                )
            }

            Spacer(Modifier.height(38.dp))

            Box(
                modifier = Modifier
                    .width(52.dp)
                    .height(1.dp)
                    .background(Gold.copy(alpha = 0.55f))
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "OPENING LATE 2026",
                color = Cream.copy(alpha = 0.9f),
                fontSize = 14.sp,
                letterSpacing = 6.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun EventTypeScreen(
    onBackClick: () -> Unit,
    onEventTypeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "What are you planning?",
                color = Cream,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "Select the event type that best suits your enquiry.",
                color = Cream.copy(alpha = 0.75f),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(42.dp))

            EventTypeButton("Wedding") { onEventTypeSelected("Wedding") }
            EventTypeButton("Corporate Event") { onEventTypeSelected("Corporate Event") }
            EventTypeButton("Private Dining") { onEventTypeSelected("Private Dining") }
            EventTypeButton("Celebration") { onEventTypeSelected("Celebration") }
            EventTypeButton("Other") { onEventTypeSelected("Other") }

            Spacer(Modifier.height(36.dp))

            TextButton(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Gold
                )
            }
        }
    }
}

@Composable
fun EventTypeButton(label: String, onClick: () -> Unit) {
    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.outlinedButtonColors(
            contentColor = Cream
        ),
        shape = RoundedCornerShape(999.dp),
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .padding(vertical = 5.dp)
    ) {
        Text(
            text = label.uppercase(),
            letterSpacing = 3.sp,
            fontSize = 13.sp
        )
    }
}

@Composable
fun GuestNumbersScreen(
    selectedEventType: String,
    onBackClick: () -> Unit,
    onGuestRangeSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = selectedEventType.uppercase(),
                color = Gold,
                fontSize = 13.sp,
                letterSpacing = 5.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "How many guests are you expecting?",
                color = Cream,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "An estimate is fine. This helps us understand which areas of Grangehill may suit your event.",
                color = Cream.copy(alpha = 0.72f),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(42.dp))

            EventTypeButton("2 - 10") { onGuestRangeSelected("2 - 10") }
            EventTypeButton("11 - 20") { onGuestRangeSelected("11 - 20") }
            EventTypeButton("21 - 40") { onGuestRangeSelected("21 - 40") }
            EventTypeButton("41 - 60") { onGuestRangeSelected("41 - 60") }
            EventTypeButton("61 - 80") { onGuestRangeSelected("61 - 80") }
            EventTypeButton("81+") { onGuestRangeSelected("81+") }

            Spacer(Modifier.height(36.dp))

            TextButton(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Gold
                )
            }
        }
    }
}

@Composable
fun PreferredTimingScreen(
    selectedEventType: String,
    selectedGuestRange: String,
    onBackClick: () -> Unit,
    onTimingSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "${selectedEventType.uppercase()} · $selectedGuestRange GUESTS",
                color = Gold,
                fontSize = 12.sp,
                letterSpacing = 4.sp,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "When are you thinking?",
                color = Cream,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                lineHeight = 32.sp
            )

            Spacer(Modifier.height(12.dp))

            Text(
                text = "This helps us understand whether your enquiry is for early access, a future event, or a flexible date.",
                color = Cream.copy(alpha = 0.72f),
                fontSize = 15.sp,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp
            )

            Spacer(Modifier.height(42.dp))

            EventTypeButton("Within 3 Months") {
                onTimingSelected("Within 3 Months")
            }

            EventTypeButton("3 - 6 Months") {
                onTimingSelected("3 - 6 Months")
            }

            EventTypeButton("6 - 12 Months") {
                onTimingSelected("6 - 12 Months")
            }

            EventTypeButton("12+ Months") {
                onTimingSelected("12+ Months")
            }

            EventTypeButton("Not Sure Yet") {
                onTimingSelected("Not Sure Yet")
            }

            Spacer(Modifier.height(36.dp))

            TextButton(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Gold
                )
            }
        }
    }
}

@Composable
fun ContactDetailsScreen(
    selectedEventType: String,
    selectedGuestRange: String,
    selectedTiming: String,
    onBackClick: () -> Unit,
    onContinueClick: (String, String, String) -> Unit
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "YOUR DETAILS",
                color = Gold,
                fontSize = 12.sp,
                letterSpacing = 4.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Tell us how to contact you",
                color = Cream,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "$selectedEventType · $selectedGuestRange · $selectedTiming",
                color = Cream.copy(alpha = 0.65f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                label = { Text("Phone") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(28.dp))

            Button(
                onClick = {
                    onContinueClick(name, email, phone)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(
                    text = "CONTINUE",
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(24.dp))

            TextButton(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Gold
                )
            }
        }
    }
}

@Composable
fun AdditionalInfoScreen(
    selectedEventType: String,
    selectedGuestRange: String,
    selectedTiming: String,
    contactName: String,
    contactEmail: String,
    contactPhone: String,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit
) {
    var notes by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "FINAL DETAILS",
                color = Gold,
                fontSize = 12.sp,
                letterSpacing = 4.sp
            )

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Tell us about your event",
                color = Cream,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(12.dp))

            OutlinedTextField(
                value = notes,
                onValueChange = { notes = it },
                label = { Text("Additional Information") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            )

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onSubmitClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("SUBMIT ENQUIRY")
            }

            Spacer(Modifier.height(24.dp))

            TextButton(onClick = onBackClick) {
                Text(
                    text = "Back",
                    color = Gold
                )
            }
        }
    }
}

@Composable
fun ThankYouScreen(
    onStartAgainClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Black)
            .padding(28.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Text(
                text = "THANK YOU",
                color = Gold,
                fontSize = 14.sp,
                letterSpacing = 6.sp
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "Your enquiry has been received.",
                color = Cream,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(16.dp))

            Text(
                text = "Our team will be in touch shortly.",
                color = Cream.copy(alpha = 0.75f),
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(40.dp))

            Button(
                onClick = onStartAgainClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Gold,
                    contentColor = Black
                ),
                shape = RoundedCornerShape(999.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text("START AGAIN")
            }
        }
    }
}