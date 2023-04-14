package com.hana897trx.contactsregister.data.contacts

import com.hana897trx.contactsregister.data.contacts.model.ContactsModel
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.flow.Flow

interface ContactsLDS {
    suspend fun saveContacts(data: List<ContactsModel>) : ResourceState<Unit>
}