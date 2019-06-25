package com.example.darthvader.speechrecognition;

import android.app.Application;
import android.speech.SpeechRecognizer;

public class MyApplication extends Application {
    public static SpeechRecognizer recognizer;

    @Override
    public void onCreate() {
        super.onCreate();
        recognizer=SpeechRecognizer.createSpeechRecognizer(this);

    }
    
}
