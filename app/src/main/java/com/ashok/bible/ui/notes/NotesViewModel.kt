package com.ashok.bible.ui.notes

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.data.local.repositary.DbRepository
import com.ashok.bible.data.remote.network.ApiError
import com.ashok.bible.ui.base.BaseViewModel
import javax.inject.Inject

class NotesViewModel @Inject constructor(
    private val dbRepository: DbRepository
) :
    BaseViewModel() {

    val error: MutableLiveData<ApiError> by lazy { MutableLiveData<ApiError>() }
    val notesData: MutableLiveData<List<NoteModelEntry>> by lazy { MutableLiveData<List<NoteModelEntry>>() }
    val deleteNote: MutableLiveData<Unit> by lazy { MutableLiveData<Unit>() }

    fun getAllNotes(frg: NotesFragment) {
        dbRepository.getAllNotes(
            { data->
                data.observe(frg, Observer {
                    notesData.value = it
                })
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
    fun deleteNoteById(id: Int) {
        dbRepository.deleteNote(
            id,
            {
                deleteNote.value = it
            },
            {

            }
        ).also { compositeDisposable.dispose() }
    }
}
