package com.ashok.bible.domain.usecase

import com.ashok.bible.domain.repository.DbRepository
import javax.inject.Inject

class DatabaseUseCase @Inject constructor(val mDbRepository: DbRepository)
