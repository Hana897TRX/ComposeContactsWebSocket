package com.hana897trx.contactsregister.repo

import com.hana897trx.contactsregister.data.model.SocketResponse
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface SocketRepository {
    suspend fun sendData(socketPayload: SocketResponse) : Flow<ResourceState<Boolean>>
    suspend fun receiveData() : Flow<ResourceState<SocketResponse>>
}