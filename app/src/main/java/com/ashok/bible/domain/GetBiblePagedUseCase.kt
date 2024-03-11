package com.ashok.bible.domain

import com.ashok.bible.domain.repository.DbRepository

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