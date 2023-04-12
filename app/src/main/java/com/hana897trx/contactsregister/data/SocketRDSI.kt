package com.hana897trx.contactsregister.data

import com.google.gson.Gson
import com.hana897trx.contactsregister.data.listener.WebSocketHana
import com.hana897trx.contactsregister.data.model.SocketResponse
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import okhttp3.WebSocket
import javax.inject.Inject

class SocketRDSI @Inject constructor(
    private val webSocketListener: WebSocketHana,
    private val webSocket: WebSocket,
): SocketRDS {

    override suspend fun sendData(socketPayload: SocketResponse) : Flow<ResourceState<Boolean>> = flow {
        emit(ResourceState.Loading)
        try {
            webSocketListener.onMessage(webSocket, Gson().toJson(socketPayload))
            emit(ResourceState.Success(true))
        } catch (e: Exception) {
            emit(ResourceState.Error(errorMessage = e.message ?: "Unknown error"))
        }
    }.flowOn(IO)

    override suspend fun receiveData() : Flow<ResourceState<SocketResponse>> {
        // TO BE DEFINED
        return flowOf()
    }
}