package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class TaskSolvingActivity extends AppCompatActivity implements ChessDelegate{
    ChessView mChessView;
    ChessGame chessGame = new ChessGame();
    ArrayList<ChessPiece> testPiecesBox = new ArrayList<>();
    List<TaskAndDebutsDescription> chosenTask = new ArrayList<>();

    TextView mTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_solving);
        //следующие 2 строки чтобы убрать action bar (верхнюю панель) с экрана
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mTextView = (TextView) findViewById(R.id.textTask);
        //бандл необходим нам для получение выбранной темы задач с предыдущего экрана (активности)
        Bundle arguments = getIntent().getExtras();
        //получаем json строку из файла
        String json_string = readTaskFile();
        //создаем объект GSON для десериализации нашей строки
        Gson gson = new Gson();
        //растаскиваем в наш объект-"матрешку"
        TaskAndDebuts listOfTasks =  gson.fromJson(json_string, TaskAndDebuts.class);
        //поулчаем id выбранной темы задачи (от 0 до 4)
        int id_task = Integer.parseInt(arguments.get("chosen_type_task").toString());
        //выбрали нужный нам массив с задачами
        chosenTask=initChosenTasks(listOfTasks, id_task);
        //находим нашу виртуальную доску,
        mChessView = findViewById(R.id.chess_task_view);
        //назначаем нашей доске делегат
        mChessView.mChessDelegate=this;
        initBoard(chosenTask);
        //chessGame.setPiecesBox(testPiecesBox);
    }

    List<TaskAndDebutsDescription> initChosenTasks(TaskAndDebuts object, int chosenTaskId)
    {
        List<TaskAndDebutsDescription> t_a_n_d_List = new ArrayList<>();
        switch (chosenTaskId)
        {
            case 0:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0 /*т.к. элемент у нас всего один, то можно и поставить 0
        ну либо через взятие размера массива - 1*/).getList_of_OMM(); //потом через .get(i) где i - номер задачи, сможем переключаться
                mTextView.setText("Мат в 1 ход");
                break;
            case 1:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_TMM();
                mTextView.setText("Мат в 2 хода");
                break;
            case 2:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_ThMM();
                mTextView.setText("Мат в 3 хода");
                break;
            case 3:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_NnRM();
                mTextView.setText("Мат конем и ладьей");
                break;
            case 4:
                t_a_n_d_List= object.getList_of_task_or_debuts_topic().get(0).getList_of_MwS();
                mTextView.setText("Мат с жертвой фигуры");
                break;
        }
        return t_a_n_d_List;
    }

    void initBoard(List<TaskAndDebutsDescription> list)
    {
        String fen_position = list.get(0).getFen_or_pgn_str();
        ParseFENotationToSquare test = new ParseFENotationToSquare(fen_position);
        testPiecesBox = test.loadPositionsOnBoard();
        chessGame.setPiecesBox(testPiecesBox);
    }

    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
        chessGame.movePiece(from,to);
        mChessView = findViewById(R.id.chess_task_view);
        mChessView.invalidate();
    }
    String readTaskFile()
    {
        BufferedReader str = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.chesstasks)));
        StringBuilder total = new StringBuilder();
        try {
            for (String line; (line = str.readLine()) != null; ) {
                total.append(line).append('\n');
            }
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "В вашем файле есть ошибочка)", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();
        }

        Log.d("JSONread", total.toString());
        return total.toString();
    }
}