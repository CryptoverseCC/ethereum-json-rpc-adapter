package io.userfeeds.parityapi

import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST
import java.util.*

internal interface ParityGenericApi {

    interface EthModule {
        @POST("/")
        fun getBlockNumber(@Body request: Request): Single<Response<String>>

        @POST("/")
        fun getBlockByNumber(@Body request: Request): Single<Response<ParityApi.EthereumBlock>>

        @POST("/")
        fun getBlockByHash(@Body request: Request): Single<Response<ParityApi.BlockByHashResult>>

        @POST("/")
        fun getBlockTrace(@Body request: Request): Single<Response<List<ParityApi.Trace>>>

        @POST("/")
        fun getLogs(@Body request: Request): Single<Response<List<ParityApi.Log>>>

        @POST("/")
        fun getTransactionReceipt(@Body request: Request): Single<Response<ParityApi.TransactionReceiptResult>>
    }

    interface ParityModule {
        @POST("/")
        fun getBlockHeaderByNumber(@Body request: Request): Single<Response<ParityApi.BlockHeaderResult>>

        @POST("/")
        fun getEnode(@Body request: Request): Single<Response<String>>
    }

    interface SetModule {

        @POST("/")
        fun addReservedPeer(@Body request: Request): Single<Response<Unit>>
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

internal inline fun <reified T> Single<ParityGenericApi.Response<T>>.unwrap(): Single<T> =
        flatMap {
            if (it.error == null) {
                Single.just(it.result)
            } else {
                Single.error<T>(RuntimeException(it.error.message))
            }
        }

