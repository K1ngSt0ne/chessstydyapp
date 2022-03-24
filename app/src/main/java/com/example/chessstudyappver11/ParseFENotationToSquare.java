package com.example.chessstudyappver11;

import java.util.ArrayList;
import java.util.HashMap;

public class ParseFENotationToSquare {
    private String fen_string;

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

    public ArrayList<ChessPiece> loadPositionsOnBoard()
    {
        ArrayList<ChessPiece> chessPieces = new ArrayList<>();
        String[] fen = fen_string.split(" ");
        String position = fen[0];
        String queue_move = fen[1];
        String[] rows = position.split("/");
       // rows_and_queue_move[rows_and_queue_move.length-1].split(" ");
        int row = 7;
        for (String str : rows)
        {
            //p1pBq1p1 6 ряд
            char[] char_row = str.toCharArray();
            int column = 0;
            if (row>=0)
            {
                for (char sym : char_row)
                {
                    if (Character.isDigit(sym))
                        column=column+Character.getNumericValue(sym);
                    else
                    {
                        if (Character.isLowerCase(sym))
                            chessPieces.add(new ChessPiece(column,row, playerList.get(Character.toString(sym)), black_figure_list.get(Character.toString(sym)), image_list.get(Character.toString(sym))));
                        else
                            chessPieces.add(new ChessPiece(column,row, playerList.get(Character.toString(sym)), white_figure_list.get(Character.toString(sym)), image_list.get(Character.toString(sym))));
                        column++;
                    }
                }
            }
            row--;
        }


        return chessPieces;
    }
}
