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
        taskList.add(new TopicLesson("Мат слоном и ладьей", "Решение задач, где нужно поставить мат ладьей и конем", R.drawable.figure));
        taskList.add(new TopicLesson("Мат с жертвой фигуры", "Решение задач, где нужно поставить мат, пожертвовав фигуру", R.drawable.figure));
    }
    void goToChosenTask(int pos)
    {
        Intent chosen_type = new Intent(this, TaskSolvingActivity.class);
        switch (pos)
        {
            case 0:
                chosen_type.putExtra("chosen_type_task","one_move_mate");
                break;
            case 1:
                chosen_type.putExtra("chosen_type_task","two_move_mate");
                break;
            case 2:
                chosen_type.putExtra("chosen_type_task","three_move_mate");
                break;
            case 3:
                chosen_type.putExtra("chosen_type_task","mate_with_N_and_R");
                break;
            case 4:
                chosen_type.putExtra("chosen_type_task","mate_with_sacrifice");
                break;
        }
        startActivity(chosen_type);
        finish();
    }
}