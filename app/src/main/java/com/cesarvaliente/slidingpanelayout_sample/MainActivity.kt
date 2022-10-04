package com.cesarvaliente.slidingpanelayout_sample

import android.content.res.Configuration
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.slidingpanelayout.widget.SlidingPaneLayout
import androidx.window.layout.WindowMetricsCalculator
import com.google.android.material.slider.Slider


enum class WindowSizeClass { COMPACT, MEDIUM, EXPANDED }

class MainActivity : AppCompatActivity() {
    private val TAG = "SLIDEPANE"
    lateinit var slidingPane: SlidingPaneLayout
    lateinit var slidingText: TextView
    lateinit var sidePane : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


//        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)
//        val height = displayMetrics.heightPixels
//        val width = displayMetrics.widthPixels







        slidingPane = findViewById(R.id.sliding_pane_layout)

        val open = findViewById<Button>(R.id.side_open)
        open.setOnClickListener { slidingPane.open() }

        slidingText = findViewById<TextView>(R.id.sliding_text)

        sidePane = findViewById<LinearLayout>(R.id.side_pane_content)
//        computeWindowSizeClasses()
//        val slider = findViewById<Slider>(R.id.width_slider)
//
//
//        slider.addOnChangeListener { slider, value, _ ->
//            Log.i(TAG, "new width: $value")
//
//            val params: ViewGroup.LayoutParams? = sidePane.layoutParams
//            params?.width = (value * this.resources.displayMetrics.density).toInt()
//            sidePane.layoutParams = params
//
//            Log.i(TAG, "slideable: " + slidingPane.isSlideable)
//            slidingText.text = "slideable: " + slidingPane.isSlideable.toString()
//        }
    }

    //This function is just to see how the isSlideable() state changes when there is a new configuration change.
    //SlidingPaneLayout can be used without this.
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        if (slidingPane.isSlideable) {
            Log.i(TAG, "slidingPane.isSlideable")
        } else {
            Log.i(TAG, "NOT slidingPane.isSlideable")
        }
    }

    private fun computeWindowSizeClasses() {
        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(this)

        val widthDp = metrics.bounds.width() /
                resources.displayMetrics.density
        val widthWindowSizeClass = when {
            widthDp < 600f -> WindowSizeClass.COMPACT
            widthDp < 840f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }

        val windowMetrics = WindowMetricsCalculator.getOrCreate().computeCurrentWindowMetrics(this)
        val currentBounds = windowMetrics.bounds // E.g. [0 0 1350 1800]
        val width = currentBounds.width()
        val height = currentBounds.height()

        Log.e(TAG, "computeWindowSizeClasses: $width" )

        if (width == widthWindowSizeClass.compareTo(WindowSizeClass.EXPANDED) ){

            val params: ViewGroup.LayoutParams? = sidePane.layoutParams
            params?.width = (width * this.resources.displayMetrics.density).toInt()
            sidePane.layoutParams = params
        }

        val heightDp = metrics.bounds.height() /
                resources.displayMetrics.density
        val heightWindowSizeClass = when {
            heightDp < 480f -> WindowSizeClass.COMPACT
            heightDp < 900f -> WindowSizeClass.MEDIUM
            else -> WindowSizeClass.EXPANDED
        }
    }
}