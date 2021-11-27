package com.example.chessstudyappver11;

public class SliderItem {
    private int imageID;
    private String chess_text;
    private String chess_description;
    private String button_text;

    public SliderItem(int imageID, String chess_text, String chess_description, String button_text) {
        this.imageID = imageID;
        this.chess_text = chess_text;
        this.chess_description = chess_description;
        this.button_text = button_text;
    }

    public int getImageID() {
        return imageID;
    }

    public void setImageID(int imageID) {
        this.imageID = imageID;
    }

    public String getChess_text() {
        return chess_text;
    }

    public void setChess_text(String chess_text) {
        this.chess_text = chess_text;
    }

    public String getChess_description() {
        return chess_description;
    }

    public void setChess_description(String chess_description) {
        this.chess_description = chess_description;
    }

    public String getButton_text() {
        return button_text;
    }

    public void setButton_text(String button_text) {
        this.button_text = button_text;
    }
}
