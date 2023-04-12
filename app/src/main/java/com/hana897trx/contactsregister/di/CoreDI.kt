package com.hana897trx.contactsregister.di

import android.content.Context
import com.hana897trx.contactsregister.data.listener.WebSocketHana
import com.hana897trx.contactsregister.utils.NetworkConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CoreDI {

    @Provides
    @Singleton
    fun provideRequest() : Request =
        Request
            .Builder()
            .url("wss://socketsbay.com/wss/v2/1/demo/")
            .build()

    @Provides
    fun provideClient() : OkHttpClient =
        OkHttpClient()


    @Provides
    @Singleton
    fun provideWebSocketListener() : WebSocketHana = WebSocketHana()

    @Provides
    @Singleton
    fun provideWebSocket(client: OkHttpClient, request: Request, socketRDSI: WebSocketHana) : WebSocket =
       client.newWebSocket(request, socketRDSI)

    @Provides
    @Singleton
    @NetworkStatus
    fun isConnected(@ApplicationContext ctx: Context) : Boolean =
        NetworkConnection.isOnline(ctx)
}