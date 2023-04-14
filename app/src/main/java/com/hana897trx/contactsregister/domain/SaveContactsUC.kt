package com.hana897trx.contactsregister.domain

import com.hana897trx.contactsregister.data.contacts.ContactsLDS
import com.hana897trx.contactsregister.data.contacts.model.ContactsModel
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SaveContactsUC @Inject constructor(
    private val contactsLDS: ContactsLDS
) {
    operator fun invoke(data: List<ContactsModel>) : Flow<ResourceState<Unit>> = flow {
        emit(ResourceState.Loading)
        try {
            emit(contactsLDS.saveContacts(data))
        } catch (e: Exception) {
            emit(ResourceState.Error(errorMessage = e.message.orEmpty()))
        }
    }.flowOn(IO)
}