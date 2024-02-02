package com.ashok.myapplication.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.RemoteMediator
import androidx.paging.map
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.repositary.DbRepository
import dagger.Component.Factory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBiblePagedUseCase(
    val dbRepo: DbRepository
) {
    fun call(): Pager<Int, BibleModelEntry> = Pager(
        PagingConfig(
            pageSize = 100,
            initialLoadSize = 100,
            enablePlaceholders = true
        )
    ) {
        dbRepo.getBible()
    }
}