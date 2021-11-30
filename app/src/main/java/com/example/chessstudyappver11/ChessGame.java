package com.example.chessstudyappver11;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class ChessGame {

    //проверка на мат: перебирать всес фигуры противника
    //ссылка: https://stackoverflow.com/questions/21622677/computing-checkmate-correctly

    private static final Player WHITE_PLAYER = Player.WHITE;
    private static final Player BLACK_PLAYER = Player.BLACK;
    private final String TAG = "ChessGame";
    private Player turnPlayer = Player.WHITE;
    boolean isCheckKing = false;

    private ArrayList<ChessPiece> piecesBox = new ArrayList<>();
    private ArrayList<ChessPiece> takingPiecesBox = new ArrayList<>();
    {
        reset();
        // Log.d(TAG, String.valueOf(piecesBox.size()));
        // movePiece(0, 0, 1, 6);
        //  movePiece(1,7,1,4);

    }

    private void clear() {
        piecesBox.clear();
    }

    private void addPiece(ChessPiece piece) {
        piecesBox.add(piece);
    }




    private boolean isClearVerticallyBetween(Square from, Square to) {
        //нужно починить
        if (from.getColumn() != to.getColumn()) return false;
        int gap = abs(from.getRow() - to.getRow());
        if (gap == 0) return true;
        for (int i = 1; i < gap; i++) {
            int nextRow;
            if (to.getRow() > from.getRow())
                nextRow = from.getRow() + i;
            else
                nextRow = from.getRow() - i;
            if (pieceAt(new Square(from.getColumn(), nextRow)) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isClearHorizontallyBetween(Square from, Square to) {
        //нужно починить
        if (from.getRow() != to.getRow()) return false;
        int gap = abs(from.getColumn() - to.getColumn());
        if (gap == 0) return true;
        for (int i = 1; i < gap; i++) {
            int nextCol;
            if (to.getColumn() > from.getColumn())
                nextCol = from.getColumn() + i;
            else
                nextCol = from.getColumn() - i;
            if (pieceAt(new Square(nextCol, from.getRow())) != null) {
                return false;
            }
        }
        return true;
    }

    private boolean isClearDiagonally(Square from, Square to) {
        if (abs(from.getColumn() - to.getColumn()) != abs(from.getRow() - to.getRow()))
            return false;
        int gap = abs(from.getColumn() - to.getColumn());
        for (int i = 1; i < gap; i++) {
            int nextCol;
            if (to.getColumn() > from.getColumn())
                nextCol = from.getColumn() + i;
            else
                nextCol = from.getColumn() - i;
            int nextRow;
            if (to.getRow() > from.getRow())
                nextRow = from.getRow() + i;
            else
                nextRow = from.getRow() - i;
            if (pieceAt(nextCol, nextRow) != null) {
                return false;
            }
        }

        return true;
    }



    private boolean isCheck(ChessPiece piece) {
        ChessPiece pieceKing = null;
        for (ChessPiece piecePB : piecesBox) {
            //костыльная проверка на то, чей ход
            //если ход белых ищем черного короля
            //иначе ищем белого
            //добавить в отедлньый метод либо придумать как это сдлеа

            if (turnPlayer==Player.WHITE)
            {
                if ((piecePB.getChessman() == Chessman.KING)&&(piecePB.getPlayer()== Player.BLACK))
                    pieceKing = piecePB;
            }
            else
            {
                if ((piecePB.getChessman() == Chessman.KING)&&(piecePB.getPlayer()== Player.WHITE))
                    pieceKing = piecePB;
            }
        }
        if (piece.getChessman() == Chessman.BISHOP) {
            return isClearDiagonally(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow()));
        }
        if (piece.getChessman() == Chessman.KHIGHT) {
            return canKnightMove(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow()));
        }
        if (piece.getChessman() == Chessman.QUEEN) {
            return (isClearDiagonally(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow()))
                    || (isClearVerticallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow())))
                    || (isClearHorizontallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow()))));
        }
        if (piece.getChessman() == Chessman.ROOK) {
            //на клетку назначения! а не отправления
            //Log.d(TAG, "Check!!");
            return (isClearVerticallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow()))) ||
                    (isClearHorizontallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKing.getColumn(), pieceKing.getRow())));

        }
        if (piece.getChessman()==Chessman.PAWN)
            return canPawnMove(new Square(piece.getColumn(),piece.getRow()), new Square(pieceKing.getColumn(),pieceKing.getRow()));
        return false;
    }

    private boolean isCheckMate(ChessPiece piece)
    {

        return false;
    }
    private boolean canProtect()
    {

        return false;
    }

    private boolean canKnightMove(Square from, Square to) {
        //Правило: либо на две клетки по вертикали (вверх вниз) + на одну клетку влево/вправо
        //либо на две клетки по горизонтали (влево вправо) и на одну клетку вверх/вниз
        return abs(from.getColumn() - to.getColumn()) == 2 && abs(from.getRow() - to.getRow()) == 1 ||
                abs(from.getColumn() - to.getColumn()) == 1 && abs(from.getRow() - to.getRow()) == 2;
    }


    private boolean canPawnMove(Square from, Square to) {

        ChessPiece piecePlayer = pieceAt(from);
        ChessPiece enemyPiece = pieceAt(to);
        if (piecePlayer!=null)
        {
            int fromRow = from.getRow();
            int toRow = to.getRow();
            int fromColumn = from.getColumn();
            int toColumn = to.getColumn();
            if (piecePlayer.getPlayer()==WHITE_PLAYER)
            {
                if (from.getColumn()==to.getColumn())
                {
                    if (enemyPiece!=null)
                    {
                        return false;
                    }
                    else
                    {
                        Log.d(TAG, "Move");
                        if (fromRow < 3) {
                            return toRow == 2 || toRow == 3;
                        } else
                            return toRow == fromRow + 1;
                    }
                }
                else
                {
                    if (enemyPiece==null)
                        return false;
                    else
                        {
                           if ((fromColumn==toColumn+1)&&(fromRow+1==toRow))
                           {
                               return toColumn == fromColumn - 1 && toRow==fromRow+1;
                           }
                           else if ((fromColumn==toColumn-1)&&(fromRow+1==toRow))
                               return toColumn == fromColumn+1 && toRow==fromRow+1;
                        }
                }

            }
            else if (piecePlayer.getPlayer()==BLACK_PLAYER)
            {
                if (from.getColumn()==to.getColumn())
                {
                    if (enemyPiece!=null)
                    {
                        return false;
                    }
                    else
                    {
                        Log.d(TAG, "Move");
                        if (fromRow > 5) {
                            return toRow == 5 || toRow == 4;
                        } else
                            return toRow == fromRow - 1;
                    }
                }
                else
                {
                    if (enemyPiece==null)
                        return false;
                    else
                    {
                        if ((fromColumn==toColumn+1)&&(fromRow-1==toRow))
                        {
                            return toColumn == fromColumn - 1 && toRow==fromRow-1;
                        }
                        else if ((fromColumn==toColumn-1)&&(fromRow-1==toRow))
                            return toColumn == fromColumn+1 && toRow==fromRow-1;
                    }
                }
            }
        }
        return false;
    }

    private boolean canRookMove(Square from, Square to) {
        //подфиксить движение по горизонтали до упора
        if (from.getColumn() == to.getColumn() && isClearVerticallyBetween(from, to) ||
                from.getRow() == to.getRow() && isClearHorizontallyBetween(from, to)) {
            return true;
        }
        return false;
    }

    private boolean canBishopMove(Square from, Square to) {
        //подфиксить движение по диагонали
        if (abs(from.getColumn() - to.getColumn()) == abs(from.getRow() - to.getRow())) {
            return isClearDiagonally(from, to);
        }
        return false;
    }

    private boolean canQueenMove(Square from, Square to) {
        return canRookMove(from, to) || canBishopMove(from, to);
    }

    private boolean canKingMove(Square from, Square to) {
        if (canQueenMove(from, to)) {
            int deltaCol = abs(from.getColumn() - to.getColumn());
            int deltaRow = abs(from.getRow() - to.getRow());
            return deltaCol == 1 && deltaRow == 1 || deltaCol + deltaRow == 1;
        }
        return false;
    }

    //переключение игрока
    public void switchPlayer() {
        if (turnPlayer == Player.WHITE)
            turnPlayer = Player.BLACK;
        else
            turnPlayer = Player.WHITE;
    }

    private boolean canMove(Square from, Square to) {
        //проверить на игрока и что его ход
        ChessPiece piecePlayer = pieceAt(from.getColumn(), from.getRow());
        ChessPiece kingPiece = pieceAt(to.getColumn(), to.getRow());

        if ((from.getColumn() == to.getColumn() && from.getRow() == to.getRow()) || (piecePlayer.getPlayer() != turnPlayer))
            return false;
        else {
            if (kingPiece!=null)
            {
                if (kingPiece.getChessman()==Chessman.KING)
                    return false;
            }
            ChessPiece piece = pieceAt(from);
            switch (piece.getChessman()) {
                case KING:
                    if (isCheckKing)
                        return false;
                    else
                        return canKingMove(from, to);
                case PAWN:
                    return canPawnMove(from, to);
                case ROOK:
                    return canRookMove(from, to);
                case QUEEN:
                    return canQueenMove(from, to);
                case BISHOP:
                    return canBishopMove(from, to);
                case KHIGHT:
                    return canKnightMove(from, to);


            }
        }

        return true;
    }

    void movePiece(Square from, Square to) {
        //добавить проверку что фигура может ходить (через canMove)
        if (canMove(from, to)) {
            movePiece(from.getColumn(), from.getRow(), to.getColumn(), to.getRow());
            ChessPiece testpiece = pieceAt(to);
            if (testpiece != null) {
                if (isCheck(testpiece))
                {
                    isCheckKing = true;
                    //здесь добавить проверку на мат
                    Log.d(TAG, "Check!!");
                }
                else
                    isCheckKing=false;
            }
            switchPlayer();

        }

    }

    private void movePiece(Integer fromCol, Integer fromRow, Integer toCol, Integer toRow) {

        ChessPiece movingPiece = pieceAt(fromCol, fromRow);
        if (movingPiece != null) {
            ChessPiece testPiece = pieceAt(toCol, toRow);

            if (testPiece != null) {
                if (testPiece.getPlayer() == movingPiece.getPlayer()) {
                    return;
                }
                piecesBox.remove(testPiece);

            }
        }

        piecesBox.remove(movingPiece);
        movingPiece.setColumn(toCol);
        movingPiece.setRow(toRow);
        addPiece(movingPiece);
        Log.d(TAG, String.valueOf(piecesBox.size()));
    }


    private void reset() {
        clear();
        for (int i = 0; i < 2; i++) {
            addPiece(new ChessPiece(0 + i * 7, 0, Player.WHITE, Chessman.ROOK, R.drawable.white_rook));
            addPiece(new ChessPiece(0 + i * 7, 7, Player.BLACK, Chessman.ROOK, R.drawable.black_rook));

            addPiece(new ChessPiece(1 + i * 5, 0, Player.WHITE, Chessman.KHIGHT, R.drawable.white_knight));
            addPiece(new ChessPiece(1 + i * 5, 7, Player.BLACK, Chessman.KHIGHT, R.drawable.black_knight));

            addPiece(new ChessPiece(2 + i * 3, 0, Player.WHITE, Chessman.BISHOP, R.drawable.white_bishop));
            addPiece(new ChessPiece(2 + i * 3, 7, Player.BLACK, Chessman.BISHOP, R.drawable.black_bishop));
        }

        for (int i = 0; i < 8; i++) {
            addPiece(new ChessPiece(i, 1, Player.WHITE, Chessman.PAWN, R.drawable.white_pawn));
            addPiece(new ChessPiece(i, 6, Player.BLACK, Chessman.PAWN, R.drawable.black_pawn));
        }

        addPiece(new ChessPiece(3, 0, Player.WHITE, Chessman.QUEEN, R.drawable.white_queen));
        addPiece(new ChessPiece(3, 7, Player.BLACK, Chessman.QUEEN, R.drawable.black_queen));
        addPiece(new ChessPiece(4, 0, Player.WHITE, Chessman.KING, R.drawable.white_king));
        addPiece(new ChessPiece(4, 7, Player.BLACK, Chessman.KING, R.drawable.black_king));
    }

    ChessPiece pieceAt(Square square) {
        return pieceAt(square.getColumn(), square.getRow());
    }

    ChessPiece pieceAt(int column, int row) {
        for (ChessPiece piece : piecesBox) {
            if (column == piece.column && row == piece.row) {
                return piece;
            }
        }
        return null;
    }


}
