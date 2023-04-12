package com.hana897trx.contactsregister.di

import com.hana897trx.contactsregister.data.SocketRDS
import com.hana897trx.contactsregister.data.SocketRDSI
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
}