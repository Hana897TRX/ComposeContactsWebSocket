package com.hana897trx.contactsregister.data.model

data class SocketResponse (
    val instruction: String,
    val payload: String,
)

data class SocketPayload(
    val instruction: InstructionSet,
    val payload: String,
)

enum class InstructionSet {
    SEND_MESSAGE,
    REGISTER_CONTACTS,
    DEBUGGER,
    DELETE_CONTACTS,
}