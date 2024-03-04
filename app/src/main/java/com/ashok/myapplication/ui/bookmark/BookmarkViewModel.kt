package com.ashok.myapplication.ui.bookmark

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.domain.repository.DbRepository
import com.ashok.myapplication.ui.dashboard.DashboardUiEvent
import com.ashok.myapplication.ui.dashboard.DashboardUiState
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val dbRepo: DbRepository
) :
    ViewModel() {

    var state by mutableStateOf(BookmarkUIState())
    fun onEvent(event: BookmarkUIEvent) {
        when (event) {
            BookmarkUIEvent.GetAllNoteBibleData -> {
                getAllNoteBibleData()
            }

            is BookmarkUIEvent.DeleteNoteById -> {
                deleteNoteById(event.obj)
            }

            is BookmarkUIEvent.DeleteFavById -> {
                deleteFavById(event.obj)
            }

            is BookmarkUIEvent.DeleteHighlightById -> {
                deleteHighlightById(event.obj)
            }

            BookmarkUIEvent.GetAllFavBibleData -> {
                getAllFavBibleData()
            }

            BookmarkUIEvent.GetAllHighlightBibleData -> {
                getAllHighlightBibleData()
            }
        }
    }

    fun getAllNoteBibleData() {
        viewModelScope.launch {
            dbRepo.getAllNotes().collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        state = state.copy(noteData = result.data)
                    }
                }
            }
        }
    }

    fun getAllFavBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.getAllFav().collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        state = state.copy(favData = result.data)
                    }
                }
            }

        }
    }

    fun getAllHighlightBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.getAllHighlights().collect { result ->
                when (result) {
                    is Result.Error -> Unit
                    is Result.Loading -> Unit
                    is Result.Success -> {
                        state = state.copy(highlightData = result.data)
                    }
                }
            }

        }
    }

    fun deleteNoteById(obj: NoteModel) {
        Log.i("index", "...........$obj")
        viewModelScope.launch {
            dbRepo.deleteNote(obj.id)
            val noteData = state.noteData?.toMutableList().also { list ->
                list?.remove(obj)
            }
            state = state.copy(noteDelete = true, noteData = noteData)
        }
    }

    fun deleteHighlightById(obj: HighlightModel) {
        viewModelScope.launch {
            dbRepo.deleteHighlight(obj.id)
            val highlightData = state.highlightData?.toMutableList().also { list ->
                list?.remove(obj)
            }
            state = state.copy(highlightDelete = true, highlightData = highlightData)
        }
    }

    fun deleteFavById(obj: FavModel) {
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteFavorite(obj.id)
            val favData = state.favData?.toMutableList().also { list ->
                list?.remove(obj)
            }
            state = state.copy(favDelete = true, favData = favData)
        }
    }


}
