package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class TasksMenuActivity extends AppCompatActivity {
    ArrayList<TopicLesson> taskList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks_menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        RecyclerView rvTasks = (RecyclerView) findViewById(R.id.task_chosen_recycler);
        setTaskType();
        TopicLessonsAdapter.OnTopicLessonClickListener tlClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                goToChosenTask(position);
            }
        };
        TopicLessonsAdapter adapter = new TopicLessonsAdapter(taskList, tlClickListener);
        rvTasks.setAdapter(adapter);
        rvTasks.setLayoutManager(new LinearLayoutManager(this));

    }
    void setTaskType()
    {
        taskList.add(new TopicLesson("Мат в 1 ход", "Решение задач, где нужно посавить мат за 1 ход", R.drawable.figure));
        taskList.add(new TopicLesson("Мат в 2 хода", "Решение задач, где нужно посавить мат за 2 хода", R.drawable.figure));
        taskList.add(new TopicLesson("Мат в 3 хода", "Решение задач, где нужно посавить мат за 3 хода", R.drawable.figure));
        taskList.add(new TopicLesson("Мат ладьей и конем", "Решение задач, где нужно поставить мат ладьей и конем", R.drawable.figure));
        taskList.add(new TopicLesson("Мат с жертвой фигуры", "Решение задач, где нужно поставить мат, пожертвовав фигуру", R.drawable.figure));
    }
    void goToChosenTask(int pos)
    {
        Intent chosen_type = new Intent(this, TaskSolvingActivity.class);
        switch (pos)
        {
            case 0:
                chosen_type.putExtra("chosen_type_task",0);
                break;
            case 1:
                chosen_type.putExtra("chosen_type_task",1);
                break;
            case 2:
                chosen_type.putExtra("chosen_type_task",2);
                break;
            case 3:
                chosen_type.putExtra("chosen_type_task",3);
                break;
            case 4:
                chosen_type.putExtra("chosen_type_task",4);
                break;
        }
        startActivity(chosen_type);
        finish();
    }
}