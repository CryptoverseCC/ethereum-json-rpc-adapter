package io.userfeeds.parityapi.modules

import io.reactivex.Single
import io.userfeeds.parityapi.*
import retrofit2.Retrofit

class EthModuleParityApi(retrofit: Retrofit) : ParityApi.EthModule {

    private val parityGenericApi by lazy { retrofit.create(ParityGenericApi.EthModule::class.java) }

    override fun getBlockNumber(): Single<String> {
        return parityGenericApi.getBlockNumber(ParityGenericApi.Request(method = "eth_blockNumber"))
                .unwrap()
    }

    override fun getBlockByNumber(number: Long): Single<ParityApi.EthereumBlock> {
        return parityGenericApi.getBlockByNumber(
                ParityGenericApi.Request(
                        method = "eth_getBlockByNumber",
                        params = listOf(number.longToHex(), false)))
                .unwrap()
    }

    override fun getBlockByHash(hash: String): Single<ParityApi.BlockByHashResult> {
        return parityGenericApi.getBlockByHash(ParityGenericApi.Request(
                method = "eth_getBlockByHash",
                params = listOf(hash, false)))
                .unwrap()
    }

    override fun getBlockTrace(blockNumber: Long): Single<List<ParityApi.Trace>> {
        return parityGenericApi.getBlockTrace(ParityGenericApi.Request(
                method = "trace_block",
                params = listOf(blockNumber.longToHex())))
                .unwrap()
    }

    override fun getLogs(fromBlock: Long, toBlock: Long): Single<List<ParityApi.Log>> {
        return parityGenericApi.getLogs(ParityGenericApi.Request(
                method = "eth_getLogs",
                params = listOf(
                        ParityApi.LogsParams(
                                fromBlock = fromBlock.longToHex(),
                                toBlock = toBlock.longToHex()
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
