package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class LessonActivity extends AppCompatActivity {
    WebView lesson;
    String topicName;
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
            topicName = settings.getString("topicNameValue", "не определено");
        }
        else
        {
            name = arguments.get("html_page").toString();
            topicName = arguments.get("topicName").toString();
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
        prefEditor.putString("topicNameValue", topicName);
        prefEditor.apply();
    }

    public void backToTopicChosenMenu(View v)
    {
        Intent intent = new Intent(LessonActivity.this, LessonsListActivity.class);
        intent.putExtra("topic_chosen", topicName);
        startActivity(intent);
        finish();
    }
}