package com.hana897trx.contactsregister.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hana897trx.contactsregister.ui.main.MainViewModel
import com.hana897trx.contactsregister.ui.theme.ContactsRegisterTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ContactsRegisterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    ConnectionUI()
                }
            }
        }
    }
}

@Composable
fun ConnectionUI() {
    val viewModel = hiltViewModel<MainViewModel>()
    val (textState, setTextState) = remember { mutableStateOf(String()) }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            text = "Hoori Apps, deployed by Hana897TRX",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
        )
        TextField(
            modifier = Modifier.padding(top = 16.dp),
            value = textState,
            onValueChange = setTextState, placeholder = {
            Text(text = "Send to websocket")
        })
        Button(
            modifier = Modifier.padding(top = 16.dp),
            onClick = {
                viewModel.sendMessage(textState)
                setTextState(String())
            },
        ) {
            Text(
                text = "Send message",
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContactsRegisterTheme {
        ConnectionUI()
    }
}