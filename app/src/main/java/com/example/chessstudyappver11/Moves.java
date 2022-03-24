package com.example.chessstudyappver11;

public class Moves {
    private Square sq_from;
    private Square sq_to;

    public Moves(Square sq_from, Square sq_to) {
        this.sq_from = sq_from;
        this.sq_to = sq_to;
    }

    public Square getSq_from() {
        return sq_from;
    }

    public void setSq_from(Square sq_from) {
        this.sq_from = sq_from;
    }

    public Square getSq_to() {
        return sq_to;
    }

    public void setSq_to(Square sq_to) {
        this.sq_to = sq_to;
    }
}
