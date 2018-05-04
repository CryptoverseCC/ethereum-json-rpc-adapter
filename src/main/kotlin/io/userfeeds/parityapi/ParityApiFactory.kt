package io.userfeeds.parityapi

import io.userfeeds.parityapi.modules.LocalizedEthModule
import io.userfeeds.parityapi.modules.LocalizedParityModule
import io.userfeeds.parityapi.modules.LocalizedParitySetModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface ParityApiFactory {
    fun createEthModuleApi(parityAddress: String): ParityApi.EthModule

    fun createParitySetModuleApi(parityAddress: String): ParityApi.ParitySetModule

    fun createParityModuleApi(parityAddress: String): ParityApi.ParityModule

    fun createComposedApi(parityAddress: String): ParityComposedApi
}

class ParityApiFactoryImpl : ParityApiFactory {

    private val retrofit: Retrofit

    constructor() {
        retrofit = createDefaultBuilder()
                .build()
    }

    constructor(customizeRetrofit: Retrofit.Builder.() -> Retrofit.Builder) {
        retrofit = createDefaultBuilder()
                .customizeRetrofit()
                .build()
    }

    private fun retrofitParityModule() = retrofit.create(ParityGenericApi.ParityModule::class.java)

    private fun retrofitEthModule() = retrofit.create(ParityGenericApi.EthModule::class.java)

    private fun retrofitParitySetModule() = retrofit.create(ParityGenericApi.ParitySetModule::class.java)

    private fun createDefaultBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
    }

    override fun createEthModuleApi(parityAddress: String): ParityApi.EthModule =
            LocalizedEthModule(parityAddress, retrofitEthModule())

    override fun createParitySetModuleApi(parityAddress: String): ParityApi.ParitySetModule {
        return LocalizedParitySetModule(parityAddress, retrofitParitySetModule())
    }

    override fun createParityModuleApi(parityAddress: String): ParityApi.ParityModule {
        return LocalizedParityModule(parityAddress, retrofitParityModule())
    }

    override fun createComposedApi(parityAddress: String): ParityComposedApi {
        return object : ParityComposedApi,
                ParityApi.EthModule by createEthModuleApi(parityAddress),
                ParityApi.ParityModule by createParityModuleApi(parityAddress),
                ParityApi.ParitySetModule by createParitySetModuleApi(parityAddress) {}
    }
}