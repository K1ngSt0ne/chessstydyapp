package com.example.chessstudyappver11;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.cardview.widget.CardView;

import org.w3c.dom.Attr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChessView extends View {

    private float scaleFactor = 0.9f;
    private float originX = 20f;
    private float originY = 200f;
    private float cellSide = 130f;
    private final String TAG = "ChessView";
    private int lightColor = Color.parseColor("#ede58a");
    private int darkColor = Color.parseColor("#a3610a");

    private HashMap<Integer, Bitmap> bitmaps = new HashMap<>();

    private Paint mPaint = new Paint();
    private int fromCol = -1;
    private int fromRow = -1;
    private  float movingPieceX = -1f;
    private float movingPieceY = -1;

    ChessDelegate mChessDelegate = null;
    private ChessPiece movingPiece = null;
    private Bitmap movingPieceBitmap = null;



    public ChessView(Context context, AttributeSet attributeSet){
        super(context, attributeSet);

    }

    {
        loadBitmaps();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int smaller = Math.min(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(smaller,smaller);
    }

    @Override
    protected void onDraw(Canvas canvas) {
       // super.onDraw(canvas);
        float chessBoardSide = Math.min(getWidth(), getHeight()) * scaleFactor;
        cellSide = chessBoardSide / 8f;
        originX = (getWidth() - chessBoardSide) /2f;
        originY = (getHeight() - chessBoardSide) /2f;
        drawChessboard(canvas);
        drawPieces(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event!=null) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                {
                    fromCol = (int)((event.getX() - originX) / cellSide);
                    fromRow = (int)(8 - ((event.getY() - originY) / cellSide));
                    Log.d(TAG, "onTouchEvent: down, col:" + fromCol + ", row: " + fromRow);
                    if (mChessDelegate!=null)
                    {
                        ChessPiece piece = mChessDelegate.pieceAt(new Square(fromCol,fromRow));
                        if (piece!=null)
                        {
                            movingPiece = piece;
                            movingPieceBitmap = bitmaps.get(piece.resID);
                            return true;
                        }
                        else
                            return false;
                    }

                }
                    case MotionEvent.ACTION_MOVE:
                    {
                        movingPieceX = event.getX();
                        movingPieceY = event.getY();
                        invalidate();
                        return true;
                    }

                    case MotionEvent.ACTION_UP:
                    {
                        int col = (int)((event.getX() - originX) / cellSide);
                        int row = (int)(8 - ((event.getY() - originY) / cellSide));
                        if (fromCol != col || fromRow != row) {
                            if (mChessDelegate!=null)
                            {
                                mChessDelegate.movePiece(new Square(fromCol,fromRow), new Square(col,row));
                            }
                        }
                        invalidate();
                        movingPiece =null;
                        movingPieceBitmap=null;
                        Log.d(TAG, "from " + fromCol + "," + fromRow + " to " + col + ","+ row);
                        return true;
                    }
                default:
                    return super.onTouchEvent(event);
                }

            }

        else
            return false;
    }

    private void drawPieces(Canvas canvas)
    {

        for (int i=0;i<8;i++)
        {
            for (int j=0;j<8;j++)
            {
                if (mChessDelegate!=null)
                {
                    ChessPiece piece = mChessDelegate.pieceAt(new Square(j,i));
                    if ((piece!=movingPiece)&&(piece!=null))
                    {
                       drawPieceAt(canvas, j,i,piece.resID);
                    }
                    if (movingPieceBitmap!=null)
                    {
                        canvas.drawBitmap(movingPieceBitmap, null, new RectF(movingPieceX - cellSide/2, movingPieceY - cellSide/2,movingPieceX + cellSide/2,movingPieceY + cellSide/2), mPaint);
                    }
                }

            }
        }

    }

    private void drawPieceAt(Canvas canvas, Integer column, Integer row, Integer resID)
    {
        Bitmap bitmap = bitmaps.get(resID);
        canvas.drawBitmap(bitmap, null, new RectF(originX + column * cellSide,originY + (7 - row) * cellSide,originX + (column + 1) * cellSide,originY + ((7 - row) + 1) * cellSide), mPaint);
    }

    private void loadBitmaps()
    {
        bitmaps.put(R.drawable.black_bishop, BitmapFactory.decodeResource(getResources(),R.drawable.black_bishop));
        bitmaps.put(R.drawable.white_bishop, BitmapFactory.decodeResource(getResources(),R.drawable.white_bishop));
        bitmaps.put(R.drawable.black_king, BitmapFactory.decodeResource(getResources(),R.drawable.black_king));
        bitmaps.put(R.drawable.white_king, BitmapFactory.decodeResource(getResources(),R.drawable.white_king));
        bitmaps.put(R.drawable.black_queen, BitmapFactory.decodeResource(getResources(),R.drawable.black_queen));
        bitmaps.put(R.drawable.white_queen, BitmapFactory.decodeResource(getResources(),R.drawable.white_queen));
        bitmaps.put(R.drawable.black_rook, BitmapFactory.decodeResource(getResources(),R.drawable.black_rook));
        bitmaps.put(R.drawable.white_rook, BitmapFactory.decodeResource(getResources(),R.drawable.white_rook));
        bitmaps.put(R.drawable.black_knight, BitmapFactory.decodeResource(getResources(),R.drawable.black_knight));
        bitmaps.put(R.drawable.white_knight, BitmapFactory.decodeResource(getResources(),R.drawable.white_knight));
        bitmaps.put(R.drawable.black_pawn, BitmapFactory.decodeResource(getResources(),R.drawable.black_pawn));
        bitmaps.put(R.drawable.white_pawn, BitmapFactory.decodeResource(getResources(),R.drawable.white_pawn));
    }

    private void drawChessboard(Canvas canvas) {
        for (int i=0; i<8; i++)
        {
            for (int j=0;j<8;j++)
            {
                drawSquareAt(canvas, j, i, (j + i) % 2 == 1);
            }
        }
    }

    private void drawSquareAt(Canvas canvas, Integer col, Integer row, Boolean isDark) {
        if (isDark)
            mPaint.setColor(darkColor);
        else
            mPaint.setColor(lightColor);
        canvas.drawRect(originX + col * cellSide, originY + row * cellSide, originX + (col + 1)* cellSide, originY + (row + 1) * cellSide, mPaint);
    }

}
