package com.ashok.bible.ui.dashboard

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.domain.repository.DbRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ashok.bible.domain.RequestState
import com.ashok.bible.domain.usecase.BibleUseCase
import com.ashok.bible.domain.usecase.DatabaseUseCase
import com.ashok.bible.ui.utilities.BibleUtils
import com.ashok.bible.ui.utilities.SharedPrefUtils
import com.ashok.bible.ui.utilities.TtsManager

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val useCase: BibleUseCase,
    private val dbUseCase: DatabaseUseCase,
    val pref: SharedPreferences,
    val application: Application
) :
    ViewModel() {

    var state by mutableStateOf(DashboardUiState())
    private var ttsManager: TtsManager? = null

    private var dbRepo:DbRepository = dbUseCase.mDbRepository

    init {
        getBibleActionForLeftRight()
        getBibleIndex()
        ttsManager = TtsManager(application)
        getSelectedLanguge()
        getStatusImages()
    }

    private fun getSelectedLanguge() {
        val lang = SharedPrefUtils.getLanguage(pref)
        lang?.let { state = state.copy(selectedLanguage = it) }
    }

    fun onEvent(event: DashboardUiEvent) {
        when (event) {
            is DashboardUiEvent.SetOrResetBibleScrollPos -> {
                setOrResetBibleScrollPos(event.pos)
            }

            is DashboardUiEvent.HighlightInsertOrDelete -> {
                val bibleVerse = event.bibleVerse
                if (bibleVerse.selectedBackground == "") {
                    highlightDeleteByBibleId(bibleVerse.bibleLangIndex)
                } else {
                    val obj = HighlightModelEntry()
                    obj.createdDate = BibleUtils.getCurrentTime()
                    obj.langauge = bibleVerse.langauge
                    obj.bibleLangIndex = bibleVerse.bibleLangIndex
                    obj.bibleId = bibleVerse.id
                    obj.colorCode = bibleVerse.selectedBackground
                    highlightInsert(obj)
                }
            }

            is DashboardUiEvent.BookmarkInsertOrDelete -> {
                val bibleVerse = event.bibleVerse
                if (bibleVerse.isBookMark) {
                    val obj = FavoriteModelEntry()
                    obj.createdDate = BibleUtils.getCurrentTime()
                    obj.langauge = bibleVerse.langauge
                    obj.bibleLangIndex = bibleVerse.bibleLangIndex
                    obj.bibleId = bibleVerse.id
                    bookmarkInsert(obj)
                } else {
                    deleteFavById(bibleVerse.bibleLangIndex)
                }
            }

            is DashboardUiEvent.NoteInsert -> {
                val bibleVerse = event.bibleVerse
                val noteObj = NoteModelEntry()
                noteObj.createdDate = BibleUtils.getCurrentTime()
                noteObj.noteName = event.title
                noteObj.langauge = bibleVerse.langauge
                noteObj.bibleLangIndex = bibleVerse.bibleLangIndex
                noteObj.bibleId = bibleVerse.id
                noteInsert(noteObj)
            }

            is DashboardUiEvent.ExpandedState -> {
                state = state.copy(expandedState = event.expandedState)
            }

            is DashboardUiEvent.GetBibleActionForLeftRight -> {
                getBibleActionForLeftRight(
                    event.clickAction,
                    event.bookId,
                    event.chapterId,
                    event.isScrollTop
                )
            }

            is DashboardUiEvent.TextSpeechPlay -> {
                ttsManager?.say(event.playingText)
            }

            DashboardUiEvent.TextSpeechStop -> {
                ttsManager?.stop()
            }
        }
    }

    /*fun getBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            val bibleData = dbRepo.getBible()
            val highlightsData = dbRepo.getAllHighlights()
            val favData = dbRepo.getAllFav()
            val noteData = dbRepo.getAllNotes()



            bibleData?.forEach {
                it.apply {
                    highlightsData?.forEach { highlightsData ->
                        if (id == highlightsData.bibleId) {
                            selectedBackground = highlightsData.colorCode
                        }
                    }
                    favData?.forEach { fav ->
                        if (id == fav.bibleId) {
                            isBookMark = true
                        }
                    }
                    noteData?.forEach { note ->
                        if (id == note.bibleId) {
                            isNote = true
                        }
                    }
                }
            }

            //Log.i("data", "data1........$bibleData")
            withContext(Dispatchers.Main) {
                *//* allModel.bible = bibleData!!
                 allModel.notes = noteData!!*//*
                _bibleData.value = Result.Success(bibleData!!)
            }

        }
    }
*/
    //val biblePagingSource: Pager<Int, BibleModelEntry> = useCase.call()
    /*.cachedIn(viewModelScope)*/

    /*fun getBibleListByBookIdAndChapterId(
        bookId: Int = 1,
        chapterId: Int = 1,
    ) {
        val language = SharedPrefUtils.getLanguage(pref)!!
        bibleListData = dbRepo.getBibleListByBookIdAndChapterId(bookId, chapterId, language)!!
    }*/
    fun getBibleActionForLeftRight(
        clickAction: String = "",
        bookId: Int = 1,
        chapterId: Int = 1,
        isScrollTop: Int = 0
    ) = viewModelScope.launch {
        dbUseCase.mDbRepository.getBiblePageActionLeftOrRight(clickAction, bookId, chapterId)
            .collect { result ->
                state = state.copy(bibleData = result)
            }
        setOrResetBibleScrollPos(isScrollTop)
    }


    /*fun getBibleScrollPosition(
        clickAction: String,
        bookId: Int,
        chapterId: Int,
        verseId: Int
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            var data = dbRepo.getBibleScrollPosition(bookId, chapterId, verseId)
            if (data == null) {
                println("$bookId, $chapterId")
                if (clickAction == "LEFT") {
                    val lastBiblePosItem = dbRepo.getBibleScrollLastPosition(bookId - 1)
                    if (lastBiblePosItem != null)
                        data =
                            dbRepo.getBibleScrollPosition(
                                lastBiblePosItem.Book,
                                lastBiblePosItem.Chapter,
                                1
                            )
                } else if (clickAction == "RIGHT") {
                    data = dbRepo.getBibleScrollPosition(bookId + 1, 1, 1)
                }
            }

            withContext(Dispatchers.Main) {
                data?.let {
                    //Log.i(
                        "data....",
                        "data............." + (it.bibleLangIndex.split("-")[1].toInt() - 1)
                    )
                    bibleScrollPos = it.bibleLangIndex.split("-")[1].toInt() - 1
                }
            }


        }
    }*/


    fun noteInsert(model: NoteModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteNotesByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllNotes(arrayListOf(model))
        }
    }

    fun bookmarkInsert(model: FavoriteModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteFavoriteByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllFav(arrayListOf(model))
        }
    }

    fun highlightInsert(model: HighlightModelEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteHighlightByBibleLangIndex(model.bibleLangIndex)
            dbRepo.insertAllHighlight(arrayListOf(model))
        }
    }

    fun highlightDeleteByBibleId(bibleLangIndex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteHighlightByBibleLangIndex(bibleLangIndex)
        }
    }

    fun deleteFavById(bibleLangIndex: String) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteFavoriteByBibleLangIndex(bibleLangIndex)
        }
    }

    private fun setOrResetBibleScrollPos(pos: Int) {
        state = state.copy(bibleScrollPos = pos)
    }


    init {
        getBibleIndex()
    }

    fun getBibleIndex() = viewModelScope.launch {
        dbRepo.getBibleIndex().collect { result ->
            state = state.copy(bibleIndexData = result)
        }
    }

    private fun getStatusImages() {
        viewModelScope.launch {
            useCase.getStatusEmptyImages().collect { result ->
                when (result) {
                    is RequestState.Success -> {
                        state = state.copy(
                            statusImages = result.data
                        )
                    }

                    else -> {}
                }
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        ttsManager?.stop()
        ttsManager?.shutDown()
    }
}
