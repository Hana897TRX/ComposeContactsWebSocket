package com.hana897trx.contactsregister.data.socket

import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface SocketRDS {
    suspend fun sendData(socketPayload: SocketPayload) : Flow<ResourceState<Boolean>>
    suspend fun receiveData() : Flow<ResourceState<SocketPayload>>
}