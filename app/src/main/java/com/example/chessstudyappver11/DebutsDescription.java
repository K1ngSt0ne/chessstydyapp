package com.example.chessstudyappver11;

public class DebutsDescription {
    private int id;
    private String name;
    private String pgn;
    private String description;

    public DebutsDescription(int id, String name, String pgn, String description) {
        this.id = id;
        this.name = name;
        this.pgn = pgn;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPgn() {
        return pgn;
    }

    public void setPgn(String pgn) {
        this.pgn = pgn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
