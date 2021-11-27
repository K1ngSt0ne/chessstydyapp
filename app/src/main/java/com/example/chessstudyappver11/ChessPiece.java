package com.example.chessstudyappver11;

public class ChessPiece {
    Integer column;
    Integer row;
    Player mPlayer;
    Chessman mChessman;
    Integer resID;

    public ChessPiece(Integer column, Integer row, Player player, Chessman chessman, Integer resID) {
        this.column = column;
        this.row = row;
        mPlayer = player;
        mChessman = chessman;
        this.resID = resID;
    }

    public Integer getColumn() {
        return column;
    }

    public void setColumn(Integer column) {
        this.column = column;
    }

    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    public Player getPlayer() {
        return mPlayer;
    }

    public void setPlayer(Player player) {
        mPlayer = player;
    }

    public Chessman getChessman() {
        return mChessman;
    }

    public void setChessman(Chessman chessman) {
        mChessman = chessman;
    }

    public Integer getResID() {
        return resID;
    }

    public void setResID(Integer resID) {
        this.resID = resID;
    }
}
