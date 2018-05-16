package io.userfeeds.parityapi

import io.reactivex.Single

interface ParityApi {
    interface EthModule {
        fun getBlockNumber(): Single<String>
        fun getBlockByNumber(number: Long): Single<EthereumBlock>
        fun getFullBlockByNumber(number: Long): Single<FullEthereumBlock>
        fun getBlockByHash(hash: String): Single<EthereumBlock>
        fun getFullBlockByHash(hash: String): Single<FullEthereumBlock>
        fun getBlockTrace(blockNumber: Long): Single<List<Trace>>
        fun getLogs(fromBlock: Long, toBlock: Long): Single<List<Log>>
        fun getTransactionReceipt(transactionHash: String): Single<TransactionReceiptResult>
    }

    interface ParityModule {
        fun getBlockHeaderByNumber(blockNumber: Long): Single<BlockHeaderResult>
        fun getEnode(): Single<String>
        fun getNetPeers(): Single<PeersInfo>
    }

    interface ParitySetModule {
        fun addReservedPeer(enode: String): Single<Boolean>
    }

    data class TransactionReceiptResult(
            val status: String,
            val logs: List<Log>
    )

    data class LogsParams(
            val fromBlock: String,
            val toBlock: String
    )

    data class Log(
            val address: String,
            val blockHash: String,
            val data: String,
            val topics: List<String>,
            val transactionHash: String
    )

    data class EthereumBlock(
            val number: String,
            val hash: String,
            val parentHash: String,
            val timestamp: String,
            val transactions: List<String>
    )

    data class FullEthereumBlock(
            val number: String,
            val hash: String,
            val parentHash: String,
            val timestamp: String,
            val transactions: List<Transaction>
    )

    data class Transaction(
            val from: String,
            val hash: String,
            val to: String?,
            val value: String
    )

    data class Trace(
            val action: TraceAction,
            val blockHash: String,
            val result: TraceResult?,
            val traceAddress: List<Long>,
            val transactionHash: String?,
            val type: String
    )

    data class TraceAction(
            val from: String?,
            val to: String?,
            val author: String?,
            val value: String
    )

    data class TraceResult(
            val output: String
    )

    data class BlockHeaderResult(
            val timestamp: String
    )

    data class PeersInfo(
            val active: Int,
            val connected: Int,
            val max: Int,
            val peers: List<PeerInfo>)

    data class PeerInfo(
            val id: String,
            val name: String,
            val network: NetworkInfo)

    data class NetworkInfo(
            val localAddress: String,
            val remoteAddress: String)
}