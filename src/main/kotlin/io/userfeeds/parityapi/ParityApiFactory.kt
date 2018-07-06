package io.userfeeds.parityapi

import io.userfeeds.parityapi.modules.LocalizedEthModule
import io.userfeeds.parityapi.modules.LocalizedNetModule
import io.userfeeds.parityapi.modules.LocalizedParityModule
import io.userfeeds.parityapi.modules.LocalizedParitySetModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

interface ParityApiFactory {
    fun createEthModuleApi(parityAddress: String? = null): ParityApi.EthModule

    fun createParitySetModuleApi(parityAddress: String? = null): ParityApi.ParitySetModule

    fun createParityModuleApi(parityAddress: String? = null): ParityApi.ParityModule

    fun createComposedApi(parityAddress: String? = null): ParityComposedApi

    fun createNetModuleApi(parityAddress: String? = null): ParityApi.NetModule
}

class ParityApiFactoryImpl : ParityApiFactory {

    private val retrofit: Retrofit
    private val parityModule: ParityGenericApi.ParityModule
    private val parityEthModule: ParityGenericApi.EthModule
    private val paritySetModule: ParityGenericApi.ParitySetModule
    private val parityNetModule: ParityGenericApi.NetModule

    constructor() {
        retrofit = createDefaultBuilder()
                .build()
        parityEthModule = retrofit.create(ParityGenericApi.EthModule::class.java)
        parityModule = retrofit.create(ParityGenericApi.ParityModule::class.java)
        paritySetModule = retrofit.create(ParityGenericApi.ParitySetModule::class.java)
        parityNetModule = retrofit.create(ParityGenericApi.NetModule::class.java)
    }

    constructor(customizeRetrofit: Retrofit.Builder.() -> Retrofit.Builder) {
        retrofit = createDefaultBuilder()
                .customizeRetrofit()
                .build()
        parityEthModule = retrofit.create(ParityGenericApi.EthModule::class.java)
        parityModule = retrofit.create(ParityGenericApi.ParityModule::class.java)
        paritySetModule = retrofit.create(ParityGenericApi.ParitySetModule::class.java)
        parityNetModule = retrofit.create(ParityGenericApi.NetModule::class.java)
    }

    private fun createDefaultBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
                .baseUrl("http://localhost:8545")
                .client(OkHttpClient())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create())
    }

    override fun createEthModuleApi(parityAddress: String?): ParityApi.EthModule =
            LocalizedEthModule(parityAddress ?: retrofit.baseUrl().toString(), parityEthModule)

    override fun createParitySetModuleApi(parityAddress: String?): ParityApi.ParitySetModule {
        return LocalizedParitySetModule(parityAddress ?: retrofit.baseUrl().toString(), paritySetModule)
    }

    override fun createParityModuleApi(parityAddress: String?): ParityApi.ParityModule {
        return LocalizedParityModule(parityAddress ?: retrofit.baseUrl().toString(), parityModule)
    }

    override fun createNetModuleApi(parityAddress: String?): ParityApi.NetModule {
        return LocalizedNetModule(parityAddress ?: retrofit.baseUrl().toString(), parityNetModule)
    }

    override fun createComposedApi(parityAddress: String?): ParityComposedApi {
        return object : ParityComposedApi,
                ParityApi.EthModule by createEthModuleApi(parityAddress),
                ParityApi.ParityModule by createParityModuleApi(parityAddress),
                ParityApi.ParitySetModule by createParitySetModuleApi(parityAddress),
                ParityApi.NetModule by createNetModuleApi(parityAddress) {}
    }
}