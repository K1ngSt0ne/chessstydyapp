package com.example.chessstudyappver11;

import android.media.Image;

public class TopicLesson {
    private String topic_name;
    private String topic_description;
    private int topic_image_res;

    public TopicLesson(String topic_name, String topic_description, int topic_image_res) {
        this.topic_name = topic_name;
        this.topic_description = topic_description;
        this.topic_image_res = topic_image_res;
    }

    public String getTopic_name() {
        return topic_name;
    }

    public void setTopic_name(String topic_name) {
        this.topic_name = topic_name;
    }

    public String getTopic_description() {
        return topic_description;
    }

    public void setTopic_description(String topic_description) {
        this.topic_description = topic_description;
    }

    public int getTopic_image_res() {
        return topic_image_res;
    }

    public void setTopic_image_res(int topic_image_res) {
        this.topic_image_res = topic_image_res;
    }
}
