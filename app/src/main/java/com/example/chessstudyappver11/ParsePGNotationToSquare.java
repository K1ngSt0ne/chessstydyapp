package com.example.chessstudyappver11;

import java.util.ArrayList;
import java.util.HashMap;

public class ParsePGNotationToSquare {
    private String pgn_string;

    private HashMap<Integer,Integer> rows_number = new HashMap<>();
    private HashMap<String, Integer> column_literal = new HashMap<>();

    public ParsePGNotationToSquare(String pgn_string) {
        this.pgn_string = pgn_string;
    }

    public String getPgn_string() {
        return pgn_string;
    }


    public void setPgn_string(String pgn_string) {
        this.pgn_string = pgn_string;
    }


    {
        initRows();
        initColumns();
    }
    void initRows()
    {
        rows_number.put(1,0);
        rows_number.put(2,1);
        rows_number.put(3,2);
        rows_number.put(4,3);
        rows_number.put(5,4);
        rows_number.put(6,5);
        rows_number.put(7,6);
        rows_number.put(8,7);
    }
    void initColumns()
    {
        column_literal.put("a",0);
        column_literal.put("b",1);
        column_literal.put("c",2);
        column_literal.put("d",3);
        column_literal.put("e",4);
        column_literal.put("f",5);
        column_literal.put("g",6);
        column_literal.put("h",7);
    }
    public ArrayList<Moves> loadMovesFromPGNToArray()
    {
        ArrayList<Moves> convertedMoves = new ArrayList<>();
        String[] pgn_split = pgn_string.split(" ");
        ArrayList<String> moves = new ArrayList<>();
        for (String str : pgn_split)
        {
            if (str.length()>3)
                moves.add(str);
        }
        //проверяем на верхний регистр - если есть буква то значит это фигура, если буквы нет то пешка
        //мне нужно знать только координаты клеток
        for (int i=0; i<moves.size();i++)
        {
            ArrayList<Square> square_depart_arrival = new ArrayList<>();
            String[] square = moves.get(i).split("-");
            int row=0;
            int column=0;
            for (String str : square)
            {
                char[] symbols = str.toCharArray();
                for (char sym : symbols)
                {
                    if (Character.isLowerCase(sym))
                        column=column_literal.get(Character.toString(sym));
                    if (Character.isDigit(sym))
                        row=rows_number.get(Character.getNumericValue(sym));
                }
                Square sq = new Square(column, row);
                square_depart_arrival.add(sq);
            }
            Moves figure_moves = new Moves(square_depart_arrival.get(0), square_depart_arrival.get(square_depart_arrival.size()-1));
            convertedMoves.add(figure_moves);
        }
        return convertedMoves;
    }
}
