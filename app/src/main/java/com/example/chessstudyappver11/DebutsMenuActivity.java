package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;

public class DebutsMenuActivity extends AppCompatActivity {
    ArrayList<TopicLesson> listDebuts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        RecyclerView rvDebuts = (RecyclerView) findViewById(R.id.debuts_type_recycler);
        setDebutsType();
        TopicLessonsAdapter.OnTopicLessonClickListener tlClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                goToChosenDebutsList(position);
            }
        };
        TopicLessonsAdapter adapter = new TopicLessonsAdapter(listDebuts, tlClickListener);
        rvDebuts.setAdapter(adapter);
        rvDebuts.setLayoutManager(new LinearLayoutManager(this));
    }
    void setDebutsType()
    {
        listDebuts.add(new TopicLesson("Открытые","Белые начинают е4, а черные отвечают е5",R.drawable.figure));
        listDebuts.add(new TopicLesson("Закрытые","Белые начинают делают любой первый ход, кроме е4",R.drawable.figure));
        listDebuts.add(new TopicLesson("Полуоткрытые","Белые начинают е4, а черные отвечают любой ход, кроме е5",R.drawable.figure));
    }
    void goToChosenDebutsList(int position)
    {

    }
}