package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskAndDebutsTopics {
    @SerializedName(value = "one_move_mate", alternate = {"two_move_mate", "three_move_mate", "mate_with_N_and_R", "mate_with_sacrifice"})
    private List<TaskAndDebutsDescription> list_of_task_and_debuts;

    public TaskAndDebutsTopics(List<TaskAndDebutsDescription> list_of_task_and_debuts) {
        this.list_of_task_and_debuts = list_of_task_and_debuts;
    }

    public List<TaskAndDebutsDescription> getList_of_task_and_debuts() {
        return list_of_task_and_debuts;
    }

    public void setList_of_task_and_debuts(List<TaskAndDebutsDescription> list_of_task_and_debuts) {
        this.list_of_task_and_debuts = list_of_task_and_debuts;
    }
}
