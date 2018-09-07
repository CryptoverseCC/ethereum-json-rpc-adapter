package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.ParityApi
import io.userfeeds.parityapi.ParityGenericApi
import io.userfeeds.parityapi.longToHex
import io.userfeeds.parityapi.unwrap

internal class LocalizedEthModule(
        private val baseUrl: String,
        private val parityGenericEthModule: ParityGenericApi.EthModule
) : ParityApi.EthModule {

    override fun getBlockNumber(): Single<String> {
        return parityGenericEthModule.getBlockNumber(ParityGenericApi.Request(method = "eth_blockNumber"), baseUrl)
                .unwrap()
    }

    override fun getBlockByNumber(number: Long): Single<ParityApi.EthereumBlock> {
        return parityGenericEthModule.getBlockByNumber(
                ParityGenericApi.Request(
                        method = "eth_getBlockByNumber",
                        params = listOf(number.longToHex(), false)), baseUrl)
                .unwrap()
    }

    override fun getFullBlockByNumber(number: Long): Single<ParityApi.FullEthereumBlock> {
        return parityGenericEthModule.getFullBlockByNumber(
                ParityGenericApi.Request(
                        method = "eth_getBlockByNumber",
                        params = listOf(number.longToHex(), true)), baseUrl)
                .unwrap()
    }

    override fun getBlockByHash(hash: String): Single<ParityApi.EthereumBlock> {
        return parityGenericEthModule.getBlockByHash(ParityGenericApi.Request(
                method = "eth_getBlockByHash",
                params = listOf(hash, false)), baseUrl)
                .unwrap()
    }

    override fun getFullBlockByHash(hash: String): Single<ParityApi.FullEthereumBlock> {
        return parityGenericEthModule.getFullBlockByHash(ParityGenericApi.Request(
                method = "eth_getBlockByHash",
                params = listOf(hash, true)), baseUrl)
                .unwrap()
    }

    override fun getBlockTrace(blockNumber: Long): Single<List<ParityApi.Trace>> {
        return parityGenericEthModule.getBlockTrace(ParityGenericApi.Request(
                method = "trace_block",
                params = listOf(blockNumber.longToHex())), baseUrl)
                .unwrap()
    }

    override fun getLogs(fromBlock: Long, toBlock: Long): Single<List<ParityApi.Log>> {
        return parityGenericEthModule.getLogs(ParityGenericApi.Request(
                method = "eth_getLogs",
                params = listOf(
                        ParityApi.LogsParams(
                                fromBlock = fromBlock.longToHex(),
                                toBlock = toBlock.longToHex()
                        )
                )), baseUrl)
                .unwrap()
    }

    override fun getTransactionReceipt(transactionHash: String): Single<ParityApi.TransactionReceiptResult> {
        return parityGenericEthModule.getTransactionReceipt(
                ParityGenericApi.Request(
                        method = "eth_getTransactionReceipt",
                        params = listOf(transactionHash)
                ), baseUrl)
                .unwrap()
    }

    override fun call(to: String, data: String): Single<String> {
        return parityGenericEthModule.call(
                ParityGenericApi.Request(
                        method = "eth_call",
                        params = listOf(mapOf("to" to to, "data" to data))
                ), baseUrl)
                .unwrap()
    }
}
