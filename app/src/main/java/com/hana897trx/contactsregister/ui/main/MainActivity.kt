package com.hana897trx.contactsregister.ui.main

import android.Manifest
import android.content.ContentProviderOperation
import android.content.Context
import android.content.OperationApplicationException
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.RemoteException
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.hana897trx.contactsregister.ui.theme.ContactsRegisterTheme
import com.hana897trx.contactsregister.utils.TAGS.PERMISSION_TAG
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val launcherRequest: ActivityResultLauncher<String> = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) {}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.w(PERMISSION_TAG, launcherRequest.contract.toString())
        setContent {
            ContactsRegisterTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    ConnectionUI() {
                        launcherRequest.launch(Manifest.permission.WRITE_CONTACTS)
                    }
                }
            }
        }
    }
}

@Composable
fun ConnectionUI(
    requestContactPermission: () -> Unit,
) {
    val viewModel = hiltViewModel<MainViewModel>()
    val (textState, setTextState) = remember { mutableStateOf(String()) }
    val ctx: Context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(modifier = Modifier.padding(top = 16.dp),
            value = textState,
            onValueChange = setTextState,
            placeholder = {
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
        Button(
            onClick = {
                if (ContextCompat.checkSelfPermission(
                        ctx,
                        Manifest.permission.WRITE_CONTACTS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    Toast.makeText(ctx, "Permission already granted", Toast.LENGTH_SHORT).show()
                } else {
                    requestContactPermission()
                }
            }) {
            Text(
                text = "Request contact permission"
            )
        }
        Button(onClick = { RegisterContact(ctx) }) {
            Text(text = "TEST ADD CONTACT")
        }
    }
}

fun RegisterContact(ctx: Context) {
    val contentProvideOperation = ArrayList<ContentProviderOperation>()
    contentProvideOperation.add(
        ContentProviderOperation.newInsert(
            ContactsContract.RawContacts.CONTENT_URI
        )
            .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
            .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).build()
    )
    
    contentProvideOperation.add(
        ContentProviderOperation
            .newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
            )
            .withValue(
                ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                "NAME"
            ).build()
    )
    
    contentProvideOperation.add(
        ContentProviderOperation
            .newInsert(ContactsContract.Data.CONTENT_URI)
            .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
            .withValue(
                ContactsContract.Data.MIMETYPE,
                ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
            )
            .withValue(
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                "7771020921"
            )
            .withValue(
                ContactsContract.CommonDataKinds.Phone.TYPE,
                ContactsContract.CommonDataKinds.Phone.TYPE_WORK
            )
            .build())
    try {
        ctx.contentResolver.applyBatch(ContactsContract.AUTHORITY, contentProvideOperation)
    } catch (e: OperationApplicationException) {
        e.printStackTrace()
    } catch (e: RemoteException) {
        e.printStackTrace()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ContactsRegisterTheme {
        ConnectionUI() {}
    }
}