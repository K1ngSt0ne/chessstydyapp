package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

public class TaskAndDebutsDescription {
    @SerializedName("id")
    private int id_task_debuts;
   // private String task_debuts_name;
    @SerializedName("fen_pos")
    private String fen_or_pgn_str;

    public TaskAndDebutsDescription(int id_task_debuts, String fen_or_pgn_str) {
        this.id_task_debuts = id_task_debuts;
        this.fen_or_pgn_str = fen_or_pgn_str;
    }

    public int getId_task_debuts() {
        return id_task_debuts;
    }

    public void setId_task_debuts(int id_task_debuts) {
        this.id_task_debuts = id_task_debuts;
    }

    public String getFen_or_pgn_str() {
        return fen_or_pgn_str;
    }

    public void setFen_or_pgn_str(String fen_or_pgn_str) {
        this.fen_or_pgn_str = fen_or_pgn_str;
    }
}
