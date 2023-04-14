package com.hana897trx.contactsregister.domain

import com.hana897trx.contactsregister.data.socket.model.SocketPayload
import com.hana897trx.contactsregister.repo.SocketRepository
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ReceiveSocketUC @Inject constructor(
    private val socketRepository: SocketRepository,
) {
    suspend operator fun invoke() : Flow<ResourceState<SocketPayload>> {
        return withContext(IO) {
            try {
                socketRepository.receiveData()
            } catch (e: Exception) {
                flowOf(ResourceState.Error(errorMessage = e.message ?: "Unknown error"))
            }
        }
    }
}