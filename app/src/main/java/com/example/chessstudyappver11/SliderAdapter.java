package com.example.chessstudyappver11;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.SharedPreferences;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder>{

    private List<SliderItem> mSliderItemList;
    private ViewPager2 mViewPager2;
    SliderAdapter(List<SliderItem> sliderItemList, ViewPager2 viewPager2) {
        mSliderItemList = sliderItemList;
        mViewPager2 = viewPager2;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.slideitem,
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        holder.setImage(mSliderItemList.get(position));
    }

    @Override
    public int getItemCount() {
        return mSliderItemList.size();
    }

    class SliderViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView chessName;
        private TextView chessDescription;
        private MaterialButton mMaterialButtonText;
        //  private MaterialCardView mCardView;


        SliderViewHolder(@NonNull View itemView) {
            super(itemView);
            //  mCardView = itemView.findViewById(R.id.mCardView);

            mImageView = itemView.findViewById(R.id.imageSlide);
            chessName = itemView.findViewById(R.id.textChessName);
            chessDescription = itemView.findViewById(R.id.textChessDescription);
            mMaterialButtonText = itemView.findViewById(R.id.MaterialButtonText);
            mMaterialButtonText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String button_Text=mMaterialButtonText.getText().toString();
                    start_activity(button_Text, itemView);
                }
            });
        }


        void setImage(SliderItem sliderItem)
        {
            mImageView.setImageResource(sliderItem.getImageID());
            chessName.setText(sliderItem.getChess_text());
            chessDescription.setText(sliderItem.getChess_description());
            mMaterialButtonText.setText(sliderItem.getButton_text());

        }
    }

    void start_activity(String text, View itemView)
    {
        switch (text)
        {
            case "Вперед!":
                Intent intent_buffer = new Intent(itemView.getContext(), BufferActivity.class);
                itemView.getContext().startActivity(intent_buffer);
                break;
            case "К задачам":
                Intent intent_tasks = new Intent(itemView.getContext(), TasksMenuActivity.class);
                itemView.getContext().startActivity(intent_tasks);
                break;
            case "К дебютам!":
                Intent intent_debuts = new Intent(itemView.getContext(), DebutsMenuActivity.class);
                itemView.getContext().startActivity(intent_debuts);
                break;
            case "К знаниям!":
                Intent intent_lessons = new Intent(itemView.getContext(), TopicLessonsActivity.class);
                itemView.getContext().startActivity(intent_lessons);
                break;
            case "Начать!":
                Intent intent_board = new Intent(itemView.getContext(), PlayBoardActivity.class);
                itemView.getContext().startActivity(intent_board);
                break;
        }
    }


}