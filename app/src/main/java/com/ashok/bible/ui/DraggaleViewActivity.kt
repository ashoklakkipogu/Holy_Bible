package com.ashok.bible.ui

import android.R.attr.button
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ashok.bible.R
import com.ashok.bible.generated.callback.OnClickListener
import com.github.pedrovgs.DraggablePanel


class DraggaleViewActivity : AppCompatActivity() {
    var draggablePanel: DraggablePanel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_draggale_view)
        draggablePanel = findViewById<View>(R.id.draggable_panel) as DraggablePanel
        draggablePanel!!.setFragmentManager(supportFragmentManager)
        draggablePanel!!.setTopFragment(One())
        draggablePanel!!.setBottomFragment(Two())
        draggablePanel!!.setTopViewHeight(300)
        draggablePanel!!.initializeView()

        val handler = Handler()
        handler.postDelayed({ draggablePanel!!.closeToLeft() }, 100)


        var button = findViewById<View>(R.id.button) as Button
        button.setOnClickListener {
            draggablePanel!!.maximize()

        }

    }
}
