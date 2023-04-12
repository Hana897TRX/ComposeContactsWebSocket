package com.hana897trx.contactsregister.domain

import com.hana897trx.contactsregister.data.model.SocketResponse
import com.hana897trx.contactsregister.repo.SocketRepository
import com.hana897trx.contactsregister.utils.ResourceState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SendSocketUC @Inject constructor(
    private val socketRepository: SocketRepository,
){
    suspend operator fun invoke(socketPayload: SocketResponse) : Flow<ResourceState<Boolean>>  {
        return withContext(IO) {
            try {
                socketRepository.sendData(socketPayload)
            } catch (e: Exception) {
                flowOf(ResourceState.Error(errorMessage = e.message ?: "Unknown error"))
            }
        }
    }
}