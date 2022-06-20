package com.ashok.bible.utils;

import android.content.Context;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class TtsManager {
    private final static int SDK = Build.VERSION.SDK_INT;

    private final static String TAG = "TTS-TTSManager";

    private TextToSpeech tts;

    private boolean isLoaded = false;

    private Context context;

    public TtsManager(Context context) {
        this.context = context;
        try {
            TextToSpeech.OnInitListener onInitListener = new TextToSpeech.OnInitListener() {
                @Override
                public void onInit(int status) {
                    if (status == TextToSpeech.SUCCESS) {
                        tts.setLanguage(Locale.US);
                        isLoaded = true;
                    }
                }
            };
            tts = new TextToSpeech(context, onInitListener);
        } catch (Exception e) {
            Log.e(TAG, Arrays.toString(e.getStackTrace()));
        }
    }

    public TtsManager(TextToSpeech tts) {
        isLoaded = true;
        this.tts = tts;
    }

    public void shutDown() {
        tts.shutdown();

    }
    public void stop() {
        tts.stop();
    }

    public boolean say(String text, Locale locale) {
        if (isLoaded && text != null && locale != null) {
            int available = tts.isLanguageAvailable(locale);
            if (available >= TextToSpeech.LANG_AVAILABLE) {
                tts.setLanguage(locale);
                tts.setSpeechRate(1.0f);
                if (SDK >= 21) {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "random string - 2d22332");
                } else {
                    tts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
                }
                return true;
            } else {
                Log.e(TAG, "Can't play text = " + text + " for locale = " + locale.toString());
                return false;
            }
        } else {
            Log.e(TAG, "TTS Not Initialized");
            return false;
        }
    }


    private Locale[] getSupportedLanguages() {
        if (SDK >= 21) {
            Set<Locale> lang = tts.getAvailableLanguages();
            if (lang == null) {
                Log.e(TAG, "Can't retrieve list of supported languages");
                return new Locale[0];
            }
            return lang.toArray(new Locale[lang.size()]);
        } else {
            List<Locale> result = new ArrayList<>();
            Locale[] locales = Locale.getAvailableLocales();
            for (Locale loc : locales) {
                if (!loc.toString().toLowerCase().contains("os")
                        && tts.isLanguageAvailable(loc) >= 0) {
                    result.add(loc);
                }
            }
            return result.toArray(new Locale[result.size()]);
        }
    }

}
