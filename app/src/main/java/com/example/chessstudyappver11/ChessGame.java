package com.example.chessstudyappver11;

import android.util.Log;

import androidx.annotation.NonNull;

import java.util.ArrayList;

import static java.lang.Math.abs;


public class ChessGame {



    private static final Player WHITE_PLAYER = Player.WHITE;
    private static final Player BLACK_PLAYER = Player.BLACK;
    private final String TAG = "ChessGame";
    private Player turnPlayer = Player.WHITE;
    private Player turnSecondBoardPlayer;
    boolean whiteKingCheck = false;
    boolean blackKingCheck = false;
    boolean endGame = false;

    private ArrayList<ChessPiece> piecesBox = new ArrayList<>();
    private ArrayList<ChessPiece> nextTurnPiecesBox = new ArrayList<>();


    {
        reset();
        // Log.d(TAG, String.valueOf(piecesBox.size()));

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



    private boolean isCheck(ArrayList<ChessPiece> figureList) {
        ChessPiece pieceKing = null;
        pieceKing = findEnemyKing(turnPlayer);
        if (checkAllFigure(turnPlayer,pieceKing, figureList))
            return true;
        return false;

    }

    private ChessPiece findEnemyKing(Player turn){
        ChessPiece pKing = null;
        for (ChessPiece piecePB : piecesBox) {
            //костыльная проверка на то, чей ход
            //если ход белых ищем черного короля
            //иначе ищем белого
            //добавить в отедлньый метод либо придумать как это сдлеа

            if (turn==Player.WHITE)
            {
                if ((piecePB.getChessman() == Chessman.KING)&&(piecePB.getPlayer()== Player.BLACK))
                    pKing = piecePB;
            }
            else
            {
                if ((piecePB.getChessman() == Chessman.KING)&&(piecePB.getPlayer()== Player.WHITE))
                    pKing = piecePB;
            }
        }
        return pKing;
    }

    private boolean checkAllFigure(Player turn, ChessPiece pieceKing, ArrayList<ChessPiece> figureList)
    {
        for (ChessPiece piece1: figureList)
        {
            if ((turn==WHITE_PLAYER)&&(piece1.getPlayer()==Player.WHITE))
            {
                if(checkIsCheck(piece1, pieceKing.getColumn(), pieceKing.getRow()))
                    return true;
            }
            else if ((turn==BLACK_PLAYER)&(piece1.getPlayer()==Player.BLACK))
            {
                if(checkIsCheck(piece1, pieceKing.getColumn(), pieceKing.getRow()))
                    return true;
            }
        }
        return false;
    }

    private boolean checkIsCheck(ChessPiece piece, int pieceKingColumn, int pieceKingRow)
    {
        if (piece.getChessman() == Chessman.BISHOP) {
            return isClearDiagonally(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow));
        }
        if (piece.getChessman() == Chessman.KHIGHT) {
            return canKnightMove(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow));
        }
        if (piece.getChessman() == Chessman.QUEEN) {
            return (isClearDiagonally(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow))
                    || (isClearVerticallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow)))
                    || (isClearHorizontallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow))));
        }
        if (piece.getChessman() == Chessman.ROOK) {
            return (isClearVerticallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow))) ||
                    (isClearHorizontallyBetween(new Square(piece.getColumn(), piece.getRow()), new Square(pieceKingColumn, pieceKingRow)));

        }
        if (piece.getChessman()==Chessman.PAWN)
            return canPawnMove(new Square(piece.getColumn(),piece.getRow()), new Square(pieceKingColumn, pieceKingRow));
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
    public Player switchPlayer(Player playerTurn) {
        if (playerTurn == Player.WHITE)
            playerTurn = Player.BLACK;
        else
            playerTurn = Player.WHITE;
        return playerTurn;
    }

    private boolean canMove(Square from, Square to) {
        //проверить на игрока и что его ход
        ChessPiece piecePlayer = pieceAt(from.getColumn(), from.getRow());
        ChessPiece kingPiece = pieceAt(to.getColumn(), to.getRow());
        if ((from.getColumn() == to.getColumn() && from.getRow() == to.getRow()) || (piecePlayer.getPlayer() != turnPlayer))
            return false;
        else {
            //Сделать копию доски с новым ходом, если белые дают шах, то запрещаем ход иначе меняем на копию
            if (kingPiece!=null)
            {
                if (kingPiece.getChessman()==Chessman.KING)
                    return false;
            }
            ChessPiece piece = pieceAt(from);


                switch (piece.getChessman()) {
                    case KING:
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

    private boolean secondBoard(ArrayList<ChessPiece> figureList)
    {
        turnSecondBoardPlayer=turnPlayer;
        turnSecondBoardPlayer=switchPlayer(turnSecondBoardPlayer);
            if (checkAllFigure(turnSecondBoardPlayer,findEnemyKing(turnSecondBoardPlayer),figureList))
                return true;
        return false;
    }



    void movePiece(Square from, Square to) {
        nextTurnPiecesBox=new ArrayList<ChessPiece>(piecesBox);
        if (canMove(from, to)) {
            movePiece(from.getColumn(), from.getRow(), to.getColumn(), to.getRow());
            if (secondBoard(piecesBox))
            {
                Log.d(TAG, "БАН Н! ТАК ХОДИТЬ НЕЛЬЗЯ!");
                movePiece(to.getColumn(),to.getRow(), from.getColumn(), from.getRow());
                piecesBox=new ArrayList<>(nextTurnPiecesBox);
            }
            // проверять надо на шахах
            else {
                if ((turnPlayer==WHITE_PLAYER)&&(isCheck(piecesBox)))
                {
                    if (checkMate(findEnemyKing(turnPlayer)))
                    {
                        Log.d(TAG, "ВАМ МАТ!");
                        endGame=true;
                    }

                    else
                    {
                        Log.d(TAG, "Black king need protect!");
                        blackKingCheck=true;
                    }

                }
                else if ((turnPlayer==BLACK_PLAYER)&&(isCheck(piecesBox)))
                {
                    if (checkMate(findEnemyKing(turnPlayer)))
                    {
                        Log.d(TAG, "ВАМ МАТ!");
                        endGame=true;
                    }

                    else
                    {
                        Log.d(TAG, "White king need protect!");
                        whiteKingCheck=true;
                    }

                }
                else {
                    blackKingCheck=false;
                    whiteKingCheck=false;
                }
                turnPlayer= switchPlayer(turnPlayer);
            }
        }
    }

    boolean checkMate(ChessPiece kingPiece) {

        if (kingPiece.getChessman() == Chessman.KING) {
            ArrayList<Pair> possibleMoves = new ArrayList<>();
            //найдем возможные ходы для короля и добавим их в массив
            for (int i = kingPiece.getRow() - 1; i <= kingPiece.getRow() + 1; i++) {
                for (int j = kingPiece.getColumn() - 1; j <= kingPiece.getColumn() + 1; j++) {
                    if ((i >= 0 && i < 8) && (j >= 0 && j < 8)) {
                        if (canKingMove(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(j, i)))
                            possibleMoves.add(new Pair(i, j));
                    }
                }
            }
            ArrayList<Pair> possibleMovesAfterCheck = new ArrayList<>();
            int moves = 0;
            //чекаем мат
            for (Pair pair : possibleMoves) {
                //сюда нам надо!
                ChessPiece pieceFree = pieceAt(pair.getColumn(), pair.getRow());
                if (pieceFree == null) //если пустая клетка проерит что она под боем
                {
                    for (ChessPiece piece : piecesBox) {
                        if ((kingPiece.getPlayer() == WHITE_PLAYER) && (piece.getPlayer() == BLACK_PLAYER)) {
                            if (checkIsCheck(piece, pair.getColumn(), pair.getRow()))
                                moves++;
                        } else if ((kingPiece.getPlayer() == BLACK_PLAYER) & (piece.getPlayer() == WHITE_PLAYER)) {
                            if (checkIsCheck(piece, pair.getColumn(), pair.getRow()))
                                moves++;
                        }
                    }
                }
                else {
                    int guard_pieces=0;
                    for (ChessPiece guardPiece : piecesBox)
                    {
                        //!!! ничегощеньки тут не работает!!!
                        if (kingPiece.getPlayer()==WHITE_PLAYER)
                            if(checkSquareWithPiece(WHITE_PLAYER, BLACK_PLAYER, kingPiece, guardPiece, pair))
                                guard_pieces++;

                        else if (kingPiece.getPlayer()==BLACK_PLAYER)
                            if(checkSquareWithPiece(BLACK_PLAYER, WHITE_PLAYER, kingPiece, guardPiece, pair))
                                guard_pieces++;

                    }
                    if (guard_pieces==0)
                    {
                        moves++;
                    }
                }

            }
            ChessPiece attackPiece = null;
            for (ChessPiece piece : piecesBox)
            {
                if (checkIsCheck(piece, kingPiece.getColumn(), kingPiece.getRow()))
                    attackPiece=piece;
            }
           // Log.d(TAG, attackPiece.getChessman().toString());

            if (moves == possibleMoves.size()) {


                if (!canProtectNearSquare(attackPiece, piecesBox))
                {
                    //надо проверять может ли защитить фигура с несолкьких клеток
                    Log.d(TAG, "ВАМ МАТ!");
                    return true;
                }

            }
            if (canProtectDistanceSquare(attackPiece, piecesBox, kingPiece))
                Log.d(TAG, "Воркь");
        }
        return false;
    }

    boolean canProtectDistanceSquare(ChessPiece attackPiece, ArrayList<ChessPiece> piecesBx, ChessPiece kingPiece)
    {
        ArrayList<Pair> pathAttackFigureToKing = new ArrayList<>();
        boolean canProtect = false;
        switch (attackPiece.getChessman()){
                    case ROOK:
                        for (int i = attackPiece.getRow()+1; i < kingPiece.getRow()-1; i++) {
                            for (int j = attackPiece.getColumn()+1; j < kingPiece.getColumn()-1; j++)
                            {
                                if(canRookMove(new Square(j, i), new Square(kingPiece.getColumn(),kingPiece.getRow())))
                                {
                                    pathAttackFigureToKing.add(new Pair(i,j));
                                }
                            }
                        }

                    case QUEEN:
                        for (int i = attackPiece.getRow()+1; i < kingPiece.getRow()-1; i++) {
                            for (int j = attackPiece.getColumn() + 1; j < kingPiece.getColumn() - 1; j++) {
                                if (canRookMove(new Square(j, i), new Square(kingPiece.getColumn(), kingPiece.getRow()))) {
                                    pathAttackFigureToKing.add(new Pair(i, j));
                                }
                                else if (canBishopMove(new Square(j, i), new Square(kingPiece.getColumn(),kingPiece.getRow())))
                                pathAttackFigureToKing.add(new Pair(i, j));
                            }
                        }
                    case BISHOP:
                        for (int i = attackPiece.getRow()+1; i < kingPiece.getRow()-1; i++) {
                            for (int j = attackPiece.getColumn()+1; j < kingPiece.getColumn()-1; j++)
                            {
                                if(canBishopMove(new Square(j, i), new Square(kingPiece.getColumn(),kingPiece.getRow())))
                                {
                                    pathAttackFigureToKing.add(new Pair(i,j));
                                }
                            }
                        }
                }


        //создать массив с парами
        //и проверять может ли какая нить фигура достичь этой пары
        return canProtect;
    }

    void findPathAttackFigure()
    {

    }



    boolean canProtectNearSquare(ChessPiece attackPiece, ArrayList<ChessPiece> piecesBx) {
        boolean canProtect = false;
        for (ChessPiece piece : piecesBx) {
            if (piece.getPlayer() != attackPiece.getPlayer()) {
                switch (piece.getChessman()) {
                    case BISHOP:
                        if (canBishopMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                    case PAWN:
                        if (canPawnMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                    case ROOK:
                        if (canRookMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                    case QUEEN:
                        if (canQueenMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                    case KHIGHT:
                        if (canKnightMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                }
            }

        }
        return canProtect;
    }

    boolean checkSquareWithPiece(Player p1, Player p2, ChessPiece kPiece, ChessPiece atkPiece, Pair pair_sq) {
        if ((kPiece.getPlayer() == p1) && (atkPiece.getPlayer() == p2)) {
            if (checkIsCheck(atkPiece, pair_sq.getColumn(), pair_sq.getRow())) {
                for (ChessPiece attackedPiece : piecesBox) {
                    if (attackedPiece.getPlayer() == p1) {
                        switch (attackedPiece.getChessman()) {
                            case BISHOP:
                                if (canBishopMove(new Square(attackedPiece.getColumn(), attackedPiece.getRow()), new Square(pair_sq.getColumn(), pair_sq.getRow())))
                                    return true;
                                break;
                            case PAWN:
                                if (canPawnMove(new Square(attackedPiece.getColumn(), attackedPiece.getRow()), new Square(pair_sq.getColumn(), pair_sq.getRow())))
                                    return true;
                                break;
                            case ROOK:
                                if (canRookMove(new Square(attackedPiece.getColumn(), attackedPiece.getRow()), new Square(pair_sq.getColumn(), pair_sq.getRow())))
                                    return true;
                                break;
                            case QUEEN:
                                if (canQueenMove(new Square(attackedPiece.getColumn(), attackedPiece.getRow()), new Square(pair_sq.getColumn(), pair_sq.getRow())))
                                    return true;
                                break;
                            case KHIGHT:
                                if (canKnightMove(new Square(attackedPiece.getColumn(), attackedPiece.getRow()), new Square(pair_sq.getColumn(), pair_sq.getRow())))
                                    return true;
                                break;
                        }
                    }
                }

            }
        }
        return false;
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

    public boolean isEndGame() {
        return endGame;
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }
}
