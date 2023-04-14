package com.hana897trx.contactsregister.data.socket

import com.google.gson.Gson
import com.hana897trx.contactsregister.data.socket.listener.WebSocketHana
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.onEach
import okhttp3.WebSocket
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class SocketRDSI @Inject constructor(
    private val webSocketListener: WebSocketHana,
    private val webSocket: WebSocket,
): SocketRDS {

    override suspend fun sendData(socketPayload: SocketPayload) : Flow<ResourceState<Boolean>> = flow {
        emit(ResourceState.Loading)
        try {
            webSocketListener.onMessage(webSocket, Gson().toJson(socketPayload))
            emit(ResourceState.Success(true))
        } catch (e: Exception) {
            emit(ResourceState.Error(errorMessage = e.message ?: "Unknown error"))
        }
    }.flowOn(IO)

    override suspend fun receiveData() : Flow<ResourceState<SocketPayload>> {
        return webSocketListener.socketInfo
    }
}