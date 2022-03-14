package com.example.chessstudyappver11;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Что еще необходимо сделать:
 * история ходов (частично есть)
 * запись в файл
 * взятие на проходе (опционально)
 * корректное отображение победившего/проигравшего (впринципе тоже) ! done!
 * парсер из нотации в позицию
 * сделать ничьи
 * */


public class ChessGame {
    private static final Player WHITE_PLAYER = Player.WHITE;
    private static final Player BLACK_PLAYER = Player.BLACK;
    private final String TAG = "ChessGame";
    private Player turnPlayer = Player.WHITE;
    private Player turnSecondBoardPlayer;
    private boolean whiteKingCheck = false;
    private boolean blackKingCheck = false;
    private boolean endGame = false;
    private boolean isDraw = false;
    private boolean whiteIsCastling = false;
    private boolean blackIsCastling = false;
    private boolean shortCastling = false;
    private boolean longCastling = false;
    private boolean isMoving= false;
    private boolean isTransforming = false;
    private boolean canMovingWhileCheck = false;
    private int movesWithoutTakingFigure = 0;
    private ArrayList<ChessPiece> piecesBox = new ArrayList<>();
    private ArrayList<ChessPiece> nextTurnPiecesBox = new ArrayList<>();


    {
        reset();
       /* movePiece(4,1,4,2);//e3
        movePiece(0,6,0,4);//a5
        movePiece(3,0,7,4);//Qh5
        movePiece(0,7,0,5);//Ra5
        movePiece(7,4,0,4);//Qxa5
        movePiece(7,6,7,4);//h5
        movePiece(0,4,2,6);//Qxc7
        movePiece(0,5,7,5);//Rah6
        movePiece(7,1,7,3);//h4
        movePiece(5,6,5,5);//f6
        movePiece(2,6,3,6);//Qxd7+
        movePiece(4,7,5,6);//Kf7
        movePiece(3,6,1,6);//Qxb7
        movePiece(3,7,3,2);//Qd3
        movePiece(1,6,1,7);//Qxb8
        movePiece(3,2,7,6);//Qh7
        movePiece(1,7,2,7);//Qxc8
        movePiece(5,6,6,5);//Kg6*/
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
        if (piece.getChessman() == Chessman.KNIGHT) {
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
        if (piece.getChessman()==Chessman.KING)
            return canKingMove(new Square(piece.getColumn(),piece.getRow()), new Square(pieceKingColumn, pieceKingRow));
        return false;
    }

    private boolean canKnightMove(Square from, Square to) {
        //Правило: либо на две клетки по вертикали (вверх вниз) + на одну клетку влево/вправо
        //либо на две клетки по горизонтали (влево вправо) и на одну клетку вверх/вниз
        return abs(from.getColumn() - to.getColumn()) == 2 && abs(from.getRow() - to.getRow()) == 1 ||
                abs(from.getColumn() - to.getColumn()) == 1 && abs(from.getRow() - to.getRow()) == 2;
    }

    private boolean canPawnMove(Square from, Square to) {
        //доделать взяте на проходе
        //запретить пешкам скакать друг через друга
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
                    if ((enemyPiece!=null)||((fromRow+2==toRow)&&(pieceAt(toColumn, toRow-1)!=null)))
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
                    if ((enemyPiece!=null)||((fromRow-2==toRow)&&(pieceAt(toColumn, toRow+1)!=null)))
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
        if ((piecePlayer.getPlayer()==turnPlayer)|| (canMovingWhileCheck))
        {
            //|| (piecePlayer.getPlayer() != turnPlayer)
            if ((from.getColumn() == to.getColumn() && from.getRow() == to.getRow()) ||(to.getRow()<0 || to.getRow()>8) || (to.getColumn()<0 || to.getColumn()>8))
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
                        if ((!whiteIsCastling)||(!blackIsCastling))
                        {
                            if ((piece.getPlayer()==Player.BLACK)&&(canCastling(piece, piecesBox, to)))
                                return true;
                            else if ((piece.getPlayer()==Player.WHITE)&&(canCastling(piece, piecesBox, to)))
                                return true;
                            else
                                return canKingMove(from, to);
                        }
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
                    case KNIGHT:
                        return canKnightMove(from, to);
                }
            }
        }
        else
            return false;

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
        setMoving(false);
        nextTurnPiecesBox=new ArrayList<ChessPiece>(piecesBox);
        if (canMove(from, to)) {
            setMoving(true);
            movePiece(from.getColumn(), from.getRow(), to.getColumn(), to.getRow());

            if (secondBoard(piecesBox))
            {
                Log.d(TAG, "ТАК ХОДИТЬ НЕЛЬЗЯ!");
                movePiece(to.getColumn(),to.getRow(), from.getColumn(), from.getRow());
                piecesBox=new ArrayList<>(nextTurnPiecesBox);

            }
            else {

                if (pieceAt(to).getChessman()==Chessman.PAWN)
                {
                    if (((pieceAt(to).getPlayer()==Player.WHITE)&&(to.getRow()==7))||(((pieceAt(to).getPlayer()==Player.BLACK)&&(to.getRow()==0))))
                    {
                        isTransforming=true;
                    }
                }

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

                    }
                    blackKingCheck=true;

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
                    }
                    whiteKingCheck=true;

                }
                else if (pieceAt(to).getChessman()==Chessman.PAWN)
                    {
                        if (((pieceAt(to).getPlayer()==Player.WHITE)&&(to.getRow()==7))||(((pieceAt(to).getPlayer()==Player.BLACK)&&(to.getRow()==0))))
                        {
                            isTransforming=true;
                        }
                    }

                else if (checkDraw())
                    isDraw=true;
                else
                {
                    blackKingCheck=false;
                    whiteKingCheck=false;
                }
;
                turnPlayer= switchPlayer(turnPlayer);
                canMovingWhileCheck=false;
            }
        }
        else
        {
            setMoving(false);
        }

    }


    private boolean checkDraw()
    {
        /*Ситуации, при которых возникает ничья:
        1. На доске 2 короля (готово)
        2. На доске 2 короля И слон (готово)
        3. На доске 2 короля И конь (готово)
        4. На доске 2 короля и 2  и более слона (если они одного цвета)
        5. На доске пат (когда одна сторона сделала ход, а другой нет возможных ходов)
        6. Правило 50 ходов - не взята ни одна фигура и не двинута ни одна пешка
        7. Троекратное повторение позиции (здесь если позиция была, и затем она повторилась два раза подряд)
        т.е.
        если
        Кре2 Кре7
        Кре1 Кре8
        ----какие-то ходы-------
        Кре2 Кре7
        Кре1 Кре8
        Кре2 Кре7
        Кре1 Кре8
        то это ничья!
        В независимоти от числа ходов!
        т.е. нужно искать по последней существующей позиции (ну т.е. если позиция появилась 1 раз, потом прошло n число ходов и она потворилась дважды, то тогда бан)
        * */
        if (pieceBoxSize()==2)
            return true;
        if (pieceBoxSize()==3)
        {
            for (ChessPiece piece : piecesBox)
            {
                if ((piece.getChessman()==Chessman.BISHOP)||(piece.getChessman()==Chessman.KNIGHT))
                    return true;
            }
        }
        if (movesWithoutTakingFigure==100) // нужно проврить что пешки не ходили и фигуры не брались
            return true;
        if (checkStalemate())
            return true;
        if (checkThreefoldMoves())
            return true;


        return false;
    }
    private boolean checkStalemate() //проверка на пат
    {
        //впринципе надо юзать методы которые я использвал для проверки мата - поиска возможноных клеток
        //ну разве что надо проверить не может ликак нибудь фигура ходить
        //return checkMate(findEnemyKing(turnPlayer)); //добавить проверку attackPiece на null + пешки пофиксить
        ChessPiece kingPiece = findEnemyKing(turnPlayer);
        if (kingPiece.getChessman() == Chessman.KING) {
            ArrayList<Pair> possibleMoves = new ArrayList<>();
            //найдем возможные ходы для короля и добавим их в массив
            for (int i = kingPiece.getRow() - 1; i <= kingPiece.getRow() + 1; i++) {
                for (int j = kingPiece.getColumn() - 1; j <= kingPiece.getColumn() + 1; j++) {
                    if ((i >= 0 && i <= 7) && (j >= 0 && j <= 7)) {
                        if (canKingMove(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(j, i)))
                            possibleMoves.add(new Pair(i, j));
                    }
                }
            }
            Log.d(TAG, "Число возможных ходов: " + possibleMoves.size());
            int moves = 0;
            int ally_figure=0;
            //чекаем пат
            for (Pair pair : possibleMoves) {
                //сюда нам надо!
                ChessPiece pieceFree = pieceAt(pair.getColumn(), pair.getRow());
                if (pieceFree == null) //если пустая клетка проерит что она под боем
                {
                    nextTurnPiecesBox=new ArrayList<ChessPiece>(piecesBox);
                    ChessPiece newKing = new ChessPiece(kingPiece.getColumn(), kingPiece.getRow(), kingPiece.getPlayer(), kingPiece.getChessman(), kingPiece.getResID());
                    piecesBox.remove(kingPiece);
                    newKing.setColumn(pair.getColumn());
                    newKing.setRow(pair.getRow());
                    piecesBox.add(newKing);
                    for (ChessPiece piece : piecesBox) {
                        if ((newKing.getPlayer() == WHITE_PLAYER) && (piece.getPlayer() == BLACK_PLAYER)) {
                            if (checkIsCheck(piece, newKing.getColumn(), newKing.getRow()))
                                moves++;
                        } else if ((newKing.getPlayer() == BLACK_PLAYER) & (piece.getPlayer() == WHITE_PLAYER)) {
                            if (checkIsCheck(piece, newKing.getColumn(), newKing.getRow()))
                                moves++;

                        }
                    }
                    piecesBox=new ArrayList<>(nextTurnPiecesBox);
                }
                else
                {
                    if (pieceFree.getPlayer()==kingPiece.getPlayer())
                       ally_figure++;
                }
            }
            Log.d(TAG, "Битые клетки: " + moves);
            Log.d(TAG, "Занятые клетки союзными фигурами " + ally_figure);
            if (moves+ally_figure==possibleMoves.size())
            {
                Log.d(TAG,"ПААААТ!!");
                return true;
            }
        }


        return false;
    }


    /*
    1. e4 Nf6 2. Qf3 e5 3. Bc4 g6 4. d3 a6 5. Bg5 Be7 6. Nc3 b5 7. Bb3 c6 8. Nge2 a5
9. a4 bxa4 10. Rxa4 Na6 11. Rc4 h6 12. Bh4 Nc5 13. Rxc5 Bxc5 14. Bxf6 Qb6 15.
Bxh8 Bxf2+ 16. Qxf2 Qxf2+ 17. Kxf2 Bb7 18. Bxe5 O-O-O 19. Ra1 f5 20. exf5 gxf5
21. Rxa5 d5 22. Ra8+ Bxa8 23. Ng3 d4 24. Nce2 c5 25. Nxf5 Re8 26. Bf4 Rf8 27.
Nxh6 Kb7 28. Bd5+ Ka7 29. Bxa8 Kxa8 30. Kg1 Kb7 31. Bd6 Rc8 32. b4 Kc6 33. Bxc5
Kb5 34. Nxd4+ Ka4 35. Nhf5 Ka3 36. h4 Kb2 37. h5 Kc1 38. h6 Kd2 39. g4 Kc3 40.
g5 Ra8 41. g6 Ra1+ 42. Kg2 Ra8 43. h7 Rh8 44. g7 Rxh7 45. g8=Q Rh5 46. Qc4+ Kd2
47. Nf3+ Ke2 48. Qe4+ Kd1 49. Qe1+ Kxc2 50. Qe2+ Kc3 51. Bd4+ Kxb4 52. Qb2+ Ka5
53. Qb6+ Ka4 54. Qc6+ Ka5 55. Qc5+ Ka4 56. Qc4+ Ka5 57. Qc5+ Ka4 58. Qc6+ Ka5
59. Bb6+ Kb4 60. Bc5+ Kc3 61. Ne1 Rxf5 62. Be3+ Kb4 63. Qd6+ Kb3 64. Qe6+ Kc3
65. Qxf5 Kb4 66. Qe4+ Kb5 67. Qc4+ Ka5 68. d4 Kb6 69. d5+ Kb7 70. d6 Kb8 71. d7
Kb7 72. d8=Q 1/2-1/2
*/






    private boolean checkThreefoldMoves() //требует доработки
    {
        return false;
    }


    private boolean canCastling(ChessPiece kingPiece, ArrayList<ChessPiece> piecesB, Square destination)
    {
        for (ChessPiece piece : piecesB)
        {
            if ((piece.getChessman()==Chessman.ROOK)&&(piece.getPlayer()==kingPiece.getPlayer()))
            {
                if (isClearHorizontallyBetween(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(piece.getColumn(), piece.getRow())))
                {
                    if ((destination.getColumn()==kingPiece.getColumn()+2)&&((kingPiece.getRow()==0)||(kingPiece.getRow()==7))&&(piece.getColumn()==7))
                    {
                        boolean notCastling=false;

                        for (int i= kingPiece.getColumn(); i<=destination.getColumn();i++)
                        {
                            for (ChessPiece piece1 : piecesB)
                            {
                                if (piece1.getPlayer()!=kingPiece.getPlayer())
                                {
                                    if (checkIsCheck(piece1, i,kingPiece.getRow()))
                                        notCastling=true;
                                }
                            }
                        }
                        if (!notCastling)
                        {
                            movePiece(piece.getColumn(), piece.getRow(), (piece.getColumn()-2), piece.getRow());
                            shortCastling=true;
                            if (kingPiece.getPlayer()==Player.WHITE)
                                whiteIsCastling=true;
                            else if (kingPiece.getPlayer()==Player.BLACK)
                                blackIsCastling=true;
                            return true;
                        }
                    }

                    else if ((destination.getColumn()==kingPiece.getColumn()-2)&&((kingPiece.getRow()==0)||(kingPiece.getRow()==7)&&(piece.getColumn()==0)))
                    {
                        boolean notCastling=false;
                        for (int i= kingPiece.getColumn(); i<=destination.getColumn();i++)
                        {
                            for (ChessPiece piece1 : piecesB) {
                                if (piece1.getPlayer() != kingPiece.getPlayer()) {
                                    if (checkIsCheck(piece1, i, kingPiece.getRow()))
                                        notCastling = true;
                                }
                            }
                        }
                        if (!notCastling)
                        {
                            movePiece(piece.getColumn(), piece.getRow(), (piece.getColumn()+3), piece.getRow());
                            longCastling=true;
                            if (kingPiece.getPlayer()==Player.WHITE)
                                whiteIsCastling=true;
                            else if (kingPiece.getPlayer()==Player.BLACK)
                                blackIsCastling=true;
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean checkMate(ChessPiece kingPiece) {

        if (kingPiece.getChessman() == Chessman.KING) {
            ArrayList<Pair> possibleMoves = new ArrayList<>();
            //найдем возможные ходы для короля и добавим их в массив
            for (int i = kingPiece.getRow() - 1; i <= kingPiece.getRow() + 1; i++) {
                for (int j = kingPiece.getColumn() - 1; j <= kingPiece.getColumn() + 1; j++) {
                    if ((i >= 0 && i <= 7) && (j >= 0 && j <= 7 )) {
                        if (canKingMove(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(j, i)))
                            possibleMoves.add(new Pair(i, j));
                    }
                }
            }
            ChessPiece attackPiece = null;
            for (ChessPiece piece : piecesBox)
            {
                if (checkIsCheck(piece, kingPiece.getColumn(), kingPiece.getRow()))
                    attackPiece=piece;
            }
            int moves = 0;
            //чекаем мат
            for (Pair pair : possibleMoves) {
                //сюда нам надо!
                ChessPiece pieceFree = pieceAt(pair.getColumn(), pair.getRow());
                if (pieceFree == null) //если пустая клетка проерит что она под боем
                {
                    nextTurnPiecesBox=new ArrayList<ChessPiece>(piecesBox);
                    ChessPiece newKing = new ChessPiece(kingPiece.getColumn(), kingPiece.getRow(), kingPiece.getPlayer(), kingPiece.getChessman(), kingPiece.getResID());
                    piecesBox.remove(kingPiece);
                    newKing.setColumn(pair.getColumn());
                    newKing.setRow(pair.getRow());
                    piecesBox.add(newKing);
                    for (ChessPiece piece : piecesBox) {
                        if ((newKing.getPlayer() == WHITE_PLAYER) && (piece.getPlayer() == BLACK_PLAYER)) {
                            if (checkIsCheck(piece, newKing.getColumn(), newKing.getRow()))
                                moves++;
                        } else if ((newKing.getPlayer() == BLACK_PLAYER) & (piece.getPlayer() == WHITE_PLAYER)) {
                            if (checkIsCheck(piece, newKing.getColumn(), newKing.getRow()))
                                moves++;

                        }
                    }
                    piecesBox=new ArrayList<>(nextTurnPiecesBox);
                }
                else {
                    if (attackPiece!=null) //если есть атакующая фигура
                    {
                        if ((pair.getColumn()==attackPiece.getColumn())&&(pair.getRow()==attackPiece.getRow()))
                        {
                            Log.d(TAG, "НУ тут можно и прихватить");

                            for (ChessPiece piece : piecesBox)
                            {
                                if (piece.getPlayer()==attackPiece.getPlayer())
                                {
                                    if (canMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                                    {
                                        moves++;
                                        break;
                                    }

                                }
                            }

                        }
                        else
                            moves++;
                    }

                }

            }
            //попытаться сделать две проверки - если обе проваливаются то мат, иначе не мат
            Log.d(TAG, "Moves: " + moves);
            Log.d(TAG, "Possible moves " + possibleMoves.size());
            if (moves == possibleMoves.size()) {

                if (kingPiece.getPlayer()==WHITE_PLAYER)
                {
                    if (!canProtectDistanceSquare(attackPiece, piecesBox, kingPiece, WHITE_PLAYER, BLACK_PLAYER))
                    {
                        if (!canProtectNearSquare(attackPiece, piecesBox))
                        {
                            if (!canMove(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            {
                                Log.d(TAG, "ВАМ МАТ!");
                                return true;
                            }
                            // нет проверки на то, что можно взять фигуру
                            //добавить сюда ифчик

                        }
                    }
                }
                if (kingPiece.getPlayer()==BLACK_PLAYER)
                {
                    if (!canProtectDistanceSquare(attackPiece, piecesBox, kingPiece, BLACK_PLAYER,WHITE_PLAYER))
                    {
                        Log.d(TAG, "Нельзя защиттить из далека");
                        if (!canProtectNearSquare(attackPiece, piecesBox))
                        {
                            //надо проверять может ли защитить фигура с несолкьких клеток
                            if (!canMove(new Square(kingPiece.getColumn(), kingPiece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            {
                                Log.d(TAG, "ВАМ МАТ!");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean canProtectDistanceSquare(ChessPiece attackPiece, ArrayList<ChessPiece> piecesBx, ChessPiece kingPiece, Player p1, Player p2)
    {
        //НУЖНО ЗАПИЛИТЬ ПРОВЕРКУ НА ПЕШКИ!!!
        ArrayList<Pair> pathAttackFigureToKing = new ArrayList<>();
        switch (attackPiece.getChessman()){
            case ROOK:
                if (canRookMove(new Square(attackPiece.getColumn(),attackPiece.getRow()), new Square(kingPiece.getColumn(), kingPiece.getRow())))
                {
                    findRookPath(kingPiece,attackPiece, pathAttackFigureToKing);
                }
                break;
            case QUEEN:
                //возможно можно будет в один метод вынести
                if (canRookMove(new Square(attackPiece.getColumn(),attackPiece.getRow()), new Square(kingPiece.getColumn(), kingPiece.getRow())))
                {
                    findRookPath(kingPiece,attackPiece, pathAttackFigureToKing);
                }
                else if (canBishopMove(new Square(attackPiece.getColumn(),attackPiece.getRow()), new Square(kingPiece.getColumn(), kingPiece.getRow())))
                {
                    findBishopPath(kingPiece, attackPiece, pathAttackFigureToKing);
                }
                break;

            case BISHOP:
                if (canBishopMove(new Square(attackPiece.getColumn(),attackPiece.getRow()), new Square(kingPiece.getColumn(), kingPiece.getRow())))
                {
                    findBishopPath(kingPiece,attackPiece, pathAttackFigureToKing);
                }
                break;
        }

        for (ChessPiece piece : piecesBx)
        {
            if ((kingPiece.getPlayer() == p1) && (attackPiece.getPlayer() == p2) && (piece.getPlayer()==p1) && (piece.getChessman()!=Chessman.KING)) {
                for (Pair pair : pathAttackFigureToKing) {
                    canMovingWhileCheck=true;
                    if (canMove(new Square(piece.getColumn(), piece.getRow()), new Square(pair.getColumn(), pair.getRow()))) {
                        return true;
                }
            }

            }
        }
        return false;
    }

    private void findRookPath(ChessPiece kingPiece, ChessPiece attackPiece, ArrayList<Pair> path)
    {
        if (attackPiece.getColumn().equals(kingPiece.getColumn()))//бьет вертикаль
        {
            if (attackPiece.getRow()>kingPiece.getRow())
            {
                for (int i=kingPiece.getRow()+1; i<attackPiece.getRow();i++)
                    path.add(new Pair(i, kingPiece.getColumn()));
            }
            else if (attackPiece.getRow()<kingPiece.getRow())
            {
                for (int i=attackPiece.getRow()+1; i<kingPiece.getRow();i++)
                    path.add(new Pair(i, kingPiece.getColumn()));
            }
        }
        else if (attackPiece.getRow().equals(kingPiece.getRow()))
        {
            if (attackPiece.getColumn()>kingPiece.getColumn())
            {
                for (int i=kingPiece.getColumn()+1; i<attackPiece.getColumn();i++)
                    path.add(new Pair(kingPiece.getRow(), i));
            }
            else if (attackPiece.getColumn()<kingPiece.getColumn())
            {
                for (int i=attackPiece.getColumn()+1; i<kingPiece.getColumn();i++)
                    path.add(new Pair(kingPiece.getRow(),i));
            }
        }
    }

    private void findBishopPath(ChessPiece kingPiece, ChessPiece attackPiece, ArrayList<Pair> path)
    {
        if (attackPiece.getColumn()>kingPiece.getColumn())
        {

            if (attackPiece.getRow()<kingPiece.getRow())
            {
                int row = attackPiece.getRow()+1;
                int column = attackPiece.getColumn()-1;
                while ((row<kingPiece.getRow())&&(column>kingPiece.getColumn()))
                {
                    path.add(new Pair(row,column));
                    row++;
                    column--;
                }
            }
            else if (attackPiece.getRow()>kingPiece.getRow())
            {
                int row = attackPiece.getRow()-1;
                int column = attackPiece.getColumn()-1;
                while ((row>kingPiece.getRow())&&(column>kingPiece.getColumn()))
                {
                    path.add(new Pair(row,column));
                    row--;
                    column--;
                }
            }
        }
        else if (attackPiece.getColumn()<kingPiece.getColumn())
        {

            if (attackPiece.getRow()<kingPiece.getRow())
            {
                int row = attackPiece.getRow()+1;
                int column = attackPiece.getColumn()+1;
                while ((row<kingPiece.getRow())&&(column<kingPiece.getColumn()))
                {
                    path.add(new Pair(row, column));
                    row++;
                    column++;
                }
            }
            else if (attackPiece.getRow()>kingPiece.getRow())
            {
                int row = attackPiece.getRow()-1;
                int column = attackPiece.getColumn()+1;
                while ((row>kingPiece.getRow())&&(column<kingPiece.getColumn()))
                {
                    path.add(new Pair(row, column));
                    row--;
                    column++;
                }
            }
        }
    }


    private boolean canProtectNearSquare(ChessPiece attackPiece, ArrayList<ChessPiece> piecesBx) {
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
                    case KNIGHT:
                        if (canKnightMove(new Square(piece.getColumn(), piece.getRow()), new Square(attackPiece.getColumn(), attackPiece.getRow())))
                            canProtect = true;
                        break;
                }
            }

        }
        return canProtect;
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

            addPiece(new ChessPiece(1 + i * 5, 0, Player.WHITE, Chessman.KNIGHT, R.drawable.white_knight));
            addPiece(new ChessPiece(1 + i * 5, 7, Player.BLACK, Chessman.KNIGHT, R.drawable.black_knight));

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

    public int pieceBoxSize()
    {
        return piecesBox.size();
    }

    public Player getTurnPlayer() {
        return turnPlayer;
    }

    public void setTurnPlayer(Player turnPlayer) {
        this.turnPlayer = turnPlayer;
    }

    public boolean isMoving() {
        return isMoving;
    }

    public void setMoving(boolean moving) {
        isMoving = moving;
    }

    public boolean isWhiteKingCheck() {
        return whiteKingCheck;
    }

    public void setWhiteKingCheck(boolean whiteKingCheck) {
        this.whiteKingCheck = whiteKingCheck;
    }

    public boolean isBlackKingCheck() {
        return blackKingCheck;
    }

    public void setBlackKingCheck(boolean blackKingCheck) {
        this.blackKingCheck = blackKingCheck;
    }

    public boolean isShortCastling() {
        return shortCastling;
    }

    public void setShortCastling(boolean shortCastling) {
        this.shortCastling = shortCastling;
    }

    public boolean isLongCastling() {
        return longCastling;
    }

    public void setLongCastling(boolean longCastling) {
        this.longCastling = longCastling;
    }

    public boolean isTransforming() {
        return isTransforming;
    }

    public void setTransforming(boolean transforming) {
        isTransforming = transforming;
    }

    public boolean isCanMovingWhileCheck() {
        return canMovingWhileCheck;
    }

    public void setCanMovingWhileCheck(boolean canMovingWhileCheck) {
        this.canMovingWhileCheck = canMovingWhileCheck;
    }

    public boolean isWhiteIsCastling() {
        return whiteIsCastling;
    }

    public void setWhiteIsCastling(boolean whiteIsCastling) {
        this.whiteIsCastling = whiteIsCastling;
    }

    public boolean isBlackIsCastling() {
        return blackIsCastling;
    }

    public void setBlackIsCastling(boolean blackIsCastling) {
        this.blackIsCastling = blackIsCastling;
    }

    public ArrayList<ChessPiece> getPiecesBox() {
        return piecesBox;
    }

    public void setPiecesBox(ArrayList<ChessPiece> piecesBox) {
        this.piecesBox = piecesBox;
    }

    public boolean isDraw() {
        return isDraw;
    }

    public void setDraw(boolean draw) {
        isDraw = draw;
    }
}

