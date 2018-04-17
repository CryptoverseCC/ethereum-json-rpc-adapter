package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.ParityApi
import io.userfeeds.parityapi.ParityGenericApi
import io.userfeeds.parityapi.unwrap
import retrofit2.Retrofit

class ParitySetModuleParityApi(retrofit: Retrofit) : ParityApi.ParitySetModule {

    private val parityGenericApi by lazy { retrofit.create(ParityGenericApi.SetModule::class.java) }

    override fun addReservedPeer(enode: List<String>): Single<Unit> {
        return parityGenericApi.addReservedPeer(ParityGenericApi.Request(method = "parity_addReservedPeer", params = enode))
                .unwrap()
    }
}

