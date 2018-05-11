package io.userfeeds.parityapi

interface ParityComposedApi :
        ParityApi.ParityModule,
        ParityApi.EthModule,
        ParityApi.ParitySetModule