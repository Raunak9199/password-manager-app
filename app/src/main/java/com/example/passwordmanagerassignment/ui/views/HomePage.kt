package com.example.passwordmanagerassignment.ui.views


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerassignment.R
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.viewmodel.PasswordViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    passwordViewModel: PasswordViewModel,
    onAddPassword: () -> Unit,
    onEditPassword: (PasswordEntry) -> Unit
) {
    val passwords by passwordViewModel.passwords.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        passwordViewModel.getAllPasswords()
    }

    Scaffold(containerColor = Color(0xFFE8E8E8), topBar = {
        TopAppBar(title = { Text("Password Manager") })
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = { onAddPassword() }, containerColor = Color(0xFF3F7DE3)
        ) {
            Image(painter = painterResource(id = R.drawable.union), contentDescription = null)
        }
    }, content = { padding ->
        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 12.dp, vertical = 20.dp)
        ) {
            items(passwords) { password ->
                PasswordItem(padding, password = password) {
                    onEditPassword(password)
                }
            }
        }
    })
}


@Composable
fun PasswordItem(padding: PaddingValues, password: PasswordEntry, onEditPassword: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(50.dp))
            .border(BorderStroke(0.dp, Color.Transparent), shape = RoundedCornerShape(50.dp))
            .padding(vertical = 8.dp)
            .background(Color.White)
            .clickable { onEditPassword() },
        elevation = CardDefaults.cardElevation(1.dp)
    ) {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(50.dp))
                .border(BorderStroke(0.dp, Color.Transparent), shape = RoundedCornerShape(50.dp))
                .background(Color.White)
                .padding(16.dp)
                .fillMaxWidth()

        ) {
            Text(text = password.accountName, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                modifier = Modifier.padding(top = 4.dp),
                text = "*".repeat(6),
                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                color = Color.Gray
            )
            Spacer(modifier = Modifier.weight(1F))
            Image(
                modifier = Modifier.padding(top = 4.dp),
                painter = painterResource(id = R.drawable.arrow_forward),
                contentDescription = null
            )

        }
    }
}
