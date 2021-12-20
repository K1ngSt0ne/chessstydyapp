package com.example.chessstudyappver11;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DebutsActivity extends AppCompatActivity implements ChessDelegate{

    ChessView mChessView;
    private final String TAG = "ChessGame";
    ChessGame chessGame = new ChessGame();
    TextView statusBar;
    TextView historyMoves;
    int turns=1;
    ArrayList<Square> movesHistory = new ArrayList<>(); //вопрос, хранить в таком виде или все таки распарсить во что-то другое
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts);
        statusBar = findViewById(R.id.textViewGameStatus);
        historyMoves = findViewById(R.id.historyMoves);
        historyMoves.setMovementMethod(new ScrollingMovementMethod());
        mChessView = findViewById(R.id.chess_view);
        mChessView.mChessDelegate=this;
        //написать конвертер координат в нотацию (ходы)


    }


    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
            int start_size = chessGame.pieceBoxSize();
            Chessman piece_type = pieceAt(from).getChessman();
            chessGame.movePiece(from,to);
            int end_size = chessGame.pieceBoxSize();
            if (chessGame.isMoving())
            {
                movesHistory.add(to);
                historyMovesShow(to, start_size,end_size, piece_type);
            }
            if (chessGame.isEndGame())
            {
                String message_text="";
                if (chessGame.blackKingCheck)
                {
                    message_text="Белые выиграли (объявлен мат)";
                    historyMoves.setText(historyMoves.getText()+" 1-0 ");
                }

                if (chessGame.whiteKingCheck)
                {
                    message_text="Черные выиграли (объявлен мат)";
                    historyMoves.setText(historyMoves.getText()+" 0-1 ");
                }

                FragmentManager manager = getSupportFragmentManager();
                DialogFragmentShow myDialogFragment = new DialogFragmentShow("Партия закончена",message_text, this);
                myDialogFragment.show(manager, "myDialog");

            }
            else
            {
                mChessView = findViewById(R.id.chess_view);
                mChessView.invalidate();
            }
    }

    public void historyMovesShow(Square destination, int start_size, int end_size, Chessman piece)
    {
        ParseSquareToNotationMoves move = new ParseSquareToNotationMoves(destination.getColumn(),destination.getRow(), start_size,end_size, piece);
        String move_to_HM=move.convertSquareToNotation(move);
        String all_moves="";
        //т.к. мы сходили, у нас поменялась очередность хода, поэтому в данный момент ход черных, отсюда и проверка
        //(но на самом деле это для белых)
        if (chessGame.getTurnPlayer()==Player.BLACK)
        {
            if (chessGame.isEndGame())
                all_moves=historyMoves.getText()+ " "+ turns+move_to_HM+"#";
            else if (chessGame.blackKingCheck)
                all_moves=historyMoves.getText()+ " "+ turns+move_to_HM+"+";
            else if (chessGame.shortCastling)
            {
                all_moves=historyMoves.getText()+ ""+ turns+" O-O ";
                chessGame.shortCastling=false;
            }
            else if (chessGame.longCastling)
            {
                all_moves=historyMoves.getText() +  "" + turns +" O-O-O ";
                chessGame.longCastling=false;
            }

            else
                all_moves=historyMoves.getText()+ " "+ turns+move_to_HM + " ";
        }
        //(а это для черных)

        else
        {
            if (chessGame.isEndGame())
                all_moves=historyMoves.getText()+ " "+move_to_HM + "#";
            else if (chessGame.whiteKingCheck)
                all_moves=historyMoves.getText()+ " "+move_to_HM + "+";
            else if (chessGame.shortCastling)
            {
                all_moves=historyMoves.getText()+ " O-O ";
                chessGame.shortCastling=false;
            }

            else if (chessGame.longCastling)
            {
                all_moves=historyMoves.getText()+ " O-O-O ";
                chessGame.longCastling=false;
            }

            else
                all_moves=historyMoves.getText()+ " "+move_to_HM + " ";
            turns++;

        }
        historyMoves.setText(all_moves);

    }
}