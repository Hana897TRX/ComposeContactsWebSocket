package com.hana897trx.contactsregister.data.contacts

import android.content.ContentProviderOperation
import android.content.Context
import android.content.OperationApplicationException
import android.os.RemoteException
import android.provider.ContactsContract
import com.hana897trx.contactsregister.data.contacts.model.ContactsModel
import com.hana897trx.contactsregister.utils.ResourceState
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ContactsLDSI @Inject constructor(
    @ApplicationContext private val ctx: Context
) : ContactsLDS {
    override suspend fun saveContacts(data: List<ContactsModel>) : ResourceState<Unit> {
        data.map { contact ->

            // Prepare Operation
            val contentProvideOperation = ArrayList<ContentProviderOperation>()
            contentProvideOperation.add(
                ContentProviderOperation.newInsert(
                    ContactsContract.RawContacts.CONTENT_URI
                )
                    .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                    .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null).build()
            )

            // Name
            contentProvideOperation.add(
                ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME,
                        contact.name,
                    ).build()
            )

            // Number
            contentProvideOperation.add(
                ContentProviderOperation
                    .newInsert(ContactsContract.Data.CONTENT_URI)
                    .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                    .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.NUMBER,
                        contact.number,
                    )
                    .withValue(
                        ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_WORK
                    )
                    .build()
            )

            // Apply operation
            try {
                ctx.contentResolver.applyBatch(ContactsContract.AUTHORITY, contentProvideOperation)
            } catch (e: OperationApplicationException) {
                e.printStackTrace()
                return ResourceState.Error(errorMessage = e.message.orEmpty())
            } catch (e: RemoteException) {
                e.printStackTrace()
                return ResourceState.Error(errorMessage = e.message.orEmpty())
            }
        }
        return ResourceState.Success(Unit)
    }
}