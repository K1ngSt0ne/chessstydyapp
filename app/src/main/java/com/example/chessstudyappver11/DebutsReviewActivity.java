package com.example.chessstudyappver11;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class DebutsReviewActivity extends AppCompatActivity implements ChessDelegate {
    ChessView mChessView;
    private final String TAG = "ChessGame";
    ChessGame chessGame = new ChessGame();
    TextView debutDescription;
    TextView debutName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_debuts_review);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        debutDescription = (TextView) findViewById(R.id.debutsDescription);
        debutDescription.setText("Испанская партия — один из самых популярных дебютов, применяемых в партиях гроссмейстеров." +
                " Изобретателем «Испанской партии» считается Руй Лопес (в зарубежной литературе данный дебют назван в его честь «дебютом Руй Лопеса»," +
                " исп. Ruy López), однако первое упоминание об этом дебюте встречается в руководстве испанского шахматиста XV—XVI веков Луиса Рамиреса де Лусены" +
                "\nИдея испанской партии заключается в постоянной угрозе взятия и/или связке чёрного коня с6 белым слоном, что в ряде вариантов может сделать чёрную пешку е5 уязвимой целью для белых. По оценкам разных шахматных движков," +
                " дебют является одним из наиболее перспективных для белых и сохраняет наибольший перевес (полученный от преимущества первого хода), в примерно 0.2 пешки. ");
        debutDescription.setMovementMethod(new ScrollingMovementMethod());
        debutName = (TextView) findViewById(R.id.textDebut);
        debutName.setText("Испанская партия");
        mChessView = findViewById(R.id.chess_debut_view);
        mChessView.mChessDelegate=this;
        animationMoves();
    }

    @Override
    public ChessPiece pieceAt(Square square) {
        return chessGame.pieceAt(square);
    }

    @Override
    public void movePiece(Square from, Square to) {
        chessGame.movePiece(from,to);
        mChessView = findViewById(R.id.chess_debut_view);
        mChessView.invalidate();
    }
    void animationMoves()
    {
        ArrayList<Moves> debut = initStartMoves();
        Timer t = new Timer();
        TimerTask task = new TimerTask(){
            int i=0;
            public void run() {
                chessGame.movePiece(debut.get(i).getSq_from(), debut.get(i).getSq_to());
                if(i==debut.size()-1)
                    t.cancel();
                i=i+1;
                mChessView.invalidate();
            }

        };
        t.scheduleAtFixedRate(task, 1000, 1000);

    }
    ArrayList<Moves> initStartMoves()
    {
        ArrayList<Moves> debutsMoves = new ArrayList<>();
        debutsMoves.add(new Moves(new Square(4,1), new Square(4,3)));
        debutsMoves.add(new Moves(new Square(4,6), new Square(4,4)));
        debutsMoves.add(new Moves(new Square(6,0), new Square(5,2)));
        debutsMoves.add(new Moves(new Square(1,7), new Square(2,5)));
        debutsMoves.add(new Moves(new Square(5,0), new Square(1,4)));
        return debutsMoves;
    }

    public void repeatDebuts(View v)
    {
        chessGame = new ChessGame();
        animationMoves();
    }

}