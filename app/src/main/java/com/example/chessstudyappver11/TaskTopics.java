package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

import java.util.List;
//Данный класс в иерархии json'а - середина. Здесь мы у каждой конкретной темы получаем ее список задач
public class TaskTopics {
    @SerializedName("one_move_mate")
    private List<TaskDescription> list_of_OMM;
    @SerializedName("two_move_mate")
    private List<TaskDescription> list_of_TMM;
    @SerializedName("three_move_mate")
    private List<TaskDescription> list_of_ThMM;
    @SerializedName("mate_with_N_and_R")
    private List<TaskDescription> list_of_NnRM;
    @SerializedName("mate_with_sacrifice")
    private List<TaskDescription> list_of_MwS;

    public TaskTopics(List<TaskDescription> list_of_OMM, List<TaskDescription> list_of_TMM, List<TaskDescription> list_of_ThMM, List<TaskDescription> list_of_NnRM, List<TaskDescription> list_of_MwS) {
        this.list_of_OMM = list_of_OMM;
        this.list_of_TMM = list_of_TMM;
        this.list_of_ThMM = list_of_ThMM;
        this.list_of_NnRM = list_of_NnRM;
        this.list_of_MwS = list_of_MwS;
    }

    public List<TaskDescription> getList_of_OMM() {
        return list_of_OMM;
    }

    public void setList_of_OMM(List<TaskDescription> list_of_OMM) {
        this.list_of_OMM = list_of_OMM;
    }

    public List<TaskDescription> getList_of_TMM() {
        return list_of_TMM;
    }

    public void setList_of_TMM(List<TaskDescription> list_of_TMM) {
        this.list_of_TMM = list_of_TMM;
    }

    public List<TaskDescription> getList_of_ThMM() {
        return list_of_ThMM;
    }

    public void setList_of_ThMM(List<TaskDescription> list_of_ThMM) {
        this.list_of_ThMM = list_of_ThMM;
    }

    public List<TaskDescription> getList_of_NnRM() {
        return list_of_NnRM;
    }

    public void setList_of_NnRM(List<TaskDescription> list_of_NnRM) {
        this.list_of_NnRM = list_of_NnRM;
    }

    public List<TaskDescription> getList_of_MwS() {
        return list_of_MwS;
    }

    public void setList_of_MwS(List<TaskDescription> list_of_MwS) {
        this.list_of_MwS = list_of_MwS;
    }
}
