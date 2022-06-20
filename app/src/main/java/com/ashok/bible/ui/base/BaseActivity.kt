package com.lakki.kotlinlearning.view.base

import android.os.Bundle
import android.widget.TextView
import dagger.android.AndroidInjection
import javax.inject.Inject
import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.ashok.bible.App
import com.ashok.bible.R
import com.ashok.bible.common.AppConstants
import com.ashok.bible.data.local.BibleDatabase
import com.ashok.bible.utils.LocalizationUtil
import com.ashok.bible.utils.SharedPrefUtils
import com.google.android.material.snackbar.Snackbar


abstract class BaseActivity<V : ViewModel, D : ViewDataBinding> : AppCompatActivity() {
    lateinit var binding: D
    lateinit var viewModel: V
    protected abstract fun getLayoutRes(): Int
    protected abstract fun init()
    protected abstract fun getViewModel(): Class<V>
    lateinit var dialog: AlertDialog

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var pref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        binding = DataBindingUtil.setContentView(this, getLayoutRes())
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel())
        initDialog()
        init()
    }
    /* override fun supportFragmentInjector(): AndroidInjector<Fragment> {
         return fragmentAndroidInjector
     }*/

    fun showSnackbar(msg: String?) {
        if (msg != null && msg.trim { it <= ' ' } != "") {

            val snackbar =
                Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT)
            if (!snackbar.isShown) {
                val snackView = snackbar.view
                val tv =
                    snackView.findViewById(R.id.snackbar_text) as TextView
                tv.maxLines = 4
                snackbar.show()
            }
        }
    }

    fun initDialog() {
        val alertLayout = layoutInflater.inflate(R.layout.layout_dialog, null)
        val builder = AlertDialog.Builder(this)
        builder.setCancelable(false)
        builder.setView(alertLayout);
        dialog = builder.create()
    }

    fun showDialog() {
        if (dialog != null)
            dialog.show()
    }
    fun dismissDialog() {
        if (dialog != null)
            dialog.dismiss()
    }

}