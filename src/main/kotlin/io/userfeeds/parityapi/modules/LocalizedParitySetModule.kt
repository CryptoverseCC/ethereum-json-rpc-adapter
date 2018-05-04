package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.ParityApi
import io.userfeeds.parityapi.ParityGenericApi
import io.userfeeds.parityapi.unwrap

internal class LocalizedParitySetModule(
        private val baseUrl: String,
        private val paritySetModule: ParityGenericApi.ParitySetModule
) : ParityApi.ParitySetModule {

    override fun addReservedPeer(enode: String): Single<Boolean> {
        return paritySetModule.addReservedPeer(
                ParityGenericApi.Request(method = "parity_addReservedPeer", params = listOf(enode)), baseUrl)
                .unwrap()
    }
}

