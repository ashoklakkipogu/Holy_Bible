package com.ashok.myapplication.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.domain.repository.DbRepository

class GetBiblePagedUseCase(
    val dbRepo: DbRepository
) {
    /*fun call(): Pager<Int, BibleModelEntry> = Pager(
        PagingConfig(
            pageSize = 4000,
            initialLoadSize = 4000,
            enablePlaceholders = true
        )
    ) {
        dbRepo.getBible()
    }*/
}