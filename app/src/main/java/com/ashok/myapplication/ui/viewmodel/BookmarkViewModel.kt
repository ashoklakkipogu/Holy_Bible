package com.ashok.myapplication.ui.viewmodel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ashok.myapplication.data.entity.Products
import com.ashok.myapplication.data.local.entry.BibleModelEntry
import com.ashok.myapplication.data.local.entry.FavoriteModelEntry
import com.ashok.myapplication.data.local.entry.HighlightModelEntry
import com.ashok.myapplication.data.local.entry.NoteModelEntry
import com.ashok.myapplication.data.local.model.FavModel
import com.ashok.myapplication.data.local.model.HighlightModel
import com.ashok.myapplication.data.local.model.NoteModel
import com.ashok.myapplication.data.local.repositary.DbRepository
import com.ashok.myapplication.ui.repository.ProductRepository
import com.ashok.myapplication.ui.utilities.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    val productRepository: ProductRepository,
    val dbRepo: DbRepository
) :
    ViewModel() {

    private val _noteData = mutableStateOf<List<NoteModel>>(listOf())
    val noteData: State<List<NoteModel>> = _noteData


    private val _noteDelete = MutableLiveData<Boolean>()
    val noteDelete: LiveData<Boolean> get() = _noteDelete


    private val _favData = MutableLiveData<Result<List<FavModel>>>()
    val favData: LiveData<Result<List<FavModel>>> get() = _favData

    private val _highlightData = MutableLiveData<Result<List<HighlightModel>>>()
    val highlightData: LiveData<Result<List<HighlightModel>>> get() = _highlightData


    fun getAllNoteBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            var noteData = dbRepo.getAllNotes()
            noteData = noteData?.sortedBy { sort -> sort.createdDate }?.reversed()

            withContext(Dispatchers.Main) {
                _noteData.value = noteData!!
            }

        }
    }

    fun getAllFavBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            var noteData = dbRepo.getAllFav()
            noteData = noteData?.sortedBy { sort -> sort.createdDate }?.reversed()

            withContext(Dispatchers.Main) {
                _favData.value = Result.Success(noteData!!)
            }

        }
    }

    fun getAllHighlightBibleData() {
        viewModelScope.launch(Dispatchers.IO) {
            var noteData = dbRepo.getAllHighlights()
            noteData = noteData?.sortedBy { sort -> sort.createdDate }?.reversed()

            withContext(Dispatchers.Main) {
                _highlightData.value = Result.Success(noteData!!)
            }

        }
    }

    fun deleteNoteById(obj: NoteModel) {
        Log.i("index", "...........$obj")
        viewModelScope.launch(Dispatchers.IO) {
            dbRepo.deleteNote(obj.id)
            withContext(Dispatchers.Main) {
                _noteDelete.value = true
                _noteData.value = _noteData.value.toMutableList().also { list ->
                    list.remove(obj)
                }
            }
        }
    }
}
