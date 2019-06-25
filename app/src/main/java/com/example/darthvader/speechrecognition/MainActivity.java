package com.example.darthvader.speechrecognition;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.provider.CalendarContract;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;
import java.util.Random;

import static com.example.darthvader.speechrecognition.MyApplication.recognizer;

public class MainActivity extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO}, 100);
        } else {
            notification();

        }
    }

    @SuppressWarnings("deprecation")
    public void notification()
    {
        Intent recordIntent=new Intent(this,RecordCheckReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(this,0,recordIntent,0);
        NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String id="com.example.darthvader.speechrecognition";
            CharSequence sequence="Record Audio";
            NotificationChannel channel;
            channel = new NotificationChannel(id, sequence, NotificationManager.IMPORTANCE_HIGH);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setLightColor(Color.RED);
            manager.createNotificationChannel(channel);
            Notification notification = new Notification.Builder(this, id)
                    .setContentTitle("Record Audio")
                    .setAutoCancel(true)
                    .addAction(R.drawable.ic_mic_none_black_24dp, "Record", pendingIntent)
                    .setSmallIcon(R.drawable.ic_mic_black_24dp)
                    .build();
            manager.notify(1,notification);

        }
        else
        {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                Toast.makeText(this, "Hello versions above jellybean", Toast.LENGTH_SHORT).show();
                Notification notification = new Notification.Builder(this)
                        .setContentTitle("Record Audio")
                        .setAutoCancel(true)
                        .addAction(R.drawable.ic_mic_none_black_24dp, "Record", pendingIntent)
                        .setSmallIcon(R.drawable.ic_mic_black_24dp)
                        .build();
                manager.notify(1,notification);

            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Audio permission granted", Toast.LENGTH_SHORT).show();
                notification();
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        recognizer.stopListening();
    }
}
