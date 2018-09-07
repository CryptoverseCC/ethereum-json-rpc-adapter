package io.userfeeds.parityapi

import org.junit.Test

class ParityApiFactoryImplTest {

    @Test
    fun shouldCreateInstanceOfParityApi() {
        ParityApiFactoryImpl().createComposedApi().getPeerCount().blockingGet().let { print(it) }
    }

    @Test
    fun getKittiesContractName() {
        ParityApiFactoryImpl().createComposedApi()
                .call("0x06012c8cf97bead5deae237070f9587f8e7a266d", "0x06fdde03")
                .blockingGet()
                .let { print(it.getStringAtIndex(0)) }
    }
}
