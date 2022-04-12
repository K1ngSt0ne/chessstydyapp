package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class DebutsReviewActivity extends AppCompatActivity implements ChessDelegate {
    ChessView mChessView; //наша доска
    private final String TAG = "ChessGame";
    ChessGame chessGame = new ChessGame(); //игровая логика

    //текст вью для текстовой информации (название дебюта и описание)
    TextView debutDescription;
    TextView debutName;
    //ммассивы, для загрузки всей информации
    List<DebutsDescription> listOfChosenDebuts = new ArrayList<>();
    //индекс дебюта и тип
    int id_ch_deb;
    String debut_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts_review);
        //убираем actionBar
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        debutDescription = (TextView) findViewById(R.id.debutsDescription);
        debutDescription.setMovementMethod(new ScrollingMovementMethod()); //добавление скролла
        debutName = (TextView) findViewById(R.id.textDebut);
        Bundle arguments = getIntent().getExtras();
        //получаем json строку из файла
        String json_string = readDebutFile();
        //создаем объект GSON для десериализации нашей строки
        Gson gson = new Gson();
        //растаскиваем в наш объект-"матрешку"
        Debuts listOfDebuts =  gson.fromJson(json_string, Debuts.class);
        //написать метод с инициализацией выбранного типа дебюта
        listOfChosenDebuts=initChosenDebutType(listOfDebuts, arguments.get("chosen_type").toString());
        debutDescription.setText(listOfChosenDebuts.get(Integer.parseInt(arguments.get("id_debuts").toString())).getDescription());
        debutName.setText(listOfChosenDebuts.get(Integer.parseInt(arguments.get("id_debuts").toString())).getName());
        mChessView = findViewById(R.id.chess_debut_view);//находим нашу доску и присваиваем ей возможность двигать фигуры
        mChessView.mChessDelegate=this;
        id_ch_deb=Integer.parseInt(arguments.get("id_debuts").toString());
        debut_type=arguments.get("chosen_type").toString();
        animationMoves(id_ch_deb);//запускаем анимацию:)
    }

    @Override //метод необходим для получение фигуры с клетки
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override //метод отвечает за передвижение фигур по доске
    public void movePiece(Square from, Square to) {
        chessGame.movePiece(from,to);
        mChessView = findViewById(R.id.chess_debut_view);
        mChessView.invalidate();
    }

    List<DebutsDescription> initChosenDebutType(Debuts object, String chosenDebutsType)
    {
        List<DebutsDescription> debutsList = new ArrayList<>();
        switch (chosenDebutsType)
        {
            case "open":
                debutsList=object.getDebuts_type().get(0).getOpen_debuts();
                break;
            case "closed":
                debutsList=object.getDebuts_type().get(0).getClose_debuts();
                break;
            case "subopen":
                debutsList=object.getDebuts_type().get(0).getHalf_open_debuts();
                break;
        }
        return debutsList;
    }



    void animationMoves(int id_chosen_debut) //метод отвечает за анимацию передвижения фигур (все происходит по таймеру)
    {
        ArrayList<Moves> debut = initStartMoves(id_chosen_debut); //массив с ходами
        Timer t = new Timer(); //объект таймера, необходимый нам для псевдоанимации
        //создаем новую задачу
        TimerTask task = new TimerTask(){
            int i=0;
            public void run() {
                chessGame.movePiece(debut.get(i).getSq_from(), debut.get(i).getSq_to());
                if(i==debut.size()-1) //повторяем до тех пор пока в массиве есть ходы
                    t.cancel();
                i=i+1;
                mChessView.invalidate(); //обновляем нашу доску (перерисовываем)
            }

        };
        t.scheduleAtFixedRate(task, 1000, 1000); //запускаем нашу задачу на исполнение

    }
    ArrayList<Moves> initStartMoves(int id_chosen_debut) //здесь будем исопльлзовать наш парсер из PGN в клеточки:)
    {
        ArrayList<Moves> debutsMoves; //создаем массив с ходами. В данный момент - хардкод
        ParsePGNotationToSquare pgn = new ParsePGNotationToSquare(listOfChosenDebuts.get(id_chosen_debut).getPgn());
        debutsMoves = pgn.loadMovesFromPGNToArray();
        /*debutsMoves.add(new Moves(new Square(4,1), new Square(4,3)));
        debutsMoves.add(new Moves(new Square(4,6), new Square(4,4)));
        debutsMoves.add(new Moves(new Square(6,0), new Square(5,2)));
        debutsMoves.add(new Moves(new Square(1,7), new Square(2,5)));
        debutsMoves.add(new Moves(new Square(5,0), new Square(1,4)));*/
        return debutsMoves;
    }



    String readDebutFile()  //метод для чтение json файла
    {
        BufferedReader str = new BufferedReader(new InputStreamReader(getResources().openRawResource(R.raw.debuts_description)));
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

    public void repeatDebuts(View v) //если пользователь захочет повторить анимацию ему нужно всего лишь нажать на одну кнопку и он получит результат:)
    {
        chessGame = new ChessGame();
        animationMoves(id_ch_deb);
    }

    public void returnToDebutsMenuScreen(View v)
    {
        Intent intent = new Intent(this, DebutsChosenMenu.class);
        intent.putExtra("chosen_type_debuts", debut_type);
        startActivity(intent);
        finish();
    }

}