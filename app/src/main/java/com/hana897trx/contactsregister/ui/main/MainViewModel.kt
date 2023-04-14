package com.hana897trx.contactsregister.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hana897trx.contactsregister.data.socket.model.DeviceModel
import com.hana897trx.contactsregister.data.socket.model.InstructionSet
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.domain.ReceiveSocketUC
import com.hana897trx.contactsregister.domain.SendSocketUC
import com.hana897trx.contactsregister.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sendSocketUC: SendSocketUC,
    private val receiveSocketUC: ReceiveSocketUC,
): ViewModel() {
    init {
        receiveSocket()
    }
    fun sendMessage(text: String) {
        val payload = SocketPayload(device = DeviceModel.CLIENT, instruction = InstructionSet.OK, payload = text)
        viewModelScope.launch {
            sendSocketUC(payload).onEach {
                Log.w("MainViewModel", "Frontend Received: $it")
            }.launchIn(viewModelScope)
        }
    }

    private fun receiveSocket() = viewModelScope.launch {
        receiveSocketUC().collect { response ->
            when(response) {
                is ResourceState.Error -> {}
                is ResourceState.Idle -> {}
                is ResourceState.Loading -> {}
                is ResourceState.Success -> {
                    Log.w("DATA RECEIVED IN FRONT", response.data.toString())
                }
            }
        }
    }
}