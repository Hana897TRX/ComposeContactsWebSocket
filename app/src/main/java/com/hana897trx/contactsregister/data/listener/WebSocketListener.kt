package com.hana897trx.contactsregister.data.listener

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okio.ByteString

class WebSocketListener : okhttp3.WebSocketListener() {
    override fun onOpen(webSocket: WebSocket, response: Response) {
        super.onOpen(webSocket, response)
        webSocket.send("CONNECTED")
        Log.w(WSL, "CONNECTED")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        super.onMessage(webSocket, text)
        webSocket.send("RECEIVED : $text")
        Log.w(WSL, text)
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        super.onMessage(webSocket, bytes)
        // TO BE IMPLEMENTED
        Log.d(WSL, "ON RECEIVE: ${bytes.toString()}")
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
