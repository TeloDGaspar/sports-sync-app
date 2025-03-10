package com.telogaspar.sports_sync_app.feature.sportsevent.data.repository

import com.telogaspar.sports_sync_app.feature.sportsevent.data.mapper.EventMapper
import com.telogaspar.sports_sync_app.feature.sportsevent.data.remote.SportsEventListRemoteDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Sports
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.entity.Type
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.exception.SportsNotFoundException
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.FavoriteListLocalDataSource
import com.telogaspar.sports_sync_app.feature.sportsevent.domain.repository.SportListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class SportListRepositoryImpl(
    private val remoteDataSource: SportsEventListRemoteDataSource,
    private val mapper: EventMapper,
    private val localDataSource: FavoriteListLocalDataSource
) : SportListRepository {
    override fun fetchSportList(): Flow<List<Sports>> {
        return flow {
            val sportsResponse = remoteDataSource.fetchSportsEventList()
            if (sportsResponse.isEmpty()) {
                throw SportsNotFoundException()
            }
            emit(mapper.map(sportsResponse))
        }
    }

    override suspend fun saveFavorite(eventId: String, type: Type) {
        localDataSource.saveFavorite(eventId, type)
    }

    override suspend fun removeFavorite(eventId: String, type: Type) {
        localDataSource.removeFavorite(eventId, type)
    }

    override fun getFavorite(type: Type): Flow<Set<String>> {
        return localDataSource.getFavorite(type)
    }
}