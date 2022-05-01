package com.example.chessstudyappver11;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StatisticFragment extends Fragment {

    BarChart mBarChartTask;
    BarChart mBarChartTopic;

    public StatisticFragment()
    {

    }
    public static StatisticFragment newInstance()
    {
        return new StatisticFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistic, container, false);
        TextView taskCount = view.findViewById(R.id.taskSolvingCount);
        TextView topicCount = view.findViewById(R.id.topicSolvingCount);
        SharedPreferences settings = getActivity().getSharedPreferences("ChessDate", Context.MODE_PRIVATE);
        taskCount.setText(taskCount.getText()+ " " + Integer.toString(settings.getInt("taskSolving", 0)));
        topicCount.setText(topicCount.getText()+ " " + Integer.toString(settings.getInt("taskTopicSolving", 0)));
        mBarChartTask = view.findViewById(R.id.chartTaskSolve);
        mBarChartTask.getXAxis().setCenterAxisLabels(true);
        mBarChartTask.getXAxis().setLabelCount(6, true);
        mBarChartTask.getAxisRight().setEnabled(false);
        mBarChartTopic = view.findViewById(R.id.chartTopicSolve);
        mBarChartTopic.getXAxis().setCenterAxisLabels(true);
        mBarChartTopic.getXAxis().setLabelCount(6, true);
        mBarChartTopic.getAxisRight().setEnabled(false);
        ArrayList<String> labelsName = initTaskName();
        loadTaskSolvingStatistic(labelsName);
        loadTopicSolvingStatistic(labelsName);
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

    void loadTaskSolvingStatistic(ArrayList<String> label)
    {
        ArrayList<BarEntry> entries_rb = new ArrayList<>();
        ArrayList<String> labels_rb = label;
        BarDataSet dataSet;
        BarData data;
        SharedPreferences settings = getActivity().getSharedPreferences("ChessDate", Context.MODE_PRIVATE);
        entries_rb.add(new BarEntry(0, settings.getInt("oneMoveTaskSolving", 0)));
        entries_rb.add(new BarEntry(1, settings.getInt("twoMateTaskSolving", 0)));
        entries_rb.add(new BarEntry(2, settings.getInt("threeMateTaskSolving", 0)));
        entries_rb.add(new BarEntry(3, settings.getInt("rookAndKnightTaskSolving", 0)));
        entries_rb.add(new BarEntry(4, settings.getInt("sacrificeTaskSolving", 0)));
        dataSet = new BarDataSet(entries_rb, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new BarData(dataSet);
        data.setBarWidth(0.6f);
        mBarChartTask.setData(data);
        mBarChartTask.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels_rb.get((int) (value + 0.5));
                } catch (IndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        mBarChartTask.invalidate();


    }
    void loadTopicSolvingStatistic(ArrayList<String> label)
    {
        ArrayList<BarEntry> entries_rb = new ArrayList<>();
        ArrayList<String> labels_rb = label;
        BarDataSet dataSet;
        BarData data;
        SharedPreferences settings = getActivity().getSharedPreferences("ChessDate", Context.MODE_PRIVATE);
        entries_rb.add(new BarEntry(0, settings.getInt("oneMoveMateSolving", 0)));
        entries_rb.add(new BarEntry(1, settings.getInt("twoMoveMateSolving", 0)));
        entries_rb.add(new BarEntry(2, settings.getInt("threeMoveMateSolving", 0)));
        entries_rb.add(new BarEntry(3, settings.getInt("rookAndKnightMoveSolving", 0)));
        entries_rb.add(new BarEntry(4, settings.getInt("sacrificeMateSolving", 0)));
        dataSet = new BarDataSet(entries_rb, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        data = new BarData(dataSet);
        data.setBarWidth(0.6f);
        mBarChartTopic.setData(data);
        mBarChartTopic.getXAxis().setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                try {
                    return labels_rb.get((int) (value + 0.5));
                } catch (IndexOutOfBoundsException e) {
                    return "";
                }
            }
        });
        mBarChartTopic.invalidate();
    }
    ArrayList<String>  initTaskName()
    {
        ArrayList<String> labels_rb = new ArrayList<>();
        labels_rb.add("OMM");
        labels_rb.add("TMM");
        labels_rb.add("ThMM");
        labels_rb.add("RandNM");
        labels_rb.add("SMate");
        return  labels_rb;
    }
}
