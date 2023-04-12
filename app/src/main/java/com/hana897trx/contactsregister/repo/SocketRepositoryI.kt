package com.hana897trx.contactsregister.repo

import com.hana897trx.contactsregister.data.SocketRDS
import com.hana897trx.contactsregister.data.model.SocketResponse
import com.hana897trx.contactsregister.di.NetworkStatus
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class SocketRepositoryI @Inject constructor(
    @NetworkStatus
    private val isDeviceOnline: Boolean,
    private val socketRDS: SocketRDS,
): SocketRepository {

    override suspend fun sendData(socketPayload: SocketResponse): Flow<ResourceState<Boolean>> {
        return if(isDeviceOnline) {
            socketRDS.sendData(socketPayload)
        } else {
            flowOf(ResourceState.Error(errorMessage = "Device is offline"))
        }
    }

    override suspend fun receiveData(): Flow<ResourceState<SocketResponse>> {
        TODO("Not yet implemented")
    }
}