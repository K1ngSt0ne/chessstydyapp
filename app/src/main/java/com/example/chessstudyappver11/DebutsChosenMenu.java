package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;

public class DebutsChosenMenu extends AppCompatActivity {
    ArrayList<TopicLesson> debutsListChosen = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts_chosen_menu);
        //убираем actionBar (верхнюю  плашку)
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        //банлд нам необходим что бы узнать, какой из предыдщих жлемента меню был выбран (открытый, закрытый или полуоткрытый дебют)
        Bundle arguments = getIntent().getExtras();
        //загружаем в массив список дебютов
        initArrayListChosenDebuts(arguments);
        //и затем - находим на ресайклер (меню) на активности
        RecyclerView rvDebutsTypeChosen = (RecyclerView) findViewById(R.id.debuts_chosen_recycler);
        //вешаем обработчик нажатия на элемент
        TopicLessonsAdapter.OnTopicLessonClickListener lClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                Intent intent = new Intent(DebutsChosenMenu.this, DebutsReviewActivity.class);
                intent.putExtra("id_debuts", position);//какой конкретно дебют был выбран
                intent.putExtra("chosen_type", arguments.get("chosen_type_debuts").toString());//передаем тип дебюта, что бы загружать нужный массив из json'a
                startActivity(intent);
                finish();
            }
        };
        //и загружаем нас массив
        TopicLessonsAdapter adapter = new TopicLessonsAdapter(debutsListChosen, lClickListener);
        rvDebutsTypeChosen.setAdapter(adapter);
        rvDebutsTypeChosen.setLayoutManager(new LinearLayoutManager(this));
    }
    void initArrayListChosenDebuts(Bundle args)
    {
        //получаем выбранную тему
        String name = args.get("chosen_type_debuts").toString();
        switch (name)
        {
            case "open":
                //добавляем элементы в массив
                debutsListChosen.add(new TopicLesson("Испанская партия","1. e2-e4 e7-e5 2. Кg1-f3 Кb8-c6\n3. Сf1-b5", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Венская партия","1. e2-e4 e7-e5 2. Кb1-c3", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Итальянская партия","1. e2-e4 e7-e5 2. Кg1-f3 Кb8-c6\n3. Сf1-b5 Сf8-c5", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Русская партия","1. e2-e4 e7-e5 2. Кg1-f3 Кg8-f6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Дебют слона","1. e2-e4 e7-e5 2. Cf1-c4", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Дебют трех коней","1. e2-e4 e7-e5 2. Kg1-f3 Kb8-c6\n3. Kb1-c3", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Дебют четырех коней","1. e2-e4 e7-e5 2. Kg1-f3 Kb8-c6\n3. Kb1-c3 Kg8-f6", R.drawable.figure));
                break;
            case "closed":
                debutsListChosen.add(new TopicLesson("Ферзевый гамбит (принятый)","1. d2-d4 d7-d5 2. c2-c4 dxc4", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Ферзевый гамбит (отказанный)","1. d2-d4 d7-d5 2. c2-c4 е7-е6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Контргамбит Альбина","1. d2-d4 d7-d5 2. c2-c4 е7-е5", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Славянская защита","1. d2-d4 d7-d5 2. c2-c4 с7-с6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Защита Нимцовича","1. d2-d4 Kg8-f6 2.c2-c4 e7-e6\n3. Kb1-c3 Cf8-b4", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Староиндийская защита","1. d2-d4 Kg8-f6 2. c2-c4 g7-g6\n3. Kb1-c3 Cf8-g7", R.drawable.figure));
            case "subopen":
                debutsListChosen.add(new TopicLesson("Сицилианская защита","1. e2-e4 c7-c5", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Защита Каро-Канн","1. e2-e4 c7-c6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Французская защита","1. e2-e4 e7-e6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Защита Алехина","1. e2-e4 Kg8-f6", R.drawable.figure));
                debutsListChosen.add(new TopicLesson("Скандинавская защита","1. e2-e4 d7-d5", R.drawable.figure));
                break;
        }
    }
}