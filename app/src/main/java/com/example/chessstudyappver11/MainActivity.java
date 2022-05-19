package com.example.chessstudyappver11;
/**
 * Что подправить:
 * 1. в задачах неверно запоминает номер последней задачи (перебрасывает на следующую, если она была первой)
 * 2. не работает возврат к последнему уроку (через shared preferences)
 * 3. ничьи (троекратное повторение ходов)
 * */
import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NavigationBarView.OnItemSelectedListener mOnItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.navigation_home:
                    loadFragment(MenuFragment.newInstance());
                    return true;
                case R.id.navigation_statistic:
                    loadFragment(StatisticFragment.newInstance());
                    return true;
                case R.id.navigation_exit:
                    FragmentManager manager = getSupportFragmentManager();
                    AppCloseDialog close_app = new AppCloseDialog("Подтверждение","Вы действительно хотите выйти?", MainActivity.this);
                    close_app.show(manager, "myDialog");
                    return true;
            }
            return false;
        }
    };
    private void loadFragment(Fragment fragment){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_main, fragment);
        ft.commit();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        MenuFragment menuFragment = new MenuFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragment_main, menuFragment)
                .commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnItemSelectedListener(mOnItemSelectedListener);
        Bundle args = getIntent().getExtras();
        if (args!=null)
        {
            Toast toast = makeText(getApplicationContext(),
                "Вы еще не совершали действий!", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}