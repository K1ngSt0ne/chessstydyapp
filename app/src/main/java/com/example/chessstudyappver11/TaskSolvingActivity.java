package com.example.chessstudyappver11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;

public class TaskSolvingActivity extends AppCompatActivity implements ChessDelegate{
    ChessView mChessView;
    ChessGame chessGame = new ChessGame();
    ArrayList<ChessPiece> testPiecesBox = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_solving);

        mChessView = findViewById(R.id.chess_task_view);
        mChessView.mChessDelegate=this;
        testPiecesBox.add(new ChessPiece(4, 0, Player.WHITE, Chessman.KING, R.drawable.white_king));
        testPiecesBox.add(new ChessPiece(4, 7, Player.BLACK, Chessman.KING, R.drawable.black_king));
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
}