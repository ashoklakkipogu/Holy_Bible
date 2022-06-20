package com.lakki.kotlinlearning.view.base

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences

import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.BibleDatabase
import com.ashok.bible.utils.LocalizationUtil
import com.ashok.bible.utils.SharedPrefUtils
import com.google.android.material.snackbar.Snackbar
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment<V : ViewModel, D : ViewDataBinding> : Fragment() {
    lateinit var binding: D
    lateinit var viewModel: V
    protected abstract fun getLayoutRes(): Int
    protected abstract fun init()
    protected abstract fun getViewModel(): Class<V>
    lateinit var dialog: AlertDialog

    @Inject
    lateinit var pref: SharedPreferences

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidSupportInjection.inject(this)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false)
        initDialog()
        init()
        return binding.root
    }

    fun initDialog() {
        val alertLayout = layoutInflater.inflate(R.layout.layout_dialog, null)
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        builder.setView(alertLayout);
        dialog = builder.create()
    }

    fun showSnackbar(msg: String?) {
        if (msg != null && msg.trim { it <= ' ' } != "") {

            val snackbar =
                Snackbar.make(activity!!.findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            if (!snackbar.isShown) {
                val snackView = snackbar.view
                val tv =
                    snackView.findViewById(R.id.snackbar_text) as TextView
                tv.maxLines = 4
                snackbar.show()
            }
        }
    }

    fun showDialog() {
        if (dialog != null && !dialog.isShowing)
            dialog.show()
    }

    fun dismissDialog() {
        if (dialog != null && dialog.isShowing)
            dialog.dismiss()
    }

    /*override fun onAttach(context: Context) {
        val lan = SharedPrefUtils.getLanguage(pref)
        when (lan) {
            AppConstants.TELUGU -> {
                super.onAttach(LocalizationUtil.applyLanguage(context, "te"))
            }
            AppConstants.TAMIL -> {
                super.onAttach(LocalizationUtil.applyLanguage(context, "ta"))
            }
            else -> {
                super.onAttach(context)
            }
        }
    }*/
}