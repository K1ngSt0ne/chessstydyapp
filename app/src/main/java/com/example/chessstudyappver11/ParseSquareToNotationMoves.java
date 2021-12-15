package com.example.chessstudyappver11;

import android.util.Log;

import java.util.HashMap;

public class ParseSquareToNotationMoves {
    private int column;
    private int row;
    private int start_size;
    private int end_size;
    private Chessman piece_type;

    private HashMap<Integer,Integer> rows_number = new HashMap<>();
    private HashMap<Integer, String> column_literal = new HashMap<>();
    private HashMap<Chessman, String> figure_literal = new HashMap<>();

    public ParseSquareToNotationMoves(int column, int row, int start_size, int end_size, Chessman piece_type) {
        this.column = column;
        this.row = row;
        this.start_size = start_size;
        this.end_size = end_size;
        this.piece_type = piece_type;
    }

    {
        init_rows();
        init_columns();
        init_figure_icon();
    }

    private void init_rows()
    {
        rows_number.put(0,1);
        rows_number.put(1,2);
        rows_number.put(2,3);
        rows_number.put(3,4);
        rows_number.put(4,5);
        rows_number.put(5,6);
        rows_number.put(6,7);
        rows_number.put(7,8);
    }
    private void init_columns()
    {
        column_literal.put(0, "a");
        column_literal.put(1, "b");
        column_literal.put(2, "c");
        column_literal.put(3, "d");
        column_literal.put(4, "e");
        column_literal.put(5, "f");
        column_literal.put(6, "g");
        column_literal.put(7, "h");
    }
    private void init_figure_icon()
    {
        figure_literal.put(Chessman.KING, "\u2654");
        figure_literal.put(Chessman.QUEEN, "\u2655");
        figure_literal.put(Chessman.ROOK, "\u2656");
        figure_literal.put(Chessman.BISHOP, "\u2657");
        figure_literal.put(Chessman.KNIGHT, "\u2658");
        figure_literal.put(Chessman.PAWN, "\u2659");
    }


    public String convertSquareToNotation(ParseSquareToNotationMoves move)
    {
        //добавить словарь с фигурами
        String notation_string="";
        notation_string+=figure_literal.get(move.getPiece_type());
           // if (i==move.getPiece_type())
            //    notation_string+=figure_literal.get(i);

        for (int i=0;i<column_literal.size();i++)
        {
            if (i==move.getColumn())
                notation_string+=column_literal.get(i);
        }
        for (int i=0;i<rows_number.size();i++)
        {
            if (i==move.getRow())
                notation_string+=rows_number.get(i);
        }
        return notation_string;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getStart_size() {
        return start_size;
    }

    public void setStart_size(int start_size) {
        this.start_size = start_size;
    }

    public int getEnd_size() {
        return end_size;
    }

    public void setEnd_size(int end_size) {
        this.end_size = end_size;
    }

    public Chessman getPiece_type() {
        return piece_type;
    }

    public void setPiece_type(Chessman piece_type) {
        this.piece_type = piece_type;
    }
}
