package com.example.passwordmanagerassignment.ui.views

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.passwordmanagerassignment.R
import com.example.passwordmanagerassignment.data.PasswordEntry
import com.example.passwordmanagerassignment.utils.EncryptionUtil
import com.example.passwordmanagerassignment.utils.PasswordGenerator
import com.example.passwordmanagerassignment.viewmodel.PasswordViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordScreen(
    onAddPassword: (PasswordEntry) -> Unit,
    onDeletePassword: (PasswordEntry) -> Unit,
    passwordEntry: PasswordEntry? = null,
    passwordViewModel: PasswordViewModel,
    onDismiss: () -> Unit
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    ModalBottomSheet(sheetState = sheetState, onDismissRequest = {
        scope.launch {
            onDismiss()
            sheetState.hide()
        }
    }, content = {
        AddPasswordBottomSheet(
            onAddPassword = onAddPassword,
            onDeletePassword = onDeletePassword,
            passwordEntry = passwordEntry,
            passwordViewModel = passwordViewModel,
        )
    })

    LaunchedEffect(Unit) {
        sheetState.show()
    }
//    LaunchedEffect(sheetState.currentValue) {
//        if (sheetState.currentValue == ModalBottomSheetValue.Hidden) {
//            onDismiss()
//        }
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPasswordBottomSheet(
    onAddPassword: (PasswordEntry) -> Unit,
    onDeletePassword: (PasswordEntry) -> Unit,
    passwordEntry: PasswordEntry? = null,
    passwordViewModel: PasswordViewModel,
) {
    val id by remember { mutableIntStateOf(0) }

    var accountName by remember { mutableStateOf(passwordEntry?.accountName ?: "") }
    var usernameEmail by remember { mutableStateOf(passwordEntry?.usernameEmail ?: "") }
    var password by remember { mutableStateOf(passwordEntry?.encryptedPassword ?: "") }
    var decryptedPassword by remember { mutableStateOf<String?>(null) }

    var passwordVisible by remember { mutableStateOf(false) }

    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }

    LaunchedEffect(passwordEntry) {
        if (passwordEntry != null) {
            accountName = passwordEntry.accountName
            usernameEmail = passwordEntry.usernameEmail
            password = passwordEntry.encryptedPassword
            decryptedPassword = EncryptionUtil.decrypt(passwordEntry.encryptedPassword)
        }
    }

    Column(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        if (passwordEntry != null) {
            Text(
                text = "Account Details", style = TextStyle(
                    color = Color(0xFF3F7DE3),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            )
        }
        if (passwordEntry != null) Spacer(modifier = Modifier.height(18.dp))
        OutlinedTextField(
            value = accountName,
            onValueChange = {
                accountName = it
                Log.d("AddPasswordBottomSheet", "Account Name changed: $it")
            },
            label = { Text("Account Name") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFCBCBCB),
                unfocusedBorderColor = Color(0xFFCBCBCB),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = usernameEmail,
            onValueChange = {
                usernameEmail = it
                Log.d("AddPasswordBottomSheet", "Username/Email changed: $it")
            },
            label = { Text("Username/Email") },
            modifier = Modifier.fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Black, fontWeight = FontWeight.Bold
            ),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color(0xFFCBCBCB),
                unfocusedBorderColor = Color(0xFFCBCBCB),
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        (if (decryptedPassword != null) decryptedPassword else password)?.let {
            OutlinedTextField(
                value = it,
                onValueChange = {
                    if (decryptedPassword != null) decryptedPassword = it else password = it
                    Log.d("AddPasswordBottomSheet", "Password changed: $it")
                },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold
                ),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFCBCBCB),
                    unfocusedBorderColor = Color(0xFFCBCBCB),
                ),
                trailingIcon = {
                    val image = if (passwordVisible)
                        ImageVector.vectorResource(id = R.drawable.baseline_visibility_24)
                    else ImageVector.vectorResource(id = R.drawable.baseline_visibility_off_24)

                    IconButton(onClick = {
                        passwordVisible = !passwordVisible
                    }) {
                        Icon(imageVector = image, contentDescription = if (passwordVisible) "Hide password" else "Show password")
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        Row(
            horizontalArrangement = Arrangement.End
        ) {

                Text("Generate Random Password", fontSize = 15.sp, color = Color.Blue.copy(alpha = 0.6F),
                    fontWeight = FontWeight.W500,
                    modifier = Modifier
                        .clickable {
                            if (decryptedPassword != null) decryptedPassword =
                                PasswordGenerator.generatePassword(10) else password =
                                PasswordGenerator.generatePassword(10)
                        }
                        .fillMaxWidth()
                    )
            }
//        }
        Spacer(modifier = Modifier.height(10.dp))
        Row {
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2C2C2C)
                ), onClick = {
                    if (accountName.isNotBlank() && usernameEmail.isNotBlank() && password.isNotBlank()) {
                        if (passwordEntry != null) {
                            onAddPassword(
                                passwordEntry.copy(
                                    accountName = accountName,
                                    usernameEmail = usernameEmail,
                                    encryptedPassword = decryptedPassword ?: password
                                )
                            )
                            Log.d("AddPasswordBottomSheet", "Edited Password Entry: $passwordEntry")
                        } else {
                            val newEntry = PasswordEntry(id, accountName, usernameEmail, password)
                            onAddPassword(newEntry)
                            Log.d("AddPasswordBottomSheet", "Added New Password Entry: $newEntry")
                        }
                    } else {
                        showToast = true
                    }
                }, modifier = Modifier
                    .weight(0.4f)
                    .height(50.dp)
            ) {
                Text(if (passwordEntry != null) "Edit" else "Add New Account")
            }

            if (passwordEntry != null) Spacer(modifier = Modifier.width(16.dp))
            if (passwordEntry != null) {
                Button(
                    onClick = {
                        onDeletePassword(passwordEntry)
                        Log.d("AddPasswordBottomSheet", "Deleted Password Entry: $passwordEntry")
                    },
                    modifier = Modifier
                        .weight(0.4f)
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF04646)
                    )
                ) {
                    Text("Delete")
                }
            }
        }

        if (showToast) {
            LaunchedEffect(showToast) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                showToast = false
            }
        }
    }
}


