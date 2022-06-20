package com.ashok.bible.ui.notes

import android.app.AlertDialog
import android.view.View
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.data.local.entry.FavoriteModelEntry
import com.ashok.bible.data.local.entry.HighlightModelEntry
import com.ashok.bible.data.local.entry.NoteModelEntry
import com.ashok.bible.databinding.FragmentNoteBinding
import com.ashok.bible.ui.MainActivity
import com.ashok.bible.ui.adapter.HighlightsAdapter
import com.ashok.bible.ui.adapter.NotesAdapter
import com.ashok.bible.utils.Utils
import com.facebook.shimmer.ShimmerFrameLayout
import com.google.android.gms.ads.AdRequest
import com.lakki.kotlinlearning.view.base.BaseFragment
import javax.inject.Inject

class NotesFragment : BaseFragment<NotesViewModel, FragmentNoteBinding>() {
    var modelList: List<NoteModelEntry> = ArrayList()
    lateinit var notesAdapter: NotesAdapter
    private lateinit var animation: ShimmerFrameLayout


    @Inject
    lateinit var adRequest: AdRequest

    override fun getLayoutRes(): Int {
        return R.layout.fragment_note
    }

    override fun init() {
        binding.adView.loadAd(adRequest)
        animation = binding.loadingAnimation
        Utils.verticalRecyclerView(binding.recyclerView, activity)
        notesAdapter = NotesAdapter(this, modelList);
        binding.recyclerView.adapter = notesAdapter

        with(viewModel){
            animation.startShimmer()
            animation.visibility = View.VISIBLE
            getAllNotes(this@NotesFragment)
            notesData.observe(this@NotesFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                if (it != null) {
                    val data = it.sortedBy {sort-> sort.createdDate }.reversed()
                    notesAdapter.updateData(data)
                    if (data.isEmpty()) {
                        binding.placeholderLabel.visibility = View.VISIBLE
                    } else {
                        binding.placeholderLabel.visibility = View.GONE
                    }
                }else{
                    binding.placeholderLabel.visibility = View.VISIBLE
                }
            })
            deleteNote.observe(this@NotesFragment, Observer {
                animation.startShimmer()
                animation.visibility = View.VISIBLE
                getAllNotes(this@NotesFragment)
            })
            error.observe(this@NotesFragment, Observer {
                animation.stopShimmer();
                animation.visibility = View.GONE
                binding.placeholderLabel.visibility = View.VISIBLE
            })
        }
    }

    override fun getViewModel(): Class<NotesViewModel> {
        return NotesViewModel::class.java
    }
    fun onClickItem(list: NoteModelEntry) {
        (activity as MainActivity).onClickItem(list.bibleId)
    }

    fun deleteRow(entry: NoteModelEntry) {
        val ab: AlertDialog.Builder = AlertDialog.Builder(activity)
        ab.setTitle("Delete")
        ab.setMessage("Are you sure to delete this item?")
        ab.setPositiveButton("Yes") { _, id ->
            viewModel.deleteNoteById(entry.id)
        }
        ab.setNegativeButton(
            "No"
        ) { pObjDialog, id -> pObjDialog.dismiss() }
        ab.show()

    }
}
