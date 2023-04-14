package com.hana897trx.contactsregister.data.socket.model

import com.google.gson.annotations.SerializedName

data class SocketPayload(
    @SerializedName("device")
    val device: DeviceModel = DeviceModel.CLIENT,
    @SerializedName("instruction")
    val instruction: InstructionSet = InstructionSet.NULL,
    @SerializedName("payload")
    val payload: String = String(),
)