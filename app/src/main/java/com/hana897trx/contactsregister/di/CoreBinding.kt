package com.hana897trx.contactsregister.di

import com.hana897trx.contactsregister.data.contacts.ContactsLDS
import com.hana897trx.contactsregister.data.contacts.ContactsLDSI
import com.hana897trx.contactsregister.data.socket.SocketRDS
import com.hana897trx.contactsregister.data.socket.SocketRDSI
import com.hana897trx.contactsregister.repo.SocketRepository
import com.hana897trx.contactsregister.repo.SocketRepositoryI
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CoreBinding {
    @Binds
    abstract fun bindSocketRDS(
        imp: SocketRDSI
    ) : SocketRDS

    @Binds
    abstract fun bindRepositoryRDS(
        imp: SocketRepositoryI
    ) : SocketRepository

    @Binds
    abstract fun bindContactsLDS(
        imp: ContactsLDSI
    ) : ContactsLDS
}