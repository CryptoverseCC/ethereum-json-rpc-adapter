package io.userfeeds.parityapi

import org.junit.Test

class ParityApiFactoryImplTest {

    @Test
    fun shouldCreateInstanceOfParityApi() {
        ParityApiFactoryImpl().createComposedApi().getPeerCount().blockingGet().let { print(it) }
    }
}