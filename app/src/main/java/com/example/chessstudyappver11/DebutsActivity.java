package com.example.chessstudyappver11;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DebutsActivity extends AppCompatActivity implements ChessDelegate{

    ChessView mChessView;
    ChessGame chessGame = new ChessGame();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts);
        mChessView = findViewById(R.id.chess_view);
        mChessView.mChessDelegate=this;
        // setContentView(new ChessView(this));
    }


    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
            chessGame.movePiece(from,to);
            mChessView = findViewById(R.id.chess_view);
            mChessView.invalidate();
    }
}