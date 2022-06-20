package com.ashok.bible.ui.admin

import android.net.Uri
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.remote.firebase.Firebase
import com.ashok.bible.data.remote.firebase.FirebaseImageUploadListener
import com.ashok.bible.data.remote.model.BannerModel
import com.ashok.bible.data.remote.model.LyricsModel
import com.ashok.bible.databinding.ActivityBannerPosBinding
import com.ashok.bible.databinding.ActivityLyricPostBinding
import com.ashok.bible.utils.DialogBuilder
import com.ashok.bible.utils.DialogListenerForLyricFilter
import com.ashok.bible.utils.Utils
import com.google.firebase.storage.StorageReference
import com.lakki.kotlinlearning.view.base.BaseActivity
import lv.chi.photopicker.PhotoPickerFragment
import javax.inject.Inject


class BannerPosActivity : BaseActivity<AdminViewModel, ActivityBannerPosBinding>(),
    PhotoPickerFragment.Callback {

    var imageUri: Uri? = null
    var filterValue = AppConstants.FILTER_ALL

    @Inject
    lateinit var mStorage: StorageReference

    override fun getViewModel(): Class<AdminViewModel> {
        return AdminViewModel::class.java
    }

    override fun getLayoutRes(): Int {
        return R.layout.activity_banner_pos
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.submit_btn -> {
                val editImageUrl = binding.editImageUrl.text.toString()

                if (editImageUrl.isEmpty()) {
                    showSnackbar("Please enter image url")
                } else {
                    showDialog()
                    val obj = BannerModel()
                    obj.image = editImageUrl
                    viewModel.createBanner(obj)
                }


            }
            R.id.select_img -> {
                openPicker()
            }
        }
    }

    override fun init() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        binding.handlers = this
        with(viewModel) {
            createBanner.observe(this@BannerPosActivity, Observer {
                dismissDialog()
                if (it != null) {
                    binding.editImageUrl.setText("")
                    binding.imageView.setImageResource(0);
                    showSnackbar("Create Banner Successfully.")
                }
            })

            error.observe(this@BannerPosActivity, Observer {
                dismissDialog()
                if (it != null) {
                    showSnackbar(it.getErrorMessage())
                }
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun openPicker() {
        PhotoPickerFragment.newInstance(
            multiple = true,
            allowCamera = true,
            maxSelection = 1,
            theme = R.style.ChiliPhotoPicker_Dark
        ).show(supportFragmentManager, "picker")
    }

    override fun onImagesPicked(photos: ArrayList<Uri>) {
        if (photos.isNotEmpty()) {
            for ((index, value) in photos.withIndex()) {
                binding.imageView.setImageURI(value)
                showDialog()
                Firebase.uploadImage(
                    this,
                    mStorage,
                    value,
                    object : FirebaseImageUploadListener {
                        override fun uploadSuccess(url: String) {
                            dismissDialog()
                            binding.editImageUrl.setText(url, TextView.BufferType.EDITABLE)
                        }

                        override fun uploadError() {
                            dismissDialog()
                        }
                    })
            }
        }
    }
}
