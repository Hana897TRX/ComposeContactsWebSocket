package com.hana897trx.contactsregister.utils

import com.fasterxml.jackson.databind.ObjectMapper
import com.google.gson.Gson
import com.hana897trx.contactsregister.data.socket.model.DeviceModel
import com.hana897trx.contactsregister.data.socket.model.InstructionSet
import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import org.json.JSONObject

fun String.toSocketResponse() : SocketPayload {
    return try {
        val objectData = ObjectMapper().readTree(this)
        println(objectData)

        val device = objectData["device"].toString().removeComas().toDevice()
        val instruction = objectData["instruction"].toString().removeComas().toInstruction()
        val payload : String = objectData["payload"].toString().removeSurrounding("\"")

        return SocketPayload(device, instruction, payload)
    } catch (e: Exception) {
        println(e)
        SocketPayload(payload = this)
    }
}

fun String.removeComas(): String {
    return this.replace("\"", "")
}

fun String.toInstruction() : InstructionSet {
    return try {
        InstructionSet.valueOf(this)
    } catch (e: Exception) {
        InstructionSet.NULL
    }
}

fun String.toDevice() : DeviceModel {
    return try {
        DeviceModel.valueOf(this)
    } catch (e: Exception) {
        DeviceModel.CLIENT
    }
}