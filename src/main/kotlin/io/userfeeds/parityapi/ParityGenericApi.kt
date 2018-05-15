package io.userfeeds.parityapi

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Url
import java.util.*

internal interface ParityGenericApi {

    interface EthModule {
        @POST
        fun getBlockNumber(@Body request: Request, @Url baseUrl: String): Single<Response<String>>

        @POST
        fun getBlockByNumber(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.EthereumBlock>>

        @POST
        fun getFullBlockByNumber(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.FullEthereumBlock>>

        @POST
        fun getBlockByHash(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.EthereumBlock>>

        @POST
        fun getFullBlockByHash(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.FullEthereumBlock>>

        @POST
        fun getBlockTrace(@Body request: Request, @Url baseUrl: String): Single<Response<List<ParityApi.Trace>>>

        @POST
        fun getLogs(@Body request: Request, @Url baseUrl: String): Single<Response<List<ParityApi.Log>>>

        @POST
        fun getTransactionReceipt(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.TransactionReceiptResult>>
    }

    interface ParityModule {
        @POST
        fun getBlockHeaderByNumber(@Body request: Request, @Url baseUrl: String): Single<Response<ParityApi.BlockHeaderResult>>

        @POST
        fun getEnode(@Body request: Request, @Url baseUrl: String): Single<Response<String>>
    }

    interface ParitySetModule {

        @POST
        fun addReservedPeer(@Body request: Request, @Url baseUrl: String): Single<Response<Boolean>>
    }

    data class Request(
            val id: String = UUID.randomUUID().toString(),
            val jsonrpc: String = "2.0",
            val method: String,
            val params: List<Any> = emptyList()
    )

    data class Response<out T>(
            val result: T?,
            val error: Error?
    )

    data class Error(val message: String)
}

internal fun <T> Single<ParityGenericApi.Response<T>>.unwrap(): Single<T> =
        map {
            if (it.error == null) {
                it.result!!
            } else {
                throw RuntimeException(it.error.message)
            }
        }

