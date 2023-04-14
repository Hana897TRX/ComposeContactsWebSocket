package com.hana897trx.contactsregister.utils

import com.google.gson.Gson
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import org.json.JSONObject

fun String.toSocketResponse() : SocketPayload {
    return try {
        Gson().fromJson(this, SocketPayload::class.java)
    } catch (e: Exception) {
        SocketPayload()
    }
}