package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class LessonsListActivity extends AppCompatActivity {
    ArrayList<TopicLesson> listLessons = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        Bundle arguments = getIntent().getExtras();
        initListLessonsArray(arguments);
        RecyclerView rvLessons = (RecyclerView) findViewById(R.id.lessons_list_recycler);
        TopicLessonsAdapter.OnTopicLessonClickListener lClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                Intent intent = new Intent(LessonsListActivity.this, LessonActivity.class);
                startActivity(intent);
                finish();
            }
        };
        TopicLessonsAdapter adapter = new TopicLessonsAdapter(listLessons, lClickListener);
        rvLessons.setAdapter(adapter);
        rvLessons.setLayoutManager(new LinearLayoutManager(this));

    }
    void initListLessonsArray(Bundle args)
    {
        String name = args.get("topic_chosen").toString();
        switch (name)
        {
            case "chess_rules":
                listLessons.add(new TopicLesson("О шахматах","Краткая история и цель игры", R.drawable.figure));
                listLessons.add(new TopicLesson("О доске","Что представляет из себя шахматная доска", R.drawable.figure));
                listLessons.add(new TopicLesson("О фигурах","О фигурах и их расположении на доске", R.drawable.figure));
                listLessons.add(new TopicLesson("Шах, мат и пат","Рассказываем об основных событиях во время партии", R.drawable.figure));
                listLessons.add(new TopicLesson("Рокировка","Что это такое и для чего", R.drawable.figure));
                listLessons.add(new TopicLesson("Превращение пешки","Расскажем когда пешка может стать сильнейшей фигурой", R.drawable.figure));
                break;
            case "move_figure":
                listLessons.add(new TopicLesson("Как ходит пешка","Учимся передвигать пешку", R.drawable.white_pawn));
                listLessons.add(new TopicLesson("Как ходит конь","Учимся передвигать коня", R.drawable.white_knight));
                listLessons.add(new TopicLesson("Как ходит слон","Учимся передвигать слона", R.drawable.white_bishop));
                listLessons.add(new TopicLesson("Как ходит ладья","Учимся передвигать ладью", R.drawable.white_rook));
                listLessons.add(new TopicLesson("Как ходит ферзь","Учимся передвигать ферзя", R.drawable.white_queen));
                listLessons.add(new TopicLesson("Как ходит король","Учимся передвигать короля", R.drawable.white_king));
                break;
            case "game_stages":
                listLessons.add(new TopicLesson("Немного о дебюте","Основные идеи и цель этой стадии игры", R.drawable.figure));
                listLessons.add(new TopicLesson("Немного о миттельшпиле","В середине игры происходят основные события", R.drawable.figure));
                listLessons.add(new TopicLesson("Немного об эндшпиле","Конец партии важен также, как и ее начало", R.drawable.figure));
                break;
            case "chess_rules_prof":
                break;
            case "chess_tactic":
                break;
            case "chess_strategy":
                break;
        }
    }
    public void returnToTopicLessonScreen(View v)
    {
        Intent intent = new Intent(this, TopicLessonsActivity.class);
        startActivity(intent);
        finish();
    }
}
