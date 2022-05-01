package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.webkit.WebView;

public class LessonActivity extends AppCompatActivity {
    WebView lesson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);//еще не готово
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle arguments = getIntent().getExtras();
        String name;
        if (arguments==null)
        {
            SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
            name=settings.getString("lastLesson", "не определено");
        }
        else
        {
            name = arguments.get("html_page").toString();
        }

        String address = "file:///android_res/raw/" + name;
        lesson = (WebView) findViewById(R.id.lesson_description);
        lesson.loadUrl(address); //при переходе с активности нужно выдергивать с
        // какой мы пришли и вставлять в строку,а потом загружать нужную страничку
        //сохраняем на каком уроке мы остановились
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("lastLesson",name);
        prefEditor.putString("lastActivity","lessonActivity");
        prefEditor.apply();
    }
}