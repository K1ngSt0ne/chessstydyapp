package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskAndDebutsTopics {
    @SerializedName("one_move_mate")
    private List<TaskAndDebutsDescription> list_of_OMM;
    @SerializedName("two_move_mate")
    private List<TaskAndDebutsDescription> list_of_TMM;
    @SerializedName("three_move_mate")
    private List<TaskAndDebutsDescription> list_of_ThMM;
    @SerializedName("mate_with_N_and_R")
    private List<TaskAndDebutsDescription> list_of_NnRM;
    @SerializedName("mate_with_sacrifice")
    private List<TaskAndDebutsDescription> list_of_MwS;

    public TaskAndDebutsTopics(List<TaskAndDebutsDescription> list_of_OMM, List<TaskAndDebutsDescription> list_of_TMM, List<TaskAndDebutsDescription> list_of_ThMM, List<TaskAndDebutsDescription> list_of_NnRM, List<TaskAndDebutsDescription> list_of_MwS) {
        this.list_of_OMM = list_of_OMM;
        this.list_of_TMM = list_of_TMM;
        this.list_of_ThMM = list_of_ThMM;
        this.list_of_NnRM = list_of_NnRM;
        this.list_of_MwS = list_of_MwS;
    }

    public List<TaskAndDebutsDescription> getList_of_OMM() {
        return list_of_OMM;
    }

    public void setList_of_OMM(List<TaskAndDebutsDescription> list_of_OMM) {
        this.list_of_OMM = list_of_OMM;
    }

    public List<TaskAndDebutsDescription> getList_of_TMM() {
        return list_of_TMM;
    }

    public void setList_of_TMM(List<TaskAndDebutsDescription> list_of_TMM) {
        this.list_of_TMM = list_of_TMM;
    }

    public List<TaskAndDebutsDescription> getList_of_ThMM() {
        return list_of_ThMM;
    }

    public void setList_of_ThMM(List<TaskAndDebutsDescription> list_of_ThMM) {
        this.list_of_ThMM = list_of_ThMM;
    }

    public List<TaskAndDebutsDescription> getList_of_NnRM() {
        return list_of_NnRM;
    }

    public void setList_of_NnRM(List<TaskAndDebutsDescription> list_of_NnRM) {
        this.list_of_NnRM = list_of_NnRM;
    }

    public List<TaskAndDebutsDescription> getList_of_MwS() {
        return list_of_MwS;
    }

    public void setList_of_MwS(List<TaskAndDebutsDescription> list_of_MwS) {
        this.list_of_MwS = list_of_MwS;
    }
}
