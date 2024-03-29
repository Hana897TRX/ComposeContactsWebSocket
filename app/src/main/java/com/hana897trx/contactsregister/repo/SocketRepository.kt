package com.hana897trx.contactsregister.repo

import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    suspend fun sendData(socketPayload: SocketPayload) : Flow<ResourceState<Boolean>>
    suspend fun receiveData() : Flow<ResourceState<SocketPayload>>
}