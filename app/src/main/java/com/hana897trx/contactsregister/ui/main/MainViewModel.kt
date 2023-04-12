package com.hana897trx.contactsregister.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.contactsregister.data.model.SocketResponse
import com.hana897trx.contactsregister.domain.SendSocketUC
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val socketUC: SendSocketUC,
): ViewModel() {
    fun sendMessage(text: String) {
        val payload: SocketResponse = SocketResponse(instruction = "SEND_MESSAGE", payload = text)
        viewModelScope.launch {
            socketUC(payload).onEach {
                Log.w("MainViewModel", "Frontend Received: $it")
            }.launchIn(viewModelScope)
        }
    }
}