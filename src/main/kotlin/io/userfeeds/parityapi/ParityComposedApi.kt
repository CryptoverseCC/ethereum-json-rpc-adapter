package io.userfeeds.parityapi

import io.userfeeds.parityapi.modules.EthModuleParityApi
import io.userfeeds.parityapi.modules.ParityModuleParityApi
import io.userfeeds.parityapi.modules.ParitySetModuleParityApi
import retrofit2.Retrofit

class ParityComposedApi(retrofit: Retrofit) :
        ParityApi.ParityModule by ParityModuleParityApi(retrofit),
        ParityApi.ParitySetModule by ParitySetModuleParityApi(retrofit),
        ParityApi.EthModule by EthModuleParityApi(retrofit)