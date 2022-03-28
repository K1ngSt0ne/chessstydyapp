package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;
//Данный класс - самый низший из иерархии json'а. Здесь у нас информация о конкретной задаче - ее id
// FEN нотация и число ходов, за которое ее надо решить
public class TaskDescription {
    @SerializedName("id")
    private int id_task_debuts;
    @SerializedName("fen_pos")
    private String fen_or_pgn_str;
    @SerializedName("turns")
    private int need_turns;

    public TaskDescription(int id_task_debuts, String fen_or_pgn_str, int need_turns) {
        this.id_task_debuts = id_task_debuts;
        this.fen_or_pgn_str = fen_or_pgn_str;
        this.need_turns = need_turns;
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

    public int getNeed_turns() {
        return need_turns;
    }

    public void setNeed_turns(int need_turns) {
        this.need_turns = need_turns;
    }
}
