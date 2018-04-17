package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.*
import retrofit2.Retrofit

class EthParityApi(retrofit: Retrofit) : ParityApi.EthModule {

    private val parityGenericApi by lazy { retrofit.create(ParityGenericApi.EthModule::class.java) }

    override fun getBlockNumber(): Single<Long> {
        return parityGenericApi.getBlockNumber(ParityGenericApi.Request(method = "eth_blockNumber"))
                .unwrap()
                .map { it.hexToLong() }
    }

    override fun getBlockByNumber(number: Long): Single<ParityApi.EthereumBlock> {
        return parityGenericApi.getBlockByNumber(
                ParityGenericApi.Request(
                        method = "eth_getBlockByNumber",
                        params = listOf("0x${number.toString(16)}", false)))
                .unwrap()
    }

    override fun getBlockByHash(hash: String): Single<ParityApi.BlockByHashResult> {
        return parityGenericApi.getBlockByHash(ParityGenericApi.Request(
                method = "eth_getBlockByHash",
                params = listOf(hash, false)))
                .unwrap()
    }

    override fun getBlockTrace(block: Block): Single<List<ParityApi.Trace>> {
        return parityGenericApi.getBlockTrace(ParityGenericApi.Request(
                method = "trace_block",
                params = listOf(block.numberHex)))
                .unwrap()
    }

    override fun getLogs(block: Block): Single<List<ParityApi.Log>> {
        return parityGenericApi.getLogs(ParityGenericApi.Request(
                method = "eth_getLogs",
                params = listOf(
                        ParityApi.LogsParams(
                                fromBlock = block.numberHex,
                                toBlock = block.numberHex
                        )
                )))
                .unwrap()
    }

    override fun getTransactionReceipt(transactionHash: String): Single<ParityApi.TransactionReceiptResult> {
        return parityGenericApi.getTransactionReceipt(
                ParityGenericApi.Request(
                        method = "eth_getTransactionReceipt",
                        params = listOf(transactionHash)))
                .unwrap()
    }
}


