package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.ParityApi
import io.userfeeds.parityapi.ParityGenericApi
import io.userfeeds.parityapi.hexToLong
import io.userfeeds.parityapi.unwrap

internal class LocalizedNetModule(
        private val baseUrl: String,
        private val parityGenericNetModule: ParityGenericApi.NetModule
) : ParityApi.NetModule {
    override fun getPeerCount(): Single<String> {
        return parityGenericNetModule.getNetPeers(ParityGenericApi.Request(method = "net_peerCount"), baseUrl).unwrap()
    }
}