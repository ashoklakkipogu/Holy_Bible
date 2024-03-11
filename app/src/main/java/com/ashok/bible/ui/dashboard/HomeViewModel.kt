package com.ashok.bible.ui.dashboard

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.domain.repository.DbRepository
import com.ashok.bible.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.ashok.bible.data.local.entity.StatusEmptyImagesModel
import com.ashok.bible.domain.repository.BibleRepository
import com.ashok.bible.ui.utilities.BibleUtils
import com.ashok.bible.ui.utilities.SharedPrefUtils
import com.ashok.bible.ui.utilities.TtsManager

@HiltViewModel
class HomeViewModel @Inject constructor(
    val bibleRepository: BibleRepository,
    val dbRepo: DbRepository,
    val pref: SharedPreferences,
    val application: Application
) :
    ViewModel() {

    var state by mutableStateOf(DashboardUiState())
    private var ttsManager: TtsManager? = null


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
        dbRepo.getBiblePageActionLeftOrRight(clickAction, bookId, chapterId).collect { result ->
            when (result) {
                is Result.Error -> {
                    state = state.copy(isLoading = false, error = result.apiError?.getErrorMessage())
                }

                is Result.Loading -> {
                    state = state.copy(isLoading = true)
                }

                is Result.Success -> {
                    if (result.data?.isNotEmpty() == true)
                        state = state.copy(isLoading = false, bibleData = result.data)
                }
            }
        }

        /*val language = SharedPrefUtils.getLanguage(pref)!!
        val data = dbRepo.getBibleListByBookIdAndChapterId(bookId, chapterId, language)
        if (data.isNullOrEmpty()) {
            if (clickAction == "LEFT" && bookId != 1 && chapterId != 1) {
                val lastPos = dbRepo.getBibleScrollLastPosition(bookId - 1)
                if (lastPos != null)
                    bibleListData = dbRepo.getBibleListByBookIdAndChapterId(
                        lastPos.Book,
                        lastPos.Chapter,
                        language
                    )!!
            } else if (clickAction == "RIGHT" && bookId != 66 && chapterId != 22) {
                bibleListData = dbRepo.getBibleListByBookIdAndChapterId(bookId + 1, 1, language)!!
            }
        } else {
            bibleListData = data
        }*/
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
            when (result) {
                is Result.Error -> Unit
                is Result.Loading -> Unit
                is Result.Success -> {
                    state = state.copy(bibleIndexData = result.data)
                }
            }
        }
    }

    /*fun textToSpeech(context: Context, playingText: String?, onStop: () -> Unit) {
        textToSpeech = TextToSpeech(
            context
        ) {
            if (it == TextToSpeech.SUCCESS) {
                textToSpeech?.let { txtToSpeech ->
                    //txtToSpeech.language = Locale.US
                    txtToSpeech.language = Locale("te_IN")
                    txtToSpeech.setSpeechRate(1.0f)
                    if (playingText != null) {
                        txtToSpeech.speak(
                            playingText,
                            TextToSpeech.QUEUE_FLUSH,
                            null,
                            null
                        )
                    }
                }
            }
            if (it == TextToSpeech.STOPPED) {
                onStop.invoke()
            }
        }
    }

    fun stopPlaying() {
        textToSpeech?.stop()
    }*/

    private fun getStatusImages() {
        viewModelScope.launch {
            bibleRepository.getStatusEmptyImages().collect { result ->
                when (result) {
                    is Result.Error -> {
                        state = state.copy(
                            statusImagesError = result.apiError?.getErrorMessage(),
                            isLoadingStatus = false
                        )
                    }

                    is Result.Loading -> {
                        state = state.copy(isLoadingStatus = true)
                    }

                    is Result.Success -> {
                        val data = result.data
                        state = if (data != null) {
                            val list = ArrayList<StatusEmptyImagesModel>()
                            for ((_, value) in data) {
                                list.add(value)
                            }
                            val newList = list.sortedBy { sort ->
                                sort.createdDate
                            }.reversed()
                            state.copy(
                                statusImages = newList,
                                isLoadingStatus = false
                            )
                        } else {
                            state.copy(
                                isLoadingStatus = false
                            )
                        }
                    }
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
