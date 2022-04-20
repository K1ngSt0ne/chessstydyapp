package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

public class BufferActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buffer);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        String lastActivityValue=settings.getString("lastActivity", "не определено");
        startLastActivity(lastActivityValue);
    }
    void startLastActivity(String name)
    {
        switch (name)
        {
            case "lessonActivity":
                Intent intent_lessons = new Intent(BufferActivity.this, TopicLessonsActivity.class);
                startActivity(intent_lessons);
                finish();
                break;
            case "taskActivity":
                Intent intent_tasks = new Intent(BufferActivity.this, TasksMenuActivity.class);
                startActivity(intent_tasks);
                finish();
                break;
            case "playActivity":
                Intent intent_play = new Intent(BufferActivity.this, PlayBoardActivity.class);
                startActivity(intent_play);
                finish();
                break;
        }
    }
}