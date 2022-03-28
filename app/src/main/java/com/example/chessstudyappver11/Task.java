package com.example.chessstudyappver11;

import com.google.gson.annotations.SerializedName;

import java.util.List;
//Данный класс - самый "верхний" по иерархии json'а
//Получает список всех типов задач
public class Task {
    @SerializedName("tasks_type")
    private List<TaskTopics> list_of_task_or_debuts_topic;

    public Task(List<TaskTopics> list_of_task_or_debuts_topic) {
        this.list_of_task_or_debuts_topic = list_of_task_or_debuts_topic;
    }



    public List<TaskTopics> getList_of_task_or_debuts_topic() {
        return list_of_task_or_debuts_topic;
    }

    public void setList_of_task_or_debuts_topic(List<TaskTopics> list_of_task_or_debuts_topic) {
        this.list_of_task_or_debuts_topic = list_of_task_or_debuts_topic;
    }
}
