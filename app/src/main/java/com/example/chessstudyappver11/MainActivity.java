package com.example.chessstudyappver11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager2 mViewPager2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        mViewPager2 = findViewById(R.id.viewPagerImageSlider);
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.pawnpic, "Вернуться", "к последнему уроку", "Вперед!")); //продолжить урок
        sliderItems.add(new SliderItem(R.drawable.puzzle,"Задачи", "решение задач", "К задачам")); //решать задачи

        sliderItems.add(new SliderItem(R.drawable.debuts,"Дебюты", "изучаем первые ходы", "К дебютам!")); //дебюты
        sliderItems.add(new SliderItem(R.drawable.headpic,"Уроки", "выбрать урок", "К знаниям!")); //все уроки
        sliderItems.add(new SliderItem(R.drawable.computer,"Сыграть", "с компьютером", "Начать!")); // сыграть с компьютером

        mViewPager2.setAdapter(new SliderAdapter(sliderItems, mViewPager2));
        mViewPager2.setClipChildren(false);
        mViewPager2.setClipToPadding(false);
        mViewPager2.setOffscreenPageLimit(3);
        mViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        // mViewPager2.setPageTransformer(new MarginPageTransformer(50));
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(50));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r = 1 - Math.abs(position);
                page.setScaleY(0.85f + r * 0.15f);
            }
        });
        mViewPager2.setPageTransformer(compositePageTransformer);
    }
}