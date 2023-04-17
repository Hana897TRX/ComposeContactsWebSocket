package com.hana897trx.contactsregister.data.contacts.model

data class ContactsRequest(
    val groupId: String = String(),
    val contacts: List<ContactsModel> = emptyList(),
)
