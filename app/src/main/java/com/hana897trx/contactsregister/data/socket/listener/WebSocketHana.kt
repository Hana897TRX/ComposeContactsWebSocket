package com.hana897trx.contactsregister.data.socket.listener

import android.util.Log
import com.hana897trx.contactsregister.data.socket.model.DeviceModel
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.utils.ResourceState
import com.hana897trx.contactsregister.utils.toSocketResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope.coroutineContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import kotlin.coroutines.coroutineContext
import kotlin.coroutines.suspendCoroutine

class WebSocketHana : WebSocketListener() {
    private var _socketInfo: MutableStateFlow<ResourceState<SocketPayload>> =
        MutableStateFlow(ResourceState.Idle)
    val socketInfo = _socketInfo.asStateFlow()

    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("CONNECTED")
        Log.w(WSL, "CONNECTED")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {

        val socketPayload = text.toSocketResponse()
        if (socketPayload.device == DeviceModel.CLIENT) {
            webSocket.send("RECEIVED : $text")
        } else if (socketPayload.device == DeviceModel.SERVER) {
            runBlocking(IO) {
                _socketInfo.emit(ResourceState.Loading)
                _socketInfo.emit(ResourceState.Success(socketPayload))
            }
        }

        Log.w(WSL, "RECEIVED : $text")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        super.onClosing(webSocket, code, reason)
        webSocket.close(1000, null)
        Log.w(WSL, "CLOSSED")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        super.onFailure(webSocket, t, response)
        Log.w(WSL, t)
    }

    companion object {
        const val WSL = "WEB SOCKET LOGGER"
    }
}
