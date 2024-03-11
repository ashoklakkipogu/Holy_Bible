package com.ashok.bible.ui.utilities

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.speech.tts.TextToSpeech.OnInitListener
import android.util.Log
import java.util.Arrays
import java.util.Locale


class TtsManager constructor(context: Context?) {
    private var tts: TextToSpeech? = null
    private var isLoaded = false

    init {
        try {
            val onInitListener =
                OnInitListener { status ->
                    if (status == TextToSpeech.SUCCESS) {
                        tts!!.setLanguage(Locale.US)
                        isLoaded = true
                    }
                }
            tts = TextToSpeech(context, onInitListener)
        } catch (e: Exception) {
            Log.e(TAG, Arrays.toString(e.stackTrace))
        }

    }

    fun shutDown() {
        tts!!.shutdown()
    }

    fun stop() {
        tts!!.stop()
    }

    fun say(text: String?, locale: Locale = Locale.getDefault()): Boolean {
        return if (isLoaded && text != null) {
            val newText = text.replace(":", " ")
            val available = tts!!.isLanguageAvailable(locale)
            if (available >= TextToSpeech.LANG_AVAILABLE) {
                tts!!.setLanguage(locale)
                tts!!.setSpeechRate(1.0f)
                if (SDK >= 21) {
                    tts!!.speak(newText, TextToSpeech.QUEUE_FLUSH, null, "random string - 2d22332")
                } else {
                    tts!!.speak(newText, TextToSpeech.QUEUE_FLUSH, null)
                }
                true
            } else {
                Log.e(
                    TAG,
                    "Can't play text = $newText for locale = $locale"
                )
                false
            }
        } else {
            Log.e(TAG, "TTS Not Initialized")
            false
        }
    }


    companion object {
        private val SDK = Build.VERSION.SDK_INT
        private const val TAG = "TTS-TTSManager"
    }
}

