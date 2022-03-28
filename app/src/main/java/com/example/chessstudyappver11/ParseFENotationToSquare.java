package com.example.chessstudyappver11;

import java.util.ArrayList;
import java.util.HashMap;

//ДАнный класс необходим нам для того, что бы из полученной из json файла FEN-строки выставить на доске фигуры

public class ParseFENotationToSquare {
    private String fen_string; //наша строка с позицией
    private Player queue_turn; //количество ходов, за которое нужно поставить мат. Здесь счет идет общий (т.е. 1 полный ход = 2 хода )
    //далее идут словари что бы из них брать информацию о фигуре, игроке, и изображении фигуры
    private HashMap<String,Chessman> white_figure_list = new HashMap<>();
    private HashMap<String,Chessman> black_figure_list = new HashMap<>();
    private HashMap<String, Player> playerList = new HashMap<>();
    private HashMap<String, Integer> image_list = new HashMap<>();
    //большими буквамми обозначаются белые фигуры, маленькими - черные
    // KQNRBP и kqnrbp
    //идем с 8 горизонтали до первой, слева направо
    // читать нужно будет до символа w или b (ловушка со слоном черных) хотя можно и изловчиться проверить что идет пробел и потом символы


    public ParseFENotationToSquare(String fen_string) {
        this.fen_string = fen_string;
    }

    {
        //загружаем в словари данные
        initFigureList();
        initImageList();
        initPlayer();
    }

    public String getFen_string() {
        return fen_string;
    }

    public void setFen_string(String fen_string) {
        this.fen_string = fen_string;
    }

    public Player getQueue_turn() {
        return queue_turn;
    }

    public void setQueue_turn(Player queue_turn) {
        this.queue_turn = queue_turn;
    }
//методы где добавляеются данные в словарь
    void initFigureList()
    {
        white_figure_list.put("K", Chessman.KING);
        white_figure_list.put("Q", Chessman.QUEEN);
        white_figure_list.put("R", Chessman.ROOK);
        white_figure_list.put("N", Chessman.KNIGHT);
        white_figure_list.put("B", Chessman.BISHOP);
        white_figure_list.put("P", Chessman.PAWN);
        black_figure_list.put("k", Chessman.KING);
        black_figure_list.put("q", Chessman.QUEEN);
        black_figure_list.put("r", Chessman.ROOK);
        black_figure_list.put("n", Chessman.KNIGHT);
        black_figure_list.put("b", Chessman.BISHOP);
        black_figure_list.put("p", Chessman.PAWN);
    }

    void initImageList()
    {
        image_list.put("K", R.drawable.white_king);
        image_list.put("Q", R.drawable.white_queen);
        image_list.put("R", R.drawable.white_rook);
        image_list.put("N", R.drawable.white_knight);
        image_list.put("B", R.drawable.white_bishop);
        image_list.put("P", R.drawable.white_pawn);
        image_list.put("k", R.drawable.black_king);
        image_list.put("q", R.drawable.black_queen);
        image_list.put("r", R.drawable.black_rook);
        image_list.put("n", R.drawable.black_knight);
        image_list.put("b", R.drawable.black_bishop);
        image_list.put("p", R.drawable.black_pawn);
    }

    void initPlayer()
    {
        playerList.put("K", Player.WHITE);
        playerList.put("Q", Player.WHITE);
        playerList.put("R", Player.WHITE);
        playerList.put("N", Player.WHITE);
        playerList.put("B",Player.WHITE);
        playerList.put("P", Player.WHITE);
        playerList.put("k", Player.BLACK);
        playerList.put("q", Player.BLACK);
        playerList.put("r", Player.BLACK);
        playerList.put("n", Player.BLACK);
        playerList.put("b", Player.BLACK);
        playerList.put("p", Player.BLACK);
    }
    //метод, собственно на котором все завязано - в нем мы получаем массив с фигурами из FEN строки
    public ArrayList<ChessPiece> loadPositionsOnBoard()
    {
        ArrayList<ChessPiece> chessPieces = new ArrayList<>();//создаем массив с фигурами
        String[] fen = fen_string.split(" ");//сначала делим строку по пробелам
        String position = fen[0];
        String queue_move = fen[1];
        if (queue_move.equals("w")) //устанавливаем очередность хода
            queue_turn=Player.WHITE;
        else
            queue_turn=Player.BLACK;
        String[] rows = position.split("/"); //а уже потом - по обратным слэшам, т.к. иначе все накроется
        int row = 7; //поскольку в игровой логике счет идет не с 1 до 8, а с 0 до 7, то и число будет 7 (мы же идем сверху вниз)
        for (String str : rows) //перебираем элемент массива строк (всего 8 элементов - 8 рядов)
        {
            char[] char_row = str.toCharArray(); //уже конкретный элемент разбиваем на символы, для того чтобы загружать фигуры в массив
            int column = 0; //начинаем идти слева направо
            if (row>=0) //и пока мы на доске
            {
                for (char sym : char_row) //поэлементно проходим строку
                {
                    if (Character.isDigit(sym)) //если символ цифра, то нам нужно прибавть к колонке это число
                        column=column+Character.getNumericValue(sym);
                    else //а если буква, то уже загружаем фигуру в массив с фигурами
                    {
                        if (Character.isLowerCase(sym))
                            chessPieces.add(new ChessPiece(column,row, playerList.get(Character.toString(sym)), black_figure_list.get(Character.toString(sym)), image_list.get(Character.toString(sym))));
                        else
                            chessPieces.add(new ChessPiece(column,row, playerList.get(Character.toString(sym)), white_figure_list.get(Character.toString(sym)), image_list.get(Character.toString(sym))));
                        column++;
                    }
                }
            }
            row--;//спускаемся на строчку ниже
        }
        return chessPieces; //и получаем массив с фигурами
    }
}
