package com.example.chessstudyappver11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class TopicLessonsActivity extends AppCompatActivity {
    ArrayList<TopicLesson> listTopicLessons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic_lessons);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        RecyclerView rvTopicLessons = (RecyclerView) findViewById(R.id.topic_lesson_recycler);
        setTopicLessons();
        TopicLessonsAdapter.OnTopicLessonClickListener tlClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                goToLessonList(position);
            }
        };
        TopicLessonsAdapter adapter = new TopicLessonsAdapter(listTopicLessons, tlClickListener);
        rvTopicLessons.setAdapter(adapter);
        rvTopicLessons.setLayoutManager(new LinearLayoutManager(this));

    }
    void setTopicLessons()
    {
        listTopicLessons.add(new TopicLesson("Правила шахмат", "Узнайте основные шахматные правила", R.drawable.chess_free_png_image));
        listTopicLessons.add(new TopicLesson("Учимся двигать фигуры", "Узнайте о том, как ходят фигуры", R.drawable.chess_free_png_image));
        listTopicLessons.add(new TopicLesson("Этапы игры", "Узнайте об этапах шахматной партии", R.drawable.chess_free_png_image));
        listTopicLessons.add(new TopicLesson("Правила шахмат: продвинутые", "Узнайте о специфичных шахматных правилах", R.drawable.chess_free_png_image));
        listTopicLessons.add(new TopicLesson("Шахматная тактика", "Узнайте основные приемы шахматной тактики", R.drawable.chess_free_png_image));
        listTopicLessons.add(new TopicLesson("Шахматная стратегия", "Узнайте основные приемы шахматной стратегии", R.drawable.chess_free_png_image));

    }
    void goToLessonList(int position)
    {
        Intent intent = new Intent(TopicLessonsActivity.this, LessonsListActivity.class);
        switch (position)
        {
            case 0:
                intent.putExtra("topic_chosen","chess_rules");
                break;
            case 1:
                intent.putExtra("topic_chosen","move_figure");
                break;
            case 2:
                intent.putExtra("topic_chosen","game_stages");
                break;
            case 3:
                intent.putExtra("topic_chosen","chess_rules_prof");
                break;
            case 4:
                intent.putExtra("topic_chosen","chess_tactic");
                break;
            case 5:
                intent.putExtra("topic_chosen","chess_strategy");
                break;
        }
        startActivity(intent);
        finish();

    }
    public void returnToMainScreen(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}