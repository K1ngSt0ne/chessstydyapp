package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
/**!!!1 АЛЕРТ !! НЕ ЗАБУДЬ СДЕЛАТЬ ПРОВЕРКУ НА ШАХ ИЛИ МАТ ПОСЛЕ ПРЕВРАЩЕНИЯ ФИГУРЫ!!!*/
public class PlayBoardActivity extends AppCompatActivity implements ChessDelegate, GettingDataFromDialog{

    ChessView mChessView;
    private final String TAG = "ChessGame";
    ChessGame chessGame = new ChessGame();
    TextView statusBar;
    TextView historyMoves;
    int turns=1;
    ArrayList<Square> movesHistory = new ArrayList<>(); //вопрос, хранить в таком виде или все таки распарсить во что-то другое
    //скорее всего сделаем этот лист непосредственно в игре
    private HashMap<String, Chessman> chessman_type= new HashMap<>();
    private HashMap<String, Integer> white_resID = new HashMap<>();
    private HashMap<String, Integer> black_resID = new HashMap<>();
    Square destination_square;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        SharedPreferences settings = getSharedPreferences("ChessDate", MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = settings.edit();
        prefEditor.putString("lastActivity","playActivity");
        prefEditor.apply();
        historyMoves = findViewById(R.id.historyMoves);
        historyMoves.setMovementMethod(new ScrollingMovementMethod());
        mChessView = findViewById(R.id.chess_view);
        mChessView.mChessDelegate=this;
        init_chessman_type();
    }


    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
            int start_size = chessGame.pieceBoxSize();
            ChessPiece piece_type = pieceAt(from);
            destination_square=new Square(to.getColumn(),to.getRow());
            chessGame.movePiece(from,to);
            int end_size = chessGame.pieceBoxSize();
            if (chessGame.isMoving())
            {
                movesHistory.add(to);
                historyMovesShow(to, start_size,end_size, piece_type);
                if (chessGame.isTransforming())
                {
                    FragmentManager manager = getSupportFragmentManager();
                    FigureTransformDialog dialogTransform = new FigureTransformDialog(this);
                    dialogTransform.show(manager, "transform");
                }
            }
            if (chessGame.isEndGame())
            {
                String message_text="";
                if (chessGame.isBlackKingCheck())
                {
                    message_text="Белые выиграли (объявлен мат)";
                    historyMoves.setText(historyMoves.getText()+" 1-0 ");
                }

                if (chessGame.isWhiteKingCheck())
                {
                    message_text="Черные выиграли (объявлен мат)";
                    historyMoves.setText(historyMoves.getText()+" 0-1 ");
                }

                FragmentManager manager = getSupportFragmentManager();
                ResultShow myDialogFragment = new ResultShow("Партия закончена",message_text, this, "В меню", "На доску");
                myDialogFragment.show(manager, "myDialog");
            }
            if (chessGame.isDraw())
            {
                historyMoves.setText(historyMoves.getText()+" 1/2-1/2 ");
                String message_text="";
                if (chessGame.isFifteenthResult())
                    message_text="Правило 50 ходов";
                if (chessGame.isDisadvantageResult())
                    message_text="На доске осталось недостаточно фигур!";
                if (chessGame.isStalemateResult())
                    message_text="На доске пат!";
                if (chessGame.isThreefoldMovesResult())
                    message_text="Троекратное повторение ходов!";
                FragmentManager manager = getSupportFragmentManager();
                ResultShow myDialogFragment = new ResultShow("Партия закончена",message_text, this, "В меню", "На доску");
                myDialogFragment.show(manager, "myDialog");
            }
            else
            {
                mChessView = findViewById(R.id.chess_view);
                mChessView.invalidate();
            }
    }

    public void historyMovesShow(Square destination, int start_size, int end_size, ChessPiece piece)
    {
        ParseSquareToNotationMoves move = new ParseSquareToNotationMoves(destination.getColumn(),destination.getRow(), start_size,end_size, piece.getChessman(), piece.getPlayer());
        String move_to_HM=move.convertSquareToNotation(move);
        String all_moves="";
        //т.к. мы сходили, у нас поменялась очередность хода, поэтому в данный момент ход черных, отсюда и проверка
        //(но на самом деле это для белых)
        if (chessGame.getTurnPlayer()==Player.BLACK)
        {
            if (chessGame.isEndGame())
                all_moves=historyMoves.getText()+ " "+ turns+move_to_HM+"#";
            else if (chessGame.isBlackKingCheck())
                all_moves=historyMoves.getText()+ " "+ turns+move_to_HM+"+";
            else if ((chessGame.isShortCastling())&&(chessGame.isWhiteIsCastling()))
            {
                all_moves=historyMoves.getText()+ "     "+ turns+" O-O ";
                chessGame.setShortCastling(false);

            }
            else if ((chessGame.isLongCastling())&&(chessGame.isWhiteIsCastling()))
            {
                all_moves=historyMoves.getText() +  "" + turns +" O-O-O     ";
                chessGame.setLongCastling(false);

            }
            else
                all_moves=historyMoves.getText()+ "     "+ turns+move_to_HM + "     ";
        }
        //(а это для черных)

        else
        {
            if (chessGame.isEndGame())
                all_moves=historyMoves.getText()+ " "+move_to_HM + "#";
            else if (chessGame.isWhiteKingCheck())
                all_moves=historyMoves.getText()+ " "+move_to_HM + "+";
            else if ((chessGame.isShortCastling())&&(chessGame.isBlackIsCastling()))
            {
                all_moves=historyMoves.getText()+ " O-O \n";
                chessGame.setShortCastling(false);
                //chessGame.setCastling(false);
            }
            else if ((chessGame.isLongCastling())&&(chessGame.isBlackIsCastling()))
            {
                all_moves=historyMoves.getText()+ " O-O-O \n";
                chessGame.setLongCastling(false);
               // chessGame.setCastling(false);
            }

            else
                all_moves=historyMoves.getText()+ "     "+move_to_HM + "    \n";
            turns++;

        }
        historyMoves.setText(all_moves);

    }

    @Override
    public void getData(String text) {
       Log.d(TAG, "ВЫбрал фигуру: " + text);

        ArrayList<ChessPiece> newPieceBox = chessGame.getPiecesBox();
        ChessPiece newFigure = pieceAt(destination_square);
        newPieceBox.remove(newFigure);
        Chessman newChessman=chessman_type.get(text);
        ChessPiece newChosenPiece = null;
        if (chessGame.getTurnPlayer()==Player.WHITE)
        {
            Integer resID = black_resID.get(text);
            newChosenPiece = new ChessPiece(newFigure.getColumn(), newFigure.getRow(), Player.BLACK,newChessman, resID);
        }
        else if (chessGame.getTurnPlayer()==Player.BLACK)
        {
            Integer resID = white_resID.get(text);
            newChosenPiece = new ChessPiece(newFigure.getColumn(), newFigure.getRow(),Player.WHITE, newChessman, resID);
        }
        newPieceBox.add(newChosenPiece);
        chessGame.setPiecesBox(newPieceBox);
        chessGame.setTransforming(false);
        mChessView.invalidate();
    }

    void init_chessman_type()
    {
        chessman_type.put("Ферзь", Chessman.QUEEN);
        chessman_type.put("Ладья", Chessman.ROOK);
        chessman_type.put("Слон", Chessman.BISHOP);
        chessman_type.put("Конь", Chessman.KNIGHT);
        black_resID.put("Слон", R.drawable.black_bishop);
        black_resID.put("Конь", R.drawable.black_knight);
        black_resID.put("Ладья",R.drawable.black_rook);
        black_resID.put("Ферзь", R.drawable.black_queen);
        white_resID.put("Слон", R.drawable.white_bishop);
        white_resID.put("Конь", R.drawable.white_knight);
        white_resID.put("Ладья",R.drawable.white_rook);
        white_resID.put("Ферзь", R.drawable.white_queen);

    }
}