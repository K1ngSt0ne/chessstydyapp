package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class TaskSolvingActivity extends AppCompatActivity implements ChessDelegate{
    ChessView mChessView;
    ChessGame chessGame = new ChessGame();
    ArrayList<ChessPiece> testPiecesBox = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_solving);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        String json_string = readTaskFile();
        Gson gson = new Gson();
        TaskAndDebuts listOfTasks =  gson.fromJson(json_string, TaskAndDebuts.class);
        mChessView = findViewById(R.id.chess_task_view);
        mChessView.mChessDelegate=this;
        chessGame.setPiecesBox(testPiecesBox);
    }
    void initChosenTask(Bundle args)
    {
        String name = args.get("chosen_type_task").toString();
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