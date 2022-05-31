package com.example.chessstudyappver11;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {
    private ViewPager2 mViewPager2;

    public MenuFragment() {

    }


    public static MenuFragment newInstance() {
        return new MenuFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);
        mViewPager2 = (ViewPager2) view.findViewById(R.id.viewPagerImageSlider);
        List<SliderItem> sliderItems = new ArrayList<>();
        sliderItems.add(new SliderItem(R.drawable.pawnpic, "Вернуться", "к последнему действию", "Вперед!")); //продолжить урок
        sliderItems.add(new SliderItem(R.drawable.puzzle, "Задачи", "решение задач", "К задачам")); //решать задачи

        sliderItems.add(new SliderItem(R.drawable.debuts, "Дебюты", "изучаем первые ходы", "К дебютам!")); //дебюты
        sliderItems.add(new SliderItem(R.drawable.headpic, "Уроки", "выбрать урок", "К знаниям!")); //все уроки
        sliderItems.add(new SliderItem(R.drawable.computer, "Сыграть", "с самим собой ", "Начать!")); // сыграть с компьютером
        mViewPager2.setAdapter(new SliderAdapter(sliderItems, mViewPager2));
        mViewPager2.setClipChildren(false);
        mViewPager2.setClipToPadding(false);
        mViewPager2.setOffscreenPageLimit(3);
        mViewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
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
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
