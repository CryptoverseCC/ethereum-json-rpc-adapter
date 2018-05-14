package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.ParityApi
import io.userfeeds.parityapi.ParityGenericApi
import io.userfeeds.parityapi.longToHex
import io.userfeeds.parityapi.unwrap

internal class LocalizedParityModule(
        private val baseUrl: String,
        private val parityModule: ParityGenericApi.ParityModule
) : ParityApi.ParityModule {

    override fun getBlockHeaderByNumber(blockNumber: Long): Single<ParityApi.BlockHeaderResult> {
        return parityModule.getBlockHeaderByNumber(
                ParityGenericApi.Request(
                        method = "parity_getBlockHeaderByNumber",
                        params = listOf(blockNumber.longToHex())
                ), baseUrl)
                .unwrap()
    }

    override fun getEnode(): Single<String> {
        return parityModule.getEnode(
                ParityGenericApi.Request(
                        method = "parity_enode"
                ), baseUrl)
                .unwrap()
    }

    override fun getNetPeers(): Single<ParityApi.PeersInfo> {
        return parityModule.getNetPeers(
                ParityGenericApi.Request(
                        method = "parity_netPeers"
                ), baseUrl)
                .unwrap()
    }
}
