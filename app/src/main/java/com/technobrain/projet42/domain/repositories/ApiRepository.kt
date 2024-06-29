package com.technobrain.projet42.domain.repositories;

import com.technobrain.projet42.domain.model.UserShort

interface ApiRepository {

    suspend fun getUserInfos(): Result<UserShort>

}
