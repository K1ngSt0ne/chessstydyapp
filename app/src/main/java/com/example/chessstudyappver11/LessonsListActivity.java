package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.HashMap;

public class LessonsListActivity extends AppCompatActivity {
    ArrayList<TopicLesson> listLessons = new ArrayList<>();
    HashMap<String,String> lessonsDict= new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lessons);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        initHashMapLessons();
        Bundle arguments = getIntent().getExtras();
        initListLessonsArray(arguments);
        RecyclerView rvLessons = (RecyclerView) findViewById(R.id.lessons_list_recycler);
        TopicLessonsAdapter.OnTopicLessonClickListener lClickListener = new TopicLessonsAdapter.OnTopicLessonClickListener() {
            @Override
            public void onTopicLessonClick(TopicLesson topicLesson, int position) {
                goToLessonActivity(position, arguments.get("topic_chosen").toString());
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
                listLessons.add(new TopicLesson("О шахматах","Краткая история и цель игры", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("О доске","Что представляет из себя шахматная доска", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("О фигурах","О фигурах и их расположении на доске", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Шах, мат и пат","Рассказываем об основных событиях во время партии", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Рокировка","Что это такое и для чего", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Превращение пешки","Расскажем когда пешка может стать сильнейшей фигурой", R.drawable.chess_free_png_image));
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
                listLessons.add(new TopicLesson("Немного о дебюте","Основные идеи и цель этой стадии игры", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Немного о миттельшпиле","В середине игры происходят основные события", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Немного об эндшпиле","Конец партии важен также, как и ее начало", R.drawable.chess_free_png_image));
                break;
            case "chess_rules_prof":
                listLessons.add(new TopicLesson("Взятие на проходе", "Интересное правило, о котором мало кто знает", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Правило 50 ходов", "Правило, которое позволяет сделать ничью", R.drawable.chess_free_png_image));
                break;
            case "chess_tactic":
                listLessons.add(new TopicLesson("О тактие в целом", "В общих словах - для чего этого и зачем", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Двойной удар", "Самое страшное оружие в руках шахматиста", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Вскрытое нападение", "Делаем 'рентген' на противника", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Вскрытый шах", "Нападение на короля из-за угла", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Завлечение", "Заманиваем фигуру на неудобное поле", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Отвлечение", "Отвлекаем вражескую фигуру ради достижения цели", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Уничтожение защиты", "Добираемся до фигур противника", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Перекрытие", "Создаем препятсвия для дальнобойных фигур", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Блокировка", "Не даем убежать атакованной фигуре", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Освобождение поля", "Освободим поле для фигуру которой это нужнее", R.drawable.chess_free_png_image));
                break;
            case "chess_strategy":
                listLessons.add(new TopicLesson("О стратегии в целом", "В общих словах зачем это нужно", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Пешечные цепи", "Что это такое и для чего оно нужно", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Пешечные структуры", "Какие пешечные структуры бывают и что дают", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Пространство", "Важный ресурс шахмат наравне с фигурами", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Тип позиции", "Узнаем какие есть позиции и их особенности", R.drawable.chess_free_png_image));
                listLessons.add(new TopicLesson("Когда разменивать фигуры", "Брать или не брать - вот в чем вопрос...", R.drawable.chess_free_png_image));
                break;
        }
    }
    public void returnToTopicLessonScreen(View v)
    {
        Intent intent = new Intent(this, TopicLessonsActivity.class);
        startActivity(intent);
        finish();
    }
    void goToLessonActivity(int position, String topicName)
    {
        String chosenLesson = listLessons.get(position).getTopic_name();
        String html_name = lessonsDict.get(chosenLesson);
        Intent intent = new Intent(LessonsListActivity.this, LessonActivity.class);
        intent.putExtra("html_page", html_name);
        intent.putExtra("topicName", topicName);
        startActivity(intent);
        finish();
    }
    void initHashMapLessons()
    {
        lessonsDict.put("О шахматах","about_chess.html");
        lessonsDict.put("О доске", "about_board.html");
        lessonsDict.put("О фигурах", "about_figure.html");
        lessonsDict.put("Шах, мат и пат","check_mate_stalemate");
        lessonsDict.put("Рокировка", "castling.html");
        lessonsDict.put("Превращение пешки", "pawn_upgrade.html");

        lessonsDict.put("Как ходит пешка","pawn_move.html");
        lessonsDict.put("Как ходит конь","knight_move.html");
        lessonsDict.put("Как ходит слон","bishop_move.html");
        lessonsDict.put("Как ходит ладья","rook_move.html");
        lessonsDict.put("Как ходит ферзь","queen_move.html");
        lessonsDict.put("Как ходит король","king_move.html");

        lessonsDict.put("Немного о дебюте","debut_stage.html");
        lessonsDict.put("Немного о миттельшпиле","midgame_stage.html");
        lessonsDict.put("Немного об эндшпиле","endgame_stage.html");

        lessonsDict.put("Взятие на проходе", "en_passan.html");
        lessonsDict.put("Правило 50 ходов", "fifteen_moves.html");

        lessonsDict.put("О тактие в целом", "about_tactics.html");
        lessonsDict.put("Двойной удар", "double_strike.html");
        lessonsDict.put("Вскрытое нападение", "open_attack.html");
        lessonsDict.put("Вскрытый шах", "open_check.html");
        lessonsDict.put("Завлечение", "enticement.html");
        lessonsDict.put("Отвлечение", "distraction.html");
        lessonsDict.put("Уничтожение защиты", "destroy_defence.html");
        lessonsDict.put("Перекрытие", "closing.html");
        lessonsDict.put("Блокировка", "blocking.html");
        lessonsDict.put("Освобождение поля", "free_square.html");

        lessonsDict.put("О стратегии в целом", "about_strategy.html");
        lessonsDict.put("Пешечные цепи", "pawn_chains.html");
        lessonsDict.put("Пешечные структуры", "pawns_structure.html" );
        lessonsDict.put("Пространство", "free_space.html" );
        lessonsDict.put("Тип позиции", "type_position.html" );
        lessonsDict.put("Когда разменивать фигуры", "when_exchange_figure.html");
    }
}
