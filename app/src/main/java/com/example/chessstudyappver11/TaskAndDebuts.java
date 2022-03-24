package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TaskAndDebuts {
    @SerializedName("tasks_type")
    private List<TaskAndDebutsTopics> list_of_task_or_debuts_topic;

    public TaskAndDebuts(List<TaskAndDebutsTopics> list_of_task_or_debuts_topic) {
        this.list_of_task_or_debuts_topic = list_of_task_or_debuts_topic;
    }



    public List<TaskAndDebutsTopics> getList_of_task_or_debuts_topic() {
        return list_of_task_or_debuts_topic;
    }

    public void setList_of_task_or_debuts_topic(List<TaskAndDebutsTopics> list_of_task_or_debuts_topic) {
        this.list_of_task_or_debuts_topic = list_of_task_or_debuts_topic;
    }
}
