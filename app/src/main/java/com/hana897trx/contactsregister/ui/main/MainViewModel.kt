package com.hana897trx.contactsregister.ui.main

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.hana897trx.contactsregister.data.contacts.model.ContactsModel
import com.hana897trx.contactsregister.data.contacts.model.ContactsRequest
import com.hana897trx.contactsregister.data.contacts.model.SocketResponse
import com.hana897trx.contactsregister.data.socket.model.DeviceModel
import com.hana897trx.contactsregister.data.socket.model.InstructionSet
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.domain.ReceiveSocketUC
import com.hana897trx.contactsregister.domain.SaveContactsUC
import com.hana897trx.contactsregister.domain.SendSocketUC
import com.hana897trx.contactsregister.utils.ResourceState
import com.hana897trx.contactsregister.utils.removeComas
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sendSocketUC: SendSocketUC,
    private val receiveSocketUC: ReceiveSocketUC,
    private val saveContactsUC: SaveContactsUC,
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

    fun sendMessage(payload: SocketPayload) {
        viewModelScope.launch {
            sendSocketUC(payload).onEach {
                Log.w("MainViewModel", "Frontend Received: $it")
            }.launchIn(viewModelScope)
        }
    }

    private fun receiveSocket() = viewModelScope.launch(IO) {
        try {
            receiveSocketUC().collect { response ->
                when(response) {
                    is ResourceState.Error -> {}
                    is ResourceState.Idle -> {}
                    is ResourceState.Loading -> {}
                    is ResourceState.Success -> {
                        when(response.data.instruction) {
                            InstructionSet.MESSAGE -> {

                            }
                            InstructionSet.REGISTER_CONTACTS -> {
                                val objectData = ObjectMapper().readTree(response.data.payload)

                                val contactsRequest = ContactsRequest(
                                    groupId = objectData["id"].toString().removeComas(),
                                    contacts = objectData["numbers"].map { jsonNode ->
                                        ContactsModel (
                                            name = jsonNode.toString().removeComas(),
                                            number = jsonNode.toString().removeComas()
                                        )
                                    }
                                )

                                saveContactsUC(contactsRequest).onEach { localResponse ->
                                    when(localResponse) {
                                        is ResourceState.Error -> {}
                                        is ResourceState.Idle -> {}
                                        is ResourceState.Loading -> {}
                                        is ResourceState.Success -> {
                                            val payload = SocketPayload(
                                                device = DeviceModel.CLIENT,
                                                instruction = InstructionSet.REGISTER_CONTACTS,
                                                payload = Gson().toJson(SocketResponse(id = contactsRequest.groupId, status = InstructionSet.SUCCESS))
                                            )
                                            sendMessage(payload)
                                        }
                                    }
                                }.launchIn(viewModelScope)
                            }
                            InstructionSet.DEBUGGER -> {

                            }
                            InstructionSet.DELETE_CONTACTS -> {

                            }
                            InstructionSet.OK -> {

                            }
                            InstructionSet.NULL -> {

                            }
                            InstructionSet.SUCCESS -> {

                            }
                        }
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("MainViewModel", e.toString())
        }
    }
}