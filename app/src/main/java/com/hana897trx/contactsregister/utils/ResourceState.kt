package com.hana897trx.contactsregister.utils

sealed class ResourceState<out T> {
    data class Error(val errorCode: Int = -1, val errorMessage: String = String()) : ResourceState<Nothing>()
    data class Success<out T>(val data: T) :  ResourceState<T>()
    object Loading: ResourceState<Nothing>()
    object Idle: ResourceState<Nothing>()
}