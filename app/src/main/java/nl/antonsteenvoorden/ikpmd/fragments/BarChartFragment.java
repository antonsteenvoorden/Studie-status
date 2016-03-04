package nl.antonsteenvoorden.ikpmd.fragments;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.CombinedChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CombinedData;

import java.util.ArrayList;

import butterknife.ButterKnife;
import nl.antonsteenvoorden.ikpmd.R;
import nl.antonsteenvoorden.ikpmd.model.Module;

/**
 * Created by Anton on 19/01/2016.
 */
public class BarChartFragment extends Fragment {
  private CombinedChart barChart;
  View rootView;
  Context context;


  ArrayList<BarEntry> barObtainedEntries;

  BarDataSet barDataSet;

  BarData barObtained;
  int[] colors;

  public static BarChartFragment newInstance() {
    BarChartFragment fragment = new BarChartFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
                           Bundle savedInstanceState) {
    rootView = inflater.inflate(R.layout.fragment_bar_chart, container, false);
    super.onCreate(savedInstanceState);

    context = rootView.getContext();
    barObtainedEntries = new ArrayList<BarEntry>();
    colors = new int[2];

    barChart = (CombinedChart) rootView.findViewById(R.id.chart);
    initBarChart();
    getData();
    ButterKnife.bind(this, rootView);
    return rootView;
  }

  @Override
  public void onHiddenChanged(boolean changed) {
    super.onResume();
    getData();
    barChart.invalidate();
  }

  @Override
  public void onResume() {
    super.onResume();
    getData();
  }

  private void initBarChart() {
    barChart.setClickable(false);
    barChart.getLegend().setEnabled(false);
    barChart.getXAxis().setEnabled(false);
    barChart.getAxisLeft().setDrawAxisLine(false);
    barChart.getAxisLeft().setDrawGridLines(false);
    barChart.getAxisLeft().setDrawLabels(true);
    barChart.getAxisLeft().setTextColor(Color.WHITE);
    barChart.getAxisRight().setDrawAxisLine(false);
    barChart.getAxisRight().setDrawGridLines(false);
    barChart.getAxisRight().setDrawLabels(false);
    barChart.setDrawHighlightArrow(false);
    barChart.setDrawBorders(false);
    barChart.setDrawValueAboveBar(true);
    barChart.setDescriptionColor(Color.WHITE);
    barChart.setDrawGridBackground(false);
    barChart.setDescription("");
    int color1 = Color.rgb(0, 255, 0);
    int color2 = Color.rgb(255,0,0);
    colors[0] = color1;
    colors[1] = color2;
  }

  private void getData() {
    barObtainedEntries.clear();
    for (int i = 0; i < 4; i++) {
      float ectsTmp = 0;
      float ectsReceivedTmp = 0;
      for (Module module : Module.getPeriod(i + 1)) {
        ectsTmp += module.getEcts();
        if (module.getGrade() >= 5.5) {
          ectsReceivedTmp += module.getEcts();
        }
      }
      barObtainedEntries.add(new BarEntry(new float[]{ectsReceivedTmp, (ectsTmp - ectsReceivedTmp)}, i));
      if (ectsReceivedTmp == ectsTmp) {

      }
    }
    setData();
  }

  private void setData() {

    // BAR DATA GEHAALD
    barObtained = new BarData();
    barDataSet = new BarDataSet(barObtainedEntries, "Bar DataSet");

    barDataSet.setColors(colors);
    barDataSet.setValueTextColor(Color.WHITE);
    barDataSet.setValueTextSize(15f);
    barObtained.addDataSet(barDataSet);

    // ADD data to the chart
    String[] xValues = {"1", "2", "3", "4"};
    CombinedData data = new CombinedData(xValues);
    data.setData(barObtained);

    barChart.setData(data);
    barChart.invalidate();
  }
}
