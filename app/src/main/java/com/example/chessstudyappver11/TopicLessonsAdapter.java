package com.example.chessstudyappver11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TopicLessonsAdapter extends RecyclerView.Adapter<TopicLessonsAdapter.ViewHolder>{

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView topic_lesson_name;
        public TextView description;
        public ImageView image_topic_lesson;


        public ViewHolder(View itemView) {

            super(itemView);
            topic_lesson_name= (TextView) itemView.findViewById(R.id.topiclesson_name);
            description= (TextView) itemView.findViewById(R.id.description);
            image_topic_lesson = (ImageView) itemView.findViewById(R.id.lesson_topic_image);
        }
    }
    private List<TopicLesson> mTopicLessonList;

    interface OnTopicLessonClickListener{
        void onTopicLessonClick(TopicLesson topicLesson, int position);
    }

    private final OnTopicLessonClickListener onClickListener;

    public TopicLessonsAdapter(List<TopicLesson> tl, OnTopicLessonClickListener onClickListener) {
        mTopicLessonList= tl;
        this.onClickListener = onClickListener;
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View stateView = inflater.inflate(R.layout.actoles_template, parent, false);
        ViewHolder viewHolder = new ViewHolder(stateView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TopicLesson topicLesson = mTopicLessonList.get(position);
        TextView nameTopicLesson = holder.topic_lesson_name;
        nameTopicLesson.setText(topicLesson.getTopic_name());
        TextView nameDescription = holder.description;
        nameDescription.setText(topicLesson.getTopic_description());
        ImageView imageTopicLesson = holder.image_topic_lesson;
        imageTopicLesson.setImageResource(topicLesson.getTopic_image_res());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onTopicLessonClick(topicLesson,position);
            }
        });
    }

    @Override
    public int getItemCount() {
       return  mTopicLessonList.size();
    }
}
