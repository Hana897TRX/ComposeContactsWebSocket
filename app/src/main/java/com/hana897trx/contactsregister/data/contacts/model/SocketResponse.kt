package com.hana897trx.contactsregister.data.contacts.model

import com.hana897trx.contactsregister.data.socket.model.InstructionSet

data class SocketResponse(
    val id: String = String(),
    val status: InstructionSet = InstructionSet.NULL,
)