package com.technobrain.projet42.domain.repositories;

import com.technobrain.projet42.domain.model.EventShort
import com.technobrain.projet42.domain.model.UserShort

interface ApiRepository {

    suspend fun getUserInfos(): Result<UserShort>

    suspend fun getUserEvents(): Result<List<EventShort>>
}
