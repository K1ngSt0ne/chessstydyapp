package com.example.chessstudyappver11;

public interface ChessDelegate {
    ChessPiece pieceAt(Square square);
    void movePiece(Square from, Square to);
}
